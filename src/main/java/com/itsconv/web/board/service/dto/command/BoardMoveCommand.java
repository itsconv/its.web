package com.itsconv.web.board.service.dto.command;

import com.itsconv.web.board.domain.BoardType;

import lombok.Builder;

@Builder
public record BoardMoveCommand(
    BoardType targetType,
    String requestId,
    String requestName,
    Integer nextOrder
) {
    
}
