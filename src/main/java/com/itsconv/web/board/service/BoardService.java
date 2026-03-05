package com.itsconv.web.board.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itsconv.web.board.controller.dto.request.BoardCopyRequest;
import com.itsconv.web.board.controller.dto.request.BoardListRequest;
import com.itsconv.web.board.controller.dto.request.BoardMoveRequest;
import com.itsconv.web.board.controller.dto.request.BoardOrderRequest;
import com.itsconv.web.board.controller.dto.response.BoardListResponse;
import com.itsconv.web.board.domain.Board;
import com.itsconv.web.board.repository.BoardRepository;
import com.itsconv.web.board.service.dto.command.BoardCopyCommand;
import com.itsconv.web.board.service.dto.command.BoardMoveCommand;
import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;
import com.itsconv.web.security.service.UserPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public Page<BoardListResponse> findBoardList(BoardListRequest req) {
        if (req.getType() == null) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        String keyword = req.avoidNullKeyword();
        Page<Board> page = boardRepository.search(req.getType(), req.getSearchType(), keyword, req.toPageable());

        log.info("findBoardList :: page - {}", page);

        return page.map(m -> BoardListResponse.from(m));
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

        List<Board> saveList = new ArrayList<>();
        for (Board from : board) {
            BoardCopyCommand command = BoardCopyCommand.from(
                from, 
                req.targetType(), 
                userPrincipal.getUsername(), 
                userPrincipal.getName(),
                ++nextOrder
            );

            Board to = new Board();
            
            to.saveCopiedBoard(command);

            saveList.add(to);
        }

        boardRepository.saveAll(saveList);
    }

    @Transactional
    public void delete(List<Long> targetList) {
        if (targetList == null || targetList.size() == 0) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        boardRepository.deleteAllById(targetList);
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
}
