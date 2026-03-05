package com.itsconv.web.board.service.dto.command;

import com.itsconv.web.board.domain.Board;
import com.itsconv.web.board.domain.BoardType;

import lombok.Builder;

@Builder
public record BoardCopyCommand(
    BoardType type,
    String title,
    String contents,
    String createId,
    String createName,
    String lastUpdateId,
    String lastUpdateName,
    Integer sortOrder
) {
    public static BoardCopyCommand from(
        Board b, BoardType reqType, String requestId, String requestName, Integer nextOrder
    ) {
        return BoardCopyCommand.builder()
            .type(reqType)
            .title(b.getTitle())
            .contents(b.getContents())
            .createId(requestId)
            .createName(requestName)
            .lastUpdateId(requestId)
            .lastUpdateName(requestName)
            .sortOrder(nextOrder)
            .build();
    }

}
