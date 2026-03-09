package com.itsconv.web.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itsconv.web.board.domain.Board;
import com.itsconv.web.board.domain.BoardType;
import com.itsconv.web.board.service.dto.command.BoardPostCommand;
import com.itsconv.web.view.admin.dto.BoardPost;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardQueryRepository{
    @Query("select coalesce(max(i.sortOrder), 0) from Board i where i.type = :type")
    Integer findNextOrderByType(BoardType type);

    @Query("""
            select new com.itsconv.web.view.admin.dto.BoardPost(b.id, b.title) 
            from Board b
            where b.type = :#{#dto.type}
            and (b.sortOrder < :#{#dto.sortOrder} or (b.sortOrder = :#{#dto.sortOrder} and b.id < :#{#dto.id}))
            order by sortOrder desc
            limit 1
            """)
    BoardPost findPrevPostByTypeAndOrder(@Param("dto") BoardPostCommand command);

    @Query("""
            select new com.itsconv.web.view.admin.dto.BoardPost(b.id, b.title) 
            from Board b
            where b.type = :#{#dto.type}
            and (b.sortOrder > :#{#dto.sortOrder} or (b.sortOrder = :#{#dto.sortOrder} and b.id > :#{#dto.id}))
            order by sortOrder asc
            limit 1
            """)
    BoardPost findNextPostByTypeAndOrder(@Param("dto") BoardPostCommand command);
}
