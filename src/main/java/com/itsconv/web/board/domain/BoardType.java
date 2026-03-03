package com.itsconv.web.board.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardType {
    DATA("bbs/data"),
    DOWNLOAD("bbs/download"),
    NOTICE("bbs/notice"),
    STORY("bbs/story");

    private final String path;

    public static BoardType from(String value) {
        return BoardType.valueOf(value.toUpperCase());
    }

    public String listView() {
        return this.getPath();
    }
}