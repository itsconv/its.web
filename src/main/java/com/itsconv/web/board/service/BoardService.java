package com.itsconv.web.board.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itsconv.web.board.controller.dto.request.BoardListRequest;
import com.itsconv.web.board.controller.dto.response.BoardListResponse;
import com.itsconv.web.board.domain.Board;
import com.itsconv.web.board.repository.BoardRepository;
import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;

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

        Page<Board> page;
        String keyword = req.avoidNullKeyword();

        if (keyword.isBlank()) {
            page = boardRepository.findByType(req.getType(), req.toPageable());
        } else {
            page = boardRepository.findByTypeAndTitleContainingIgnoreCase(
                req.getType(),
                keyword,
                req.toPageable()
            );
        }

        log.info("findBoardList :: page - ", page);

        return page.map(m -> BoardListResponse.from(m));
    }
}
