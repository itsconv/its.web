package com.itsconv.web.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.itsconv.web.board.domain.Board;
import com.itsconv.web.board.domain.BoardType;

public interface BoardRepository extends JpaRepository<Board, Long>{
    
    Page<Board> findByType(BoardType type, Pageable pageable);

    Page<Board> findByTypeAndTitleContainingIgnoreCase(BoardType type, String keyword, Pageable pageable);
}
