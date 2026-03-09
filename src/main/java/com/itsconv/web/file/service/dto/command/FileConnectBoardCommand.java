package com.itsconv.web.file.service.dto.command;

import java.util.List;

import lombok.Builder;

@Builder
public record FileConnectBoardCommand(
    Long boardId,
    List<Long> detailIds,
    Integer thumbnailOrder
) {
    
}
