package com.itsconv.web.board.service.dto.command;

import com.itsconv.web.board.controller.dto.request.BoardModifyRequest;

public record BoardModifyCommand(
      String title,
      String contents,
      String lastUpdateId,
      String lastUpdateName,
      Integer thumbnailOrder
) {
    public static BoardModifyCommand from(
        BoardModifyRequest req, String requestId, String requestName
    ) {
        return new BoardModifyCommand(
            req.title(), 
            req.contents(),
            requestId,
            requestName,
            req.thumbnailOrder()
        );
    }
}
