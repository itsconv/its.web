package com.itsconv.web.board.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum BoardType {
    DATA("data", "admin/bbs/data", "홍보자료"),
    DOWNLOAD("download", "admin/bbs/download", "자료실"),
    NOTICE("notice", "admin/bbs/notice", "공지사항"),
    STORY("story", "admin/bbs/story", "ITS Story");

    private final String path;
    private final String viewPath;
    private final String menu;

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
