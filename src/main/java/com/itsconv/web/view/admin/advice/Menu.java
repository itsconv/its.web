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

    DATA(new String[]{"/admin/bbs/data", "/admin/bbs/bbs_list", "/admin/bbs/edit/data", "/admin/bbs/detail/data"}, "data", "posts", "게시판관리", "홍보자료", "홍보자료를 등록, 수정, 삭제할 수 있습니다."),
    NOTICE(new String[]{"/admin/bbs/notice", "/admin/bbs/edit/notice", "/admin/bbs/detail/notice"}, "notice", "posts", "게시판관리", "공지사항", "공지사항을 등록, 수정, 삭제할 수 있습니다."),
    DOWNLOAD(new String[]{"/admin/bbs/download", "/admin/bbs/edit/download", "/admin/bbs/detail/download"}, "download", "posts", "게시판관리", "자료실", "자료실 게시물을 등록, 수정, 삭제할 수 있습니다."),
    STORY(new String[]{"/admin/bbs/story", "/admin/bbs/edit/story", "/admin/bbs/detail/story"}, "story", "posts", "게시판관리", "ITS Story", "ITS Story 게시물을 등록, 수정, 삭제할 수 있습니다."),

    HISTORY(new String[]{"/admin/history/list"}, "history", "news", "연혁관리", "연혁관리", "연혁 항목을 등록, 수정, 삭제할 수 있습니다."),

    IMAGE_MANAGEMENT(new String[]{"/admin/image"}, "image-management", "media", "이미지관리", "이미지관리", "이미지관리를 관리할 수 있습니다."),
    IMAGE_TRADING_ROOM(new String[]{"/admin/image/trading-room"}, "image-trading-room", "media", "이미지관리", "통합트레이딩룸", "통합트레이딩룸을 관리할 수 있습니다."),
    IMAGE_AI_MONITORING_CENTER(new String[]{"/admin/image/ai-monitoring-center"}, "image-ai-monitoring-center", "media", "이미지관리", "AI관제센터", "AI관제센터를 관리할 수 있습니다."),
    IMAGE_AI_CONTACT_CENTER(new String[]{"/admin/image/ai-contact-center"}, "image-ai-contact-center", "media", "이미지관리", "AI컨텍센터", "AI컨텍센터를 관리할 수 있습니다."),
    IMAGE_UC_SOLUTION(new String[]{"/admin/image/uc-solution"}, "image-uc-solution", "media", "이미지관리", "UC솔루션", "UC솔루션을 관리할 수 있습니다."),

    QUESTION(new String[]{"/admin/question/list", "/admin/question/detail"}, "question", "posts", "게시판관리", "고객문의", "홈페이지 상담신청을 통해 등록된 내용을 확인할 수 있습니다.");

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
