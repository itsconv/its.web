(function (window) {
  if (window.AdminSharedConfig) {
    return;
  }

  const assetPrefix = "/asset/img/";

  const dashboardTableMap = {
    admin: {
      headers: ["번호", "아이디", "성명", "마지막접속", "등록일", "기능"],
      rows: [
        ["1", "admin", "관리자", "2026-02-20 16:54:39", "2006-08-11 11:59:21"]
      ],
      actionLabel: "관리자등록"
    },
    popup: {
      headers: ["번호", "제목", "공지기간", "등록일", "기능"],
      rows: [
        ["1", "[공지] ITSConvergence 공지", "2026-02-20~2026-03-10", "2006-08-11 11:59:21"]
      ],
      actionLabel: "팝업등록"
    }
  };

  const tabConfig = [
    { id: "basic", label: "기본설정", icon: "icon2.png" },
    { id: "posts", label: "게시판관리", icon: "icon3.png" },
    { id: "news", label: "연혁관리", icon: "icon11.png" }
  ];

  const submenuConfig = {
    basic: [
      { id: "admin-settings", label: "관리자설정" },
      { id: "board-settings", label: "팝업관리" }
    ],
    posts: [
      { id: "promo", label: "홍보자료" },
      { id: "notice", label: "공지사항" },
      { id: "archive", label: "자료실" },
      { id: "its-story", label: "ITS Story" }
    ],
    news: []
  };

  const titleMap = {
    "site-info": "사이트정보",
    "admin-settings": "관리자설정",
    "board-settings": "팝업관리",
    promo: "홍보자료",
    notice: "공지사항",
    archive: "자료실",
    "its-story": "ITS Story",
    news: "연혁관리"
  };

  const titleSubMap = {
    "admin-settings": "관리자를 추가/수정/삭제 합니다.",
    "board-settings": "팝업관리 팝업을 추가/수정/삭제 합니다.",
    promo: "홍보자료를 추가/수정/삭제 합니다.",
    notice: "공지사항을 추가/수정/삭제 합니다.",
    archive: "자료실을 추가/수정/삭제 합니다.",
    "its-story": "ITS Story를 추가/수정/삭제 합니다.",
    news: ""
  };

  const posts = [
    "[ITS Story] 의교부 복지부 공무원조, 보도에서 의료 사각지대 예방다",
    "[ITS Story] 아이테에스컨버전스, (사)중문화에 결식아기금 지원 안내",
    "[ITS Story] (우)아이테에스컨버전스, 미역에 사회공헌 활동 나서",
    "[ITS Story] (우)아이테에스컨버전스, 미역에 사회공헌 활동 나서 '사회인정정 구조 알찬&q...",
    "[ITS Story] 아이테에스컨버전스, 행정안전부장관상 수상... 국가 인정정책 발전 기여 공로",
    "[홍보자료] 'ITS컨버전스' 스마트 통장관제 플랫폼 - 소방청13중심상황실"
  ];

  const stats = [
    { label: "총 게시판수", value: 8 },
    { label: "총 게시물", value: 69 },
    { label: "오늘 게시물", value: 0 },
    { label: "오늘 댓글", value: 0 }
  ];

  const submenuPathMap = {
    "admin-settings": "basic/admin_list",
    "board-settings": "basic/popup_list",
    promo: "bbs/data",
    notice: "bbs/its_notice",
    archive: "bbs/download",
    "its-story": "bbs/story",
    news: "year/year_list"
  };

  const pageStateMap = {
    "popup-list": {
      activeTab: "basic",
      activeSubMenu: "board-settings"
    },
    "admin-list": {
      activeTab: "basic",
      activeSubMenu: "admin-settings"
    },
    "data": {
      activeTab: "posts",
      activeSubMenu: "promo"
    },
    "bbs-list": {
      activeTab: "posts",
      activeSubMenu: "promo"
    },
    its_notice: {
      activeTab: "posts",
      activeSubMenu: "notice"
    },
    download: {
      activeTab: "posts",
      activeSubMenu: "archive"
    },
    story: {
      activeTab: "posts",
      activeSubMenu: "its-story"
    },
    year_list: {
      activeTab: "news",
      activeSubMenu: "news"
    }
  };

  window.AdminSharedConfig = {
    tabConfig,
    submenuConfig,
    titleMap,
    titleSubMap,
    dashboardTableMap,
    assetPrefix,
    posts,
    stats,
    submenuPathMap,
    pageStateMap,
    stateDefaults: {
      currentView: "dashboard",
      isSidebarCollapsed: false,
      tablePage: 1
    },
    tableRowsPerPage: 20
  };
})(window);

