package com.itsconv.web.board.repository.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.itsconv.web.board.domain.Board;
import com.itsconv.web.board.domain.BoardSearchType;
import com.itsconv.web.board.domain.BoardType;
import com.itsconv.web.board.domain.QBoard;
import com.itsconv.web.board.repository.BoardQueryRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardQueryRepositoryImpl implements BoardQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Board> search(BoardType type, BoardSearchType searchType, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        BooleanBuilder where = new BooleanBuilder();

        where.and(board.type.eq(type));

        if (!keyword.isEmpty()) {
            switch (searchType) {
                case TITLE -> where.and(board.title.containsIgnoreCase(keyword));
                case CONTENT -> where.and(board.contents.contains(keyword));
                case TITLE_CONTENT -> where.and(
                    board.title.containsIgnoreCase(keyword)
                    .or(board.contents.contains(keyword))
                );
                case NAME -> where.and(board.createName.containsIgnoreCase(keyword));
                case ID -> where.and(board.createId.containsIgnoreCase(keyword));
            }
        }

        List<Board> content = jpaQueryFactory
            .selectFrom(board)
            .where(where)
            .orderBy(board.sortOrder.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = jpaQueryFactory
            .select(board.count())
            .from(board)
            .where(where)
            .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }
    
}
