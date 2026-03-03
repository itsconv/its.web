package com.itsconv.web.board.controller.dto.response;

import java.time.LocalDateTime;

import com.itsconv.web.board.domain.Board;

public record BoardListResponse(
    Long id,
    String title,
    Integer viewCount,
    String createName,
    LocalDateTime createDate
) {
    public static BoardListResponse from(Board b) {
        return new BoardListResponse(
            b.getId(),
            b.getTitle(), 
            b.getViewCount(), 
            b.getCreateName(),
            b.getCreateDate()
        );
    }
}
