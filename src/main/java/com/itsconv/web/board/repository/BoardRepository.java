package com.itsconv.web.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itsconv.web.board.domain.Board;
import com.itsconv.web.board.domain.BoardType;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardQueryRepository{
    @Query("select coalesce(max(i.sortOrder), 0) from Board i where i.type = :type")
    Integer findNextOrderByType(BoardType type);
}
