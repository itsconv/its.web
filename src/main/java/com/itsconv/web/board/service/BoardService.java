package com.itsconv.web.board.service;


import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itsconv.web.board.controller.dto.request.BoardCopyRequest;
import com.itsconv.web.board.controller.dto.request.BoardCreateRequest;
import com.itsconv.web.board.controller.dto.request.BoardListRequest;
import com.itsconv.web.board.controller.dto.request.BoardModifyRequest;
import com.itsconv.web.board.controller.dto.request.BoardMoveRequest;
import com.itsconv.web.board.controller.dto.request.BoardOrderRequest;
import com.itsconv.web.board.controller.dto.response.BoardAttachedResponse;
import com.itsconv.web.board.controller.dto.response.BoardListResponse;
import com.itsconv.web.board.domain.Board;
import com.itsconv.web.board.repository.BoardRepository;
import com.itsconv.web.board.service.dto.command.BoardModifyCommand;
import com.itsconv.web.board.service.dto.command.BoardMoveCommand;
import com.itsconv.web.board.service.dto.command.BoardPostCommand;
import com.itsconv.web.board.service.dto.command.BoardSaveCommand;
import com.itsconv.web.board.service.dto.command.BoardSlotCommand;
import com.itsconv.web.board.service.dto.view.BoardReadView;
import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;
import com.itsconv.web.file.service.FileService;
import com.itsconv.web.file.service.dto.command.FileConnectBoardCommand;
import com.itsconv.web.file.service.dto.command.FileUpdateCommand;
import com.itsconv.web.security.service.UserPrincipal;
import com.itsconv.web.view.admin.dto.BoardDetailView;
import com.itsconv.web.view.admin.dto.BoardPost;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final FileService fileService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public Page<BoardListResponse> findBoardList(BoardListRequest req) {
        if (req.getType() == null) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        String keyword = req.avoidNullKeyword();
        Page<Board> page = boardRepository.search(req.getType(), req.getSearchType(), keyword, req.toPageable());

        log.info("findBoardList :: page - {}", page);

        List<Long> boardIds = page.getContent().stream()
            .map(Board::getId)
            .toList();

        Map<Long, Long> thumbnailMap = fileService.findThumbnailFileIdsByBoardIds(boardIds).stream()
            .collect(Collectors.toMap(
                thumbnail -> thumbnail.boardId(),
                thumbnail -> thumbnail.fileId(),
                (first, second) -> first
            ));

        return page.map(board -> BoardListResponse.from(board, thumbnailMap.get(board.getId())));
    }

    @Transactional(readOnly = true)
    public BoardAttachedResponse findBoardOne(Long id) {
        Board board = boardRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_BAD_REQUEST));

        return new BoardAttachedResponse(
            BoardReadView.from(board), fileService.findAttachesByBoardId(id)
        );
    }

    @Transactional(readOnly = true)
    public BoardDetailView getBoardDetailView(Long id) {
        Board board = boardRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_BAD_REQUEST));

        BoardPostCommand command = new BoardPostCommand(id, board.getType(), board.getSortOrder());

        BoardPost prev = boardRepository.findPrevPostByTypeAndOrder(command);
        BoardPost next = boardRepository.findNextPostByTypeAndOrder(command);

        return BoardDetailView.from(board, convertYoutubeLinks(board.getContents()), prev, next);
    }

    @Transactional
    public void move(BoardMoveRequest req, UserPrincipal userPrincipal) {
        if (req.targetId() == null || req.targetId().size() == 0) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        } 
        List<Board> board = boardRepository.findAllById(req.targetId());

        if (board.size() != req.targetId().size()) { // 일부 일치하지않는 ID?
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        //요청 type의 max(order)
        Integer nextOrder = boardRepository.findNextOrderByType(req.targetType());
        
        for (Board b : board) {
            BoardMoveCommand command = BoardMoveCommand.builder()
                .targetType(req.targetType())
                .requestId(userPrincipal.getUsername())
                .requestName(userPrincipal.getName())
                .nextOrder(++nextOrder)
                .build();
            
            b.changeBoardType(command);
        }
    }

    @Transactional
    public void copy(BoardCopyRequest req, UserPrincipal userPrincipal) {
        if (req.targetId() == null || req.targetId().size() == 0) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        } 

        List<Board> board = boardRepository.findAllById(req.targetId());

        if (board.size() != req.targetId().size()) { // 일부 일치하지않는 ID?
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        //요청 type의 max(order)
        Integer nextOrder = boardRepository.findNextOrderByType(req.targetType());

        for (Board from : board) {
            BoardSaveCommand command = BoardSaveCommand.from(
                from, 
                req.targetType(), 
                userPrincipal.getUsername(), 
                userPrincipal.getName(),
                ++nextOrder
            );

            Board to = new Board();
            
            to.saveBoard(command);
            Board savedBoard = boardRepository.save(to);

            String copiedContents = fileService.copyEditorFilesForBoard(
                from.getId(),
                from.getContents(),
                savedBoard
            );

            savedBoard.replaceContents(copiedContents);
        }
    }

    @Transactional
    public void deleteList(List<Long> boardIds) {
        if (boardIds == null || boardIds.size() == 0) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        List<Long> fileIds = fileService.findFileIdsByBoardIds(boardIds);

        boardRepository.deleteAllById(boardIds);

        // FK 삭제 SQL에 먼저 반영
        entityManager.flush();

        if (fileIds == null || fileIds.isEmpty()) return;

        fileService.deleteFilesAfterRemoveBoards(fileIds);
    }

    @Transactional
    public void delete(Long boardId) {
        if (boardId == null) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        List<Long> fileIds = fileService.findFileIdsByBoardIds(List.of(boardId));

        boardRepository.deleteById(boardId);

        entityManager.flush();

        if (fileIds == null || fileIds.isEmpty()) return;

        fileService.deleteFilesAfterRemoveBoards(fileIds);
    }
    
    @Transactional
    public void updateOrder(List<BoardOrderRequest> requests, UserPrincipal userPrincipal) {
        if (requests == null || requests.size() == 0) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        List<Long> ids = new ArrayList<>();
        Map<Long, Integer> orderMap = new HashMap<>();
        for (BoardOrderRequest item : requests) {
            ids.add(item.id());
            orderMap.put(item.id(), item.order());
        }

        List<Board> boards = boardRepository.findAllById(ids);

        if (requests.size() != boards.size()) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        for (Board b : boards) {
            // 조회된 결과의 id에 해당하는 order update
            b.updateOrder(orderMap.get(b.getId()));
        }
    }

    @Transactional
    public void registerBoard(
        BoardCreateRequest req, List<BoardSlotCommand> files, UserPrincipal userPrincipal
    ) {
        if (!hasContent(req.contents())) {
            throw new BusinessException(ErrorCode.EMPTY_CONTENTS);
        }

        Board board = new Board();
        
        Integer nextOrder = boardRepository.findNextOrderByType(req.type());

        BoardSaveCommand command = BoardSaveCommand.builder()
            .type(req.type())
            .title(req.title())
            .contents(req.contents())
            .createId(userPrincipal.getUsername())
            .createName(userPrincipal.getName())
            .lastUpdateId(userPrincipal.getUsername())
            .lastUpdateName(userPrincipal.getName())
            .sortOrder(++nextOrder)
            .build();
        
        board.saveBoard(command);

        Board savedBoard = boardRepository.save(board);

        fileService.uploadFileFromBoard(
            files, 
            FileConnectBoardCommand.builder()
                .boardId(savedBoard.getId())
                .detailIds(req.detailIds())
                .thumbnailOrder(req.thumbnailOrder())
                .build()
        );
    }

    @Transactional
    public void updateBoard(
        Long boardId, BoardModifyRequest req, List<BoardSlotCommand> files, UserPrincipal userPrincipal
    ) {
        if (!hasContent(req.contents())) {
            throw new BusinessException(ErrorCode.EMPTY_CONTENTS);
        }
        
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_BAD_REQUEST));

        BoardModifyCommand command = BoardModifyCommand.from(
            req,
            userPrincipal.getUsername(), 
            userPrincipal.getName()
        );

        // board 엔티티 수정
        board.updateBoard(command);

        fileService.updateBoardFiles(board, files, FileUpdateCommand.from(boardId, req));
    }

    @Transactional
    public void increaseViewCount(Long boardId) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_BAD_REQUEST));

        board.increaseView();
    }

    private boolean hasContent(String html) {
        Document doc = Jsoup.parse(html);

        // 텍스트
        String text = doc.text().trim();

        // 이미지
        boolean hasImage = !doc.select("img").isEmpty();

        return !text.isEmpty() || hasImage;
    }

    private String convertYoutubeLinks(String contents) {
        Document doc = Jsoup.parseBodyFragment(contents == null ? "" : contents);

        doc.select("a[href]").forEach(link -> {
            String videoId = extractYoutubeVideoId(link.attr("href"));

            if (videoId == null) return;

            Element wrapper = new Element("div").addClass("video-embed");
            Element iframe = new Element("iframe")
                .attr("src", "https://www.youtube.com/embed/" + videoId)
                .attr("title", "YouTube video player")
                .attr("frameborder", "0")
                .attr("allow", "accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share")
                .attr("allowfullscreen", "true");

            wrapper.appendChild(iframe);
            link.after(wrapper);
            link.remove();
        });

        return doc.body().html();
    }

    private String extractYoutubeVideoId(String url) {
        if (url == null || url.isBlank()) return null;

        try {
            URI uri = URI.create(url);
            String host = uri.getHost();
            String path = uri.getPath();

            if (host == null) return null;

            if (host.contains("youtu.be")) {
                return path != null && path.length() > 1 ? path.substring(1) : null;
            }

            if (!host.contains("youtube.com")) {
                return null;
            }

            if (path != null && path.equals("/watch")) {
                String query = uri.getQuery();
                if (query == null) return null;

                for (String param : query.split("&")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2 && pair[0].equals("v")) {
                        return pair[1];
                    }
                }
            }

            if (path != null && path.startsWith("/shorts/")) {
                return path.substring("/shorts/".length());
            }

            if (path != null && path.startsWith("/embed/")) {
                return path.substring("/embed/".length());
            }
        } catch (Exception ignored) {
            return null;
        }

        return null;
    }
}
