package com.itsconv.web.view.admin.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.itsconv.web.board.controller.dto.response.BoardAttachedResponse;
import com.itsconv.web.board.domain.BoardType;

public record BoardFormView(
    String createName,
    LocalDateTime createDate,
    String title,
    String contents,
    BoardType boardType,
    List<FileAttachView> fileList
) {
    public static BoardFormView create(BoardType type, String author) {
        return new BoardFormView(
           author, null, "", "", type, new ArrayList<>()
        );
    }

    public static BoardFormView edit(BoardAttachedResponse res) {
        return new BoardFormView(
            res.board().createName(),
            res.board().createDate(),
            res.board().title(),
            res.board().contents(),
            res.board().type(),
            res.attaches()
        );
    }
}
