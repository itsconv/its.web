package com.itsconv.web.file.service.dto.command;

import java.util.List;

import com.itsconv.web.board.controller.dto.request.BoardModifyRequest;

public record FileUpdateCommand(
    Long boardId,
    Integer thumbnailOrder,
    List<Long> detailIds,
    List<Long> removeMappedIds,
    List<Long> removeEditorDetailIds
) {
    public static FileUpdateCommand from(Long boardId, BoardModifyRequest req) {
        return new FileUpdateCommand(
            boardId, 
            req.thumbnailOrder(), 
            req.detailIds(), 
            req.removeMappedIds(),
            req.removeEditorDetailIds()
        );
    }
}
