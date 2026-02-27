package com.itsconv.web.view.admin.advice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Menu {

    ADMIN_LIST(new String[]{"/admin/user/list"}, "admin-list", "basic", "기본설정", "관리자설정", "관리자를 추가, 수정, 삭제할 수 있습니다."),
    ADMIN_EDIT(new String[]{"/admin/user/edit"}, "admin-list", "basic", "기본설정", "관리자목록", "관리자를 추가/수정/삭제 합니다."),
    POPUP_LIST(new String[]{"/admin/popup/list"}, "popup-list", "basic", "기본설정", "팝업관리", "팝업을 등록, 수정, 삭제할 수 있습니다."),
    POPUP_EDIT(new String[]{"/admin/popup/edit"}, "popup-list", "basic", "기본설정", "팝업관리", "팝업을 등록, 수정, 삭제할 수 있습니다."),

    DATA(new String[]{"/admin/bbs/data", "/admin/bbs/bbs_list"}, "data", "posts", "게시판관리", "홍보자료", "홍보자료를 등록, 수정, 삭제할 수 있습니다."),
    NOTICE(new String[]{"/admin/bbs/its_notice"}, "its_notice", "posts", "게시판관리", "공지사항", "공지사항을 등록, 수정, 삭제할 수 있습니다."),
    DOWNLOAD(new String[]{"/admin/bbs/download"}, "download", "posts", "게시판관리", "자료실", "자료실 게시물을 등록, 수정, 삭제할 수 있습니다."),
    STORY(new String[]{"/admin/bbs/story"}, "story", "posts", "게시판관리", "ITS Story", "ITS Story 게시물을 등록, 수정, 삭제할 수 있습니다."),

    HISTORY(new String[]{"/admin/history/history"}, "history", "news", "연혁관리", "연혁관리", "연혁 항목을 등록, 수정, 삭제할 수 있습니다.");

    private final String[] uris;
    private final String pageData;
    private final String activeTab;
    private final String breadcrumbTitle;
    private final String pageTitle;
    private final String pageDescription;

    // URI를 기반으로 Enum 찾기 (없으면 기본값 반환)
    public static Menu fromUri(String requestUri) {
        return Arrays.stream(values())
                .filter(menu -> Arrays.stream(menu.uris).anyMatch(requestUri::startsWith))
                .findFirst()
                .orElse(ADMIN_LIST); // 기본 페이지
    }
}
