package com.itsconv.web.board.controller.dto.response;

import java.time.LocalDateTime;

import com.itsconv.web.board.domain.Board;
import com.itsconv.web.board.domain.BoardType;

public record BoardListResponse(
    Long id,
    BoardType type,
    String title,
    Integer viewCount,
    String createName,
    LocalDateTime createDate,
    Integer sortOrder
) {
    public static BoardListResponse from(Board b) {
        return new BoardListResponse(
            b.getId(),
            b.getType(),
            b.getTitle(), 
            b.getViewCount(), 
            b.getCreateName(),
            b.getCreateDate(),
            b.getSortOrder()
        );
    }
}
