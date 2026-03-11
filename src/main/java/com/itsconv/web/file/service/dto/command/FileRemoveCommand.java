package com.itsconv.web.file.service.dto.command;

public record FileRemoveCommand(
    Long boardId,
    Long mappedId
) {
    public static FileRemoveCommand from(Long boardId, Long mappedId) {
        return new FileRemoveCommand(boardId, mappedId);
    }
}
