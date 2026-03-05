package com.itsconv.web.board.controller.dto.request;

import java.util.List;

import com.itsconv.web.board.domain.BoardType;

public record BoardMoveRequest(
    List<Long> targetId,
    BoardType targetType
) {
    
}
