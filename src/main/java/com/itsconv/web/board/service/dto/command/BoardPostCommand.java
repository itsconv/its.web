package com.itsconv.web.board.service.dto.command;

import com.itsconv.web.board.domain.BoardType;

public record BoardPostCommand(
    Long id,
    BoardType type,
    Integer sortOrder
) {
    
}
