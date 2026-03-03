package com.itsconv.web.board.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum BoardType {
    DATA("data", "admin/bbs/data"),
    DOWNLOAD("download", "admin/bbs/download"),
    NOTICE("its_notice", "admin/bbs/its_notice"),
    STORY("story", "admin/bbs/story");

    private final String path;
    private final String viewPath;

    public static BoardType from(String value) {
        return Arrays.stream(values())
                .filter(type -> type.path.equals(value))
                .findFirst()
                .orElseGet(() -> BoardType.valueOf(value.toUpperCase()));
    }

    public String listView() {
        return this.getViewPath();
    }
}
