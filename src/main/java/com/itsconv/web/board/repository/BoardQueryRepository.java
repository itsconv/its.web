package com.itsconv.web.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itsconv.web.board.domain.Board;
import com.itsconv.web.board.domain.BoardSearchType;
import com.itsconv.web.board.domain.BoardType;

public interface BoardQueryRepository {
    Page<Board> search(BoardType type, BoardSearchType searchType, String keyword, Pageable pageable);
}
