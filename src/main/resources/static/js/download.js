const sharedAdminConfig = window.AdminSharedConfig || {};
const {
  tabConfig = [],
  submenuConfig = {},
  titleMap = {},
  titleSubMap = {},
  posts = [],
  stats = [],
  stateDefaults = {
    currentView: "dashboard",
    isSidebarCollapsed: false,
    tablePage: 1
  },
  pageStateMap = {},
  dashboardTableMap: sharedDashboardTableMap = {},
  submenuPathMap = {},
  tableRowsPerPage = 20
} = sharedAdminConfig;

const normalizePath = AdminLayout.normalizePath;
const currentPage = document.body?.dataset?.page;
const state = AdminLayout.createState({
  ...stateDefaults,
  ...(pageStateMap[currentPage] || {})
});

const dashboardTableMap = {
  ...sharedDashboardTableMap,
  archive: {
    headers: ["번호", "제목", "작성자"],
    rows: [
      ["4", "아이티에스컨버젼스 벤처기업확인서", "관리자"],
      ["3", "WEYTEC 솔루션 End of Sales 안내", "관리자"],
      ["2", "ITS convergence 이력서 (신입, 경력)", "관리자"],
      ["1", "아이티에스컨버젼스 사업자등록증", ""]
    ],
    sortByDescNumber: true
  }
};
const assetPrefix = sharedAdminConfig.assetPrefix || "/asset/img/";

const tabButtons = document.getElementById("tabButtons");
const submenuButtons = document.getElementById("submenuButtons");
const sidebarTitle = document.getElementById("sidebarTitle");
const pageTitleBread = document.getElementById("pageTitleBread");
const pageTitleMain = document.getElementById("pageTitleMain");
const dashboardView = document.getElementById("dashboardView");
const recentView = document.getElementById("recentView");
const adminHomeLink = document.getElementById("adminHomeLink");
const postList = document.getElementById("postList");
const statsList = document.getElementById("statsList");
const sidebar = document.querySelector(".sidebar");
const collapseBtn = document.getElementById("collapseBtn");
const tableHead = document.getElementById("dataTableHead");
const tableBody = document.getElementById("dataTableBody");
const tableActionBtn = document.getElementById("tableActionBtn");
const paginationControls = document.querySelector(".pagination-controls");
const pageLeft2 = document.querySelector(".page-arrow-l2");
const pageLeft1 = document.querySelector(".page-arrow-l1");
const pageRight1 = document.querySelector(".page-arrow-r1");
const pageRight2 = document.querySelector(".page-arrow-r2");
const pageCurrent = document.querySelector(".page-current");

const paginationController = AdminLayout.createPaginationController(state, {
  pageCurrent,
  pageLeft2,
  pageLeft1,
  pageRight1,
  pageRight2,
  isEnabled: () => state.activeTab === "posts" && state.activeSubMenu === "archive",
  getTotalPages: () => {
    const config = getDashboardTableConfig();
    const rows = config.rows || [];
    return Math.max(1, Math.ceil(rows.length / tableRowsPerPage));
  },
  onPageChange: renderDashboardTable
});

function renderTabs() {
  tabButtons.innerHTML = "";
  tabConfig.forEach((tab) => {
    const button = document.createElement("button");
    button.type = "button";
    button.className = `tab-btn${state.activeTab === tab.id ? " active" : ""}`;
    const icon = /\.(png|jpg|jpeg|webp|gif|svg)$/i.test(tab.icon)
      ? `<img src="${assetPrefix}${tab.icon}" alt="${tab.label} 아이콘" />`
      : tab.icon;
    button.innerHTML = `<span class="tab-icon">${icon}</span><span class="tab-label">${tab.label}</span>`;
    button.addEventListener("click", () => {
      if (tab.id === "basic") {
        if (!window.location.pathname.endsWith("/basic/admin_list")) {
          window.location.href = normalizePath("basic/admin_list");
          return;
        }
      }

      if (tab.id === "posts") {
        if (!window.location.pathname.endsWith("/bbs/data")) {
          window.location.href = normalizePath("bbs/data");
          return;
        }
      }

      if (tab.id === "news") {
        if (!window.location.pathname.endsWith("/year/year_list")) {
          window.location.href = normalizePath("year/year_list");
          return;
        }
      }
      state.activeTab = tab.id;
      const firstSubMenu = (submenuConfig[tab.id] || [])[0];
      state.activeSubMenu = firstSubMenu ? firstSubMenu.id : tab.id;
      state.currentView = "dashboard";
      render();
    });
    tabButtons.appendChild(button);
  });
}

function renderSubmenu() {
  submenuButtons.innerHTML = "";
  const items = submenuConfig[state.activeTab] || [];
  const hasSubmenu = items.length > 0;

  sidebarTitle.style.display = hasSubmenu ? "" : "none";
  submenuButtons.style.display = hasSubmenu ? "" : "none";

  items.forEach((item) => {
    const button = document.createElement("button");
    button.type = "button";
    button.className = `submenu-btn${state.activeSubMenu === item.id ? " active" : ""}`;
    button.dataset.submenuId = item.id;
    button.textContent = item.label;
    button.addEventListener("click", () => {
      const target = submenuPathMap[item.id];
      if (target) {
        const currentPath = window.location.pathname || "";
        if (!currentPath.endsWith(target)) {
          window.location.href = normalizePath(target);
          return;
        }
      }
      state.activeSubMenu = item.id;
      render();
    });
    submenuButtons.appendChild(button);
  });
}

function renderTitles() {
  const title = titleMap[state.activeSubMenu] || "관리";
  const activeTabLabel =
    tabConfig.find((tab) => tab.id === state.activeTab)?.label || "관리";
  pageTitleBread.textContent = activeTabLabel;
  const subTitle =
    titleSubMap[state.activeSubMenu] ?? "관리자를 추가/수정/삭제 합니다.";
  pageTitleMain.innerHTML = `${title}<span>${subTitle}</span>`;
  pageTitleMain.style.display = "";
  if (pageTitleMain.nextElementSibling?.classList?.contains("divider")) {
    pageTitleMain.nextElementSibling.style.display = "";
  }
  sidebarTitle.textContent =
    state.activeTab === "basic"
      ? "기본설정"
      : state.activeTab === "posts"
      ? "게시판관리"
      : "연혁관리";
}

function getDashboardTableConfig() {
  if (state.activeTab === "basic" && state.activeSubMenu === "board-settings") {
    return dashboardTableMap.popup;
  }
  if (state.activeTab === "posts" && state.activeSubMenu === "archive") {
    return dashboardTableMap.archive;
  }
  return dashboardTableMap.admin;
}

function createNoticeActionButton(label) {
  const button = document.createElement("button");
  button.type = "button";
  button.className = "add-btn promo-action-btn";
  button.textContent = label;
  return button;
}

function renderActionButtons(isArchiveLike) {
  if (!tableActionBtn) {
    return;
  }

  const actionArea = tableActionBtn.parentElement || tableActionBtn;
  const pagination = actionArea.closest(".pagination");
  const dashboardContent = pagination?.closest(".dashboard-content");

  if (isArchiveLike) {
    if (!pagination || !dashboardContent) {
      return;
    }

    pagination.classList.add("has-notice-search");
    let controlsPanel = dashboardContent.querySelector(".notice-controls-panel");
    if (!controlsPanel) {
      controlsPanel = document.createElement("div");
      controlsPanel.className = "notice-controls-panel";
      const anchor = pagination.nextSibling;
      const insertionParent = pagination.parentNode || dashboardContent;
      if (anchor && insertionParent === anchor.parentNode) {
        insertionParent.insertBefore(controlsPanel, anchor);
      } else {
        insertionParent.appendChild(controlsPanel);
      }
    }

    let searchWrap = controlsPanel.querySelector(".notice-search-wrap");
    if (!searchWrap) {
      searchWrap = document.createElement("div");
      searchWrap.className = "notice-search-wrap";
      controlsPanel.appendChild(searchWrap);
    }
    searchWrap.innerHTML = `
      <select class="notice-search-select" name="searchopt" aria-label="공지사항 검색 조건">
        <option value="subject">제 목</option>
        <option value="content">내 용</option>
        <option value="subcon">제목 + 내용</option>
        <option value="name">작성자</option>
        <option value="memid">아이디</option>
      </select>
      <input
        type="text"
        class="notice-search-input"
        placeholder="제목으로 검색"
        aria-label="자료실 제목 검색"
      />
      <button type="button" class="notice-search-btn">검색</button>
    `;

    controlsPanel.appendChild(actionArea);

    actionArea.innerHTML = "";
    actionArea.appendChild(createNoticeActionButton("순서변경"));

    const rightArea = document.createElement("span");
    rightArea.className = "promo-right-actions";
    rightArea.appendChild(createNoticeActionButton("리스트"));
    rightArea.appendChild(createNoticeActionButton("글쓰기"));
    actionArea.appendChild(rightArea);
    return;
  }

  if (pagination) {
    const controlsPanel = pagination
      .closest(".dashboard-content")
      ?.querySelector(".notice-controls-panel");
    if (controlsPanel) {
      if (controlsPanel.contains(actionArea)) {
        pagination.appendChild(actionArea);
      }
      controlsPanel.remove();
    }
    pagination.classList.remove("has-notice-search");
  }

  tableActionBtn.className = "add-btn";
  tableActionBtn.innerHTML = `<span class="icon"><img src="/asset/img/plus.gif" alt="" /></span>${dashboardTableMap[state.activeTab === "basic" ? "admin" : "popup"].actionLabel}`;
}

function renderDashboardTable() {
  const config = getDashboardTableConfig();
  const isArchive = state.activeTab === "posts" && state.activeSubMenu === "archive";
  const hasActionColumn = config.headers.includes("기능");
  const rows = (config.rows || []).slice().sort((a, b) => {
    if (config.sortByDescNumber) {
      return Number(b[0] || 0) - Number(a[0] || 0);
    }
    return 0;
  });
  const totalPages = paginationController.resolveTotalPages();
  state.tablePage = Math.min(Math.max(state.tablePage || 1, 1), totalPages);
  paginationController.update();
  const start = (state.tablePage - 1) * tableRowsPerPage;
  const end = start + tableRowsPerPage;
  const visibleRows = rows.slice(start, end);

  if (paginationControls) {
    paginationControls.style.display = "flex";
  }

  const headerHTML = `<tr>${config.headers
    .map((header, index) =>
      index === 1 && isArchive
        ? `<th class="notice-title-head">${header}</th>`
        : index === config.headers.length - 1 && hasActionColumn
        ? `<th class="center">${header}</th>`
        : `<th>${header}</th>`
    )
    .join("")}</tr>`;
  tableHead.innerHTML = headerHTML;

  tableBody.innerHTML = visibleRows
    .map((row) => {
      const cols = config.headers.map((_, idx) =>
        idx === 1 && isArchive
          ? `<td class="notice-title">${row[idx] || ""}<img src="${assetPrefix}file.gif" alt="" class="notice-file-icon" /></td>`
          : idx === config.headers.length - 1 && hasActionColumn
          ? `<td class="center"><div class="row-actions"><button type="button" class="edit-btn">수정</button><button type="button" class="delete-btn">삭제</button></div></td>`
          : `<td>${row[idx] || ""}</td>`
      );
      return `<tr>${cols.join("")}</tr>`;
    })
    .join("");

  renderActionButtons(isArchive);
}

function renderRecentPosts() {
  if (!postList || !statsList) {
    return;
  }
  postList.innerHTML = "";
  posts.forEach((title, index) => {
    const item = document.createElement("div");
    item.className = "post-item";
    item.innerHTML = `
      <div class="post-title">${title}</div>
      <div class="post-date">${index === 5 ? "2025-06-18" : "2026-02-06"}</div>
    `;
    postList.appendChild(item);
  });

  const more = document.createElement("div");
  more.className = "more-wrap";
  more.innerHTML = `<button type="button" class="more-btn">⌄ 더보기</button>`;
  postList.appendChild(more);
}

function renderStats() {
  if (!statsList) {
    return;
  }
  statsList.innerHTML = "";
  stats.forEach((stat) => {
    const card = document.createElement("div");
    card.className = "stat-card";
    card.innerHTML = `
      <div class="stat-label">${stat.label}</div>
      <div class="stat-value">${stat.value}</div>
      <div class="stat-unit">개</div>
    `;
    statsList.appendChild(card);
  });
}

function renderView() {
  if (state.currentView === "dashboard") {
    dashboardView.classList.add("active");
    if (recentView) {
    recentView.classList.remove("active");
  }
  } else {
    dashboardView.classList.remove("active");
    if (recentView) {
    recentView.classList.add("active");
  }
  }
}

function renderSidebarState() {
  sidebar.classList.toggle("collapsed", state.isSidebarCollapsed);
  collapseBtn.textContent = state.isSidebarCollapsed ? "▶" : "◀";
  collapseBtn.setAttribute(
    "aria-label",
    state.isSidebarCollapsed ? "사이드바 펼치기" : "사이드바 접기"
  );
}

function render() {
  if (typeof window.render === "function") {
    window.render();
  }
}

AdminLayout.init({
  state,
  renderDashboardTable,
  renderRecentPosts,
  renderStats
});
