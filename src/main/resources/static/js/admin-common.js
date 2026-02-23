(function (window) {
  const sharedConfig = window.AdminSharedConfig || {};

  const fallbackTabs = [
    { id: "basic", label: "기본설정", icon: "icon2.png" },
    { id: "posts", label: "게시판관리", icon: "icon3.png" },
    { id: "news", label: "연혁관리", icon: "icon11.png" }
  ];
  const fallbackSubmenus = {
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
  const fallbackTitleMap = {
    "admin-settings": "관리자설정",
    "board-settings": "팝업관리",
    promo: "홍보자료",
    notice: "공지사항",
    archive: "자료실",
    "its-story": "ITS Story",
    news: "연혁관리"
  };
  const fallbackTitleSubMap = {
    "admin-settings": "관리자를 추가/수정/삭제 합니다.",
    "board-settings": "팝업관리 팝업을 추가/수정/삭제 합니다.",
    promo: "홍보자료를 추가/수정/삭제 합니다.",
    notice: "공지사항을 추가/수정/삭제 합니다.",
    archive: "자료실을 추가/수정/삭제 합니다.",
    "its-story": "ITS Story를 추가/수정/삭제 합니다.",
    news: ""
  };
  const fallbackPaths = {
    tabs: {
      basic: "basic/admin_list",
      posts: "bbs/data",
      news: "year/year_list"
    },
    submenus: {
      "admin-settings": "basic/admin_list",
      "board-settings": "basic/popup_list",
      promo: "bbs/data",
      notice: "bbs/its_notice",
      archive: "bbs/download",
      "its-story": "bbs/story",
      news: "year/year_list"
    }
  };
  const navConfig = {
    tabs: sharedConfig.tabConfig || fallbackTabs,
    submenus: sharedConfig.submenuConfig || fallbackSubmenus,
    titleMap: sharedConfig.titleMap || fallbackTitleMap,
    subTitleMap: sharedConfig.titleSubMap || fallbackTitleSubMap,
    paths: sharedConfig.submenuPathMap
      ? {
          tabs: fallbackPaths.tabs,
          submenus: sharedConfig.submenuPathMap
        }
      : fallbackPaths
  };

  const refs = {
    tabButtons: null,
    submenuButtons: null,
    sidebarTitle: null,
    breadcrumb: null,
    pageTitleMain: null,
    dashboardView: null,
    recentView: null,
    adminHomeLink: null,
    collapseBtn: null,
    sidebar: null
  };

  const fallbackStateMap = {
    "admin-list": {
      activeTab: "basic",
      activeSubMenu: "admin-settings"
    },
    "popup-list": {
      activeTab: "basic",
      activeSubMenu: "board-settings"
    },
    data: {
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
    },
    index: {
      activeTab: "basic",
      activeSubMenu: "admin-settings"
    }
  };

  const stateBag = {
    state: null,
    callbacks: {
      renderTabs: null,
      renderSubmenu: null,
      renderTitle: null,
      renderDashboardTable: null,
      renderRecentPosts: null,
      renderStats: null,
      renderView: null,
      renderSidebarState: null
    }
  };

  const pageStateMap = sharedConfig.pageStateMap || fallbackStateMap;

  const assetPrefix = sharedConfig.assetPrefix || "/asset/img/";

  const normalizeState = (state) => {
    const pageData = document.body?.dataset?.page || "index";
    const defaults = pageStateMap[pageData];
    if (defaults && !state.activeTab) {
      state.activeTab = defaults.activeTab;
    }
    if (defaults && !state.activeSubMenu) {
      state.activeSubMenu = defaults.activeSubMenu;
    }
    if (!state.activeTab) {
      state.activeTab = "basic";
    }
    if (!state.activeSubMenu) {
      state.activeSubMenu = "admin-settings";
    }
  };

  const createState = (seed = {}) => {
    const state = {
      activeTab: "",
      activeSubMenu: "",
      currentView: "dashboard",
      isSidebarCollapsed: false,
      tablePage: 1,
      ...seed
    };
    normalizeState(state);
    return state;
  };

  const createPaginationController = (state, options = {}) => {
    const getTotalPages = options.getTotalPages || (() => 1);
    const isEnabled = options.isEnabled || (() => true);
    const onPageChange = options.onPageChange || (() => {
      if (typeof window.render === "function") {
        window.render();
      }
    });
    const pageCurrent = options.pageCurrent || document.querySelector(".page-current");
    const pageCurrentBox = options.pageCurrentBox || document.querySelector(".page-current-box");
    const pageLeft2 = options.pageLeft2 || document.querySelector(".page-arrow-l2");
    const pageLeft1 = options.pageLeft1 || document.querySelector(".page-arrow-l1");
    const pageRight1 = options.pageRight1 || document.querySelector(".page-arrow-r1");
    const pageRight2 = options.pageRight2 || document.querySelector(".page-arrow-r2");
    const renderPageButtons =
      options.renderPageButtons || ((currentPageNumber, totalPages) => {
        if (pageCurrent) {
          pageCurrent.textContent = String(currentPageNumber);
        }
      });

    const resolveTotalPages = () => {
      const totalPages = Number(getTotalPages());
      if (!Number.isFinite(totalPages)) {
        return 1;
      }
      const safeTotal = Math.trunc(totalPages);
      return safeTotal > 0 ? safeTotal : 1;
    };

    const clampPage = (page, totalPages) => {
      const safePage = Number.isFinite(Number(page)) ? Math.trunc(Number(page)) : 1;
      if (safePage < 1) {
        return 1;
      }
      if (safePage > totalPages) {
        return totalPages;
      }
      return safePage;
    };

    const setPage = (nextPage) => {
      if (!isEnabled()) {
        return;
      }
      const totalPages = resolveTotalPages();
      const page = clampPage(nextPage, totalPages);
      if (state.tablePage === page) {
        return;
      }
      state.tablePage = page;
      onPageChange(page);
    };

    const update = () => {
      const totalPages = resolveTotalPages();
      const enabled = isEnabled();
      const currentPage = clampPage(state.tablePage || 1, totalPages);
      state.tablePage = currentPage;
      renderPageButtons(currentPage, totalPages, state);

      if (!enabled) {
        if (pageLeft2) {
          pageLeft2.disabled = true;
        }
        if (pageLeft1) {
          pageLeft1.disabled = true;
        }
        if (pageRight1) {
          pageRight1.disabled = true;
        }
        if (pageRight2) {
          pageRight2.disabled = true;
        }
        return;
      }

      if (pageLeft2) {
        pageLeft2.disabled = currentPage <= 1;
      }
      if (pageLeft1) {
        pageLeft1.disabled = currentPage <= 1;
      }
      if (pageRight1) {
        pageRight1.disabled = currentPage >= totalPages;
      }
      if (pageRight2) {
        pageRight2.disabled = currentPage >= totalPages;
      }
    };

    if (!state.__adminPaginationBind) {
      if (pageLeft2) {
        pageLeft2.addEventListener("click", () => {
          setPage(1);
        });
      }
      if (pageLeft1) {
        pageLeft1.addEventListener("click", () => {
          setPage(state.tablePage - 1);
        });
      }
      if (pageRight1) {
        pageRight1.addEventListener("click", () => {
          setPage(state.tablePage + 1);
        });
      }
      if (pageRight2) {
        pageRight2.addEventListener("click", () => {
          setPage(resolveTotalPages());
        });
      }
      if (pageCurrentBox) {
        pageCurrentBox.addEventListener("click", (event) => {
          const button = event.target.closest("button[data-page]");
          if (!button) {
            return;
          }
          const page = Number(button.dataset.page);
          if (Number.isFinite(page)) {
            setPage(page);
          }
        });
      }

      state.__adminPaginationBind = true;
    }

    return {
      setPage,
      update,
      resolveTotalPages
    };
  };

  const appRoot = () => {
    const pathname = window.location.pathname || "";
    const publicMatch = pathname.match(/(.*\/public\/html)(?=\/|$)/);
    if (publicMatch) {
      return publicMatch[1];
    }
    const match = pathname.match(/\/(basic|bbs|year)\//);
    return match ? pathname.slice(0, match.index) : "";
  };

  const normalizePath = (target) => {
    const root = appRoot();
    return root ? `${root}/${target}` : `/${target}`;
  };

  const isCurrentPath = (target) => {
    const path = window.location.pathname || "";
    return path.endsWith(`/${target}`);
  };

  const collectRefs = () => {
    refs.tabButtons = document.getElementById("tabButtons");
    refs.submenuButtons = document.getElementById("submenuButtons");
    refs.sidebarTitle = document.getElementById("sidebarTitle");
    refs.breadcrumb = document.getElementById("pageTitleBread");
    refs.pageTitleMain = document.getElementById("pageTitleMain");
    refs.dashboardView = document.getElementById("dashboardView");
    refs.recentView = document.getElementById("recentView");
    refs.adminHomeLink = document.getElementById("adminHomeLink");
    refs.collapseBtn = document.getElementById("collapseBtn");
    refs.sidebar = document.querySelector(".sidebar");
  };

  const bindLayoutEvents = () => {
    const state = stateBag.state;
    if (!state) {
      return;
    }

    if (refs.adminHomeLink && !refs.adminHomeLink.__adminCommonBind) {
      refs.adminHomeLink.__adminCommonBind = true;
      refs.adminHomeLink.addEventListener("click", (e) => {
        e.preventDefault();
        const target = navConfig.paths.tabs.basic || "basic/admin_list";
        window.location.href = normalizePath(target);
      });
    }

    if (refs.collapseBtn && !refs.collapseBtn.__adminCommonBind) {
      refs.collapseBtn.__adminCommonBind = true;
      refs.collapseBtn.addEventListener("click", () => {
        state.isSidebarCollapsed = !state.isSidebarCollapsed;
        if (typeof stateBag.callbacks.renderSidebarState === "function") {
          stateBag.callbacks.renderSidebarState();
        }
      });
    }
  };

  const renderTabs = () => {
    const state = stateBag.state;
    if (!state || !refs.tabButtons) return;

    refs.tabButtons.innerHTML = "";

    navConfig.tabs.forEach((tab) => {
      const isActive = state.activeTab === tab.id;
      const button = document.createElement("button");
      button.type = "button";
      button.className = `tab-btn${isActive ? " active" : ""}`;
      const icon = /\.(png|jpg|jpeg|webp|gif|svg)$/i.test(tab.icon)
        ? `<img src="${assetPrefix}${tab.icon}" alt="${tab.label} 아이콘" />`
        : tab.icon;
      button.innerHTML = `<span class="tab-icon">${icon}</span><span class="tab-label">${tab.label}</span>`;

      button.addEventListener("click", () => {
        const target = navConfig.paths.tabs[tab.id];
        if (target && !isCurrentPath(target)) {
          window.location.href = normalizePath(target);
          return;
        }

        state.activeTab = tab.id;
        const firstSubMenu = (navConfig.submenus[state.activeTab] || [])[0];
        state.activeSubMenu = firstSubMenu ? firstSubMenu.id : state.activeTab;
        state.currentView = "dashboard";
        if (typeof state.tablePage === "number") {
          state.tablePage = 1;
        }
        window.render();
      });

      refs.tabButtons.appendChild(button);
    });
  };

  const renderSubmenu = () => {
    const state = stateBag.state;
    if (!state || !refs.submenuButtons || !refs.sidebarTitle) return;

    const items = navConfig.submenus[state.activeTab] || [];
    const hasSubmenu = items.length > 0;

    refs.sidebarTitle.style.display = hasSubmenu ? "" : "none";
    refs.submenuButtons.style.display = hasSubmenu ? "" : "none";
    refs.submenuButtons.innerHTML = "";

    if (!hasSubmenu) return;

    items.forEach((item) => {
      const button = document.createElement("button");
      button.type = "button";
      button.className = `submenu-btn${state.activeSubMenu === item.id ? " active" : ""}`;
      button.dataset.submenuId = item.id;
      button.textContent = item.label;

      button.addEventListener("click", () => {
        const target = navConfig.paths.submenus[item.id];
        state.activeSubMenu = item.id;
        state.currentView = "dashboard";
        if (typeof state.tablePage === "number") {
          state.tablePage = 1;
        }

        if (target && !isCurrentPath(target)) {
          window.location.href = normalizePath(target);
          return;
        }

        window.render();
      });

      refs.submenuButtons.appendChild(button);
    });
  };

  const renderTitle = () => {
    const state = stateBag.state;
    if (!state || !refs.breadcrumb || !refs.pageTitleMain || !refs.sidebarTitle) return;

    const activeTab = navConfig.tabs.find((tab) => tab.id === state.activeTab)?.label || "관리";
    const title = navConfig.titleMap[state.activeSubMenu] || "관리";
    const subTitle =
      navConfig.subTitleMap[state.activeSubMenu] || "관리자를 추가/수정/삭제 합니다.";

    refs.breadcrumb.textContent = activeTab;
    refs.pageTitleMain.innerHTML = `${title}<span>${subTitle}</span>`;

    refs.sidebarTitle.textContent =
      state.activeTab === "basic"
        ? "기본설정"
        : state.activeTab === "posts"
        ? "게시판관리"
        : "연혁관리";
  };

  const renderView = () => {
    const state = stateBag.state;
    if (!state || !refs.dashboardView || !refs.recentView) {
      return;
    }

    if (state.currentView === "dashboard") {
      refs.dashboardView.classList.add("active");
      refs.recentView.classList.remove("active");
      return;
    }

    refs.dashboardView.classList.remove("active");
    refs.recentView.classList.add("active");
  };

  const renderSidebarState = () => {
    const state = stateBag.state;
    if (!state || !refs.sidebar || !refs.collapseBtn) {
      return;
    }

    refs.sidebar.classList.toggle("collapsed", state.isSidebarCollapsed);
    refs.collapseBtn.textContent = state.isSidebarCollapsed ? "▶" : "◀";
    refs.collapseBtn.setAttribute(
      "aria-label",
      state.isSidebarCollapsed ? "사이드바 펼치기" : "사이드바 접기"
    );
  };

  const drawContent = () => {
    if (typeof stateBag.callbacks.renderTabs === "function") {
      stateBag.callbacks.renderTabs();
    }
    if (typeof stateBag.callbacks.renderSubmenu === "function") {
      stateBag.callbacks.renderSubmenu();
    }
    if (typeof stateBag.callbacks.renderTitle === "function") {
      stateBag.callbacks.renderTitle();
    }

    if (typeof stateBag.callbacks.renderDashboardTable === "function") {
      stateBag.callbacks.renderDashboardTable();
    }
    if (typeof stateBag.callbacks.renderRecentPosts === "function") {
      stateBag.callbacks.renderRecentPosts();
    }
    if (typeof stateBag.callbacks.renderStats === "function") {
      stateBag.callbacks.renderStats();
    }
    if (typeof stateBag.callbacks.renderView === "function") {
      stateBag.callbacks.renderView();
    }
    if (typeof stateBag.callbacks.renderSidebarState === "function") {
      stateBag.callbacks.renderSidebarState();
    }
  };

  const installRenderer = () => {
    if (window.render && window.render.__adminCommon === true) {
      return;
    }

    const nextRender = () => {
      drawContent();
    };

    nextRender.__adminCommon = true;
    window.render = nextRender;
  };

  const init = (opts = {}) => {
    const state = opts.state;
    if (!state) {
      return;
    }

    stateBag.state = state;
    stateBag.callbacks.renderTabs = opts.renderTabs || renderTabs;
    stateBag.callbacks.renderSubmenu = opts.renderSubmenu || renderSubmenu;
    stateBag.callbacks.renderTitle = opts.renderTitle || renderTitle;
    stateBag.callbacks.renderDashboardTable =
      opts.renderDashboardTable || window.renderDashboardTable || null;
    stateBag.callbacks.renderRecentPosts =
      opts.renderRecentPosts || window.renderRecentPosts || null;
    stateBag.callbacks.renderStats = opts.renderStats || window.renderStats || null;
    stateBag.callbacks.renderView = opts.renderView || renderView;
    stateBag.callbacks.renderSidebarState =
      opts.renderSidebarState || renderSidebarState;

    normalizeState(state);
    if (!state.currentView) {
      state.currentView = "dashboard";
    }
    if (typeof state.isSidebarCollapsed !== "boolean") {
      state.isSidebarCollapsed = false;
    }

    collectRefs();
    bindLayoutEvents();
    installRenderer();
    drawContent();
  };

  window.AdminLayout = {
    init,
    createState,
    normalizeState,
    normalizePath,
    createPaginationController,
    assetPrefix,
    renderTabs,
    renderSubmenu,
    renderTitle,
    renderView,
    renderSidebarState
  };
})(window);
