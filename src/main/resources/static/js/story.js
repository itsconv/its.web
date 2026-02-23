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
  story: {
    headers: ["번호", "제목", "작성자"],
    rows: [
      ["1", "외교부·복지부 공무원노조, 보은에서 의료 사각지대 메웠다", ""],
      ["2", "아이티에스컨버젼스, (사)좋은변화에 결식위기가정 지원 위한 기부금 전달", ""],
      ["3", "(주)아이티에스컨버젼스, 마약예방 사회공헌 나서", ""],
      ["4", "(주)아이티에스컨버젼스, '마약예방 사회공헌 활동'", ""],
      ["5", "아이티에스컨버젼스, 행정안전부장관상 수상…국가 안전정책 발전 기여 공로", ""],
      ["6", "[소식] 23.03.18 2023 ITS 봄맞이 대청소(feat.커피강좌) (1)", ""],
      ["7", "[소식] 23.02.03 계묘년 설맞이 사진이벤트 (1)", ""],
      ["8", "[행사] 2022년 ITS 먹놀놀먹 송년회 (1)", ""],
      ["9", "[행사]ITS 창립 23주년 기념행사 (1)", ""],
      ["10", "[소식] 22.08.12 말복맞이 투표 이벤트 (1)", ""],
      ["11", "[행사]22.05.21 팔당댐 라이딩 (4) (1)", ""],
      ["12", "[행사]22.05.21 팔당댐 라이딩 (3) (1)", ""],
      ["13", "[행사]22.05.21 팔당댐 라이딩 (2) (1)", ""],
      ["14", "[행사]22.05.21 팔당댐 라이딩 (1)", ""],
      ["15", "[소식]22.04.25 조직 변화와 혁신 성공사례 세미나 (1)", ""],
      ["16", "[행사]22.04.22 사내 카페 공모전 시상식 (1)", ""],
      ["17", "[행사]22.04.22 울림 색소폰 연주회 EP01 (1)", ""],
      ["18", "[소식]22.01.11 ITS본사 사무실 리모델링 공사 (1)", ""],
      ["19", "[행사]2021년 대한민국 소프트웨어대전 참가 (1)", ""],
      ["20", "[소식]21.10.18 2021 고객만족도조사 리뷰 (1)", ""],
      ["21", "[행사]ITS 창립 22주년 기념행사", ""],
      ["22", "[소식]20.10.13 ITS-GS ITM-위엠비, 업무협약 체결식 (2)", ""],
      ["23", "[소식]20.08.26 ITS본사 사무실 코로나19 방역실시", ""],
      ["24", "[행사]2020년 ITS 어울림행사", ""],
      ["25", "[행사]ITS 창립 20주년 기념행사 \"청춘, 동고동락\"", ""],
      ["26", "[행사]ITS 창립 20주년 기념 힐링여행 \"더큰 도약을 위한, 쉼표\" (2)", ""],
      ["27", "[행사]ITS 창립 20주년 기념 힐링여행 \"더큰 도약을 위한, 쉼표\" (1)", ""],
      ["28", "[대외행사] Korea Forex Club 송년의 밤 행사 ITS ", ""],
      ["29", "[행사]ITS 창립 19주년 기념 팔당댐 라이딩(3)", ""],
      ["30", "[행사]ITS 창립 19주년 기념 팔당댐 라이딩(2)", ""],
      ["31", "[행사]ITS 창립 19주년 기념 팔당댐 라이딩(1) (1)", ""]
    ],
    actionLabel: "순서변경"
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
const actionArea = document.querySelector(".action-area");
const tableWrap = tableBody.closest(".table-wrap");
const paginationControls = document.querySelector(".pagination-controls");
const pageLeft2 = document.querySelector(".page-arrow-l2");
const pageLeft1 = document.querySelector(".page-arrow-l1");
const pageRight1 = document.querySelector(".page-arrow-r1");
const pageRight2 = document.querySelector(".page-arrow-r2");
const pageCurrentBox = document.querySelector(".page-current-box");

const paginationController = AdminLayout.createPaginationController(state, {
  pageLeft2,
  pageLeft1,
  pageRight1,
  pageRight2,
  pageCurrentBox,
  isEnabled: () => state.activeSubMenu === "its-story",
  renderPageButtons: (currentPageNumber, totalPages) =>
    renderPageButtons(currentPageNumber, totalPages),
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
      state.tablePage = 1;
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
      state.tablePage = 1;
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
  if (
    state.activeTab === "posts" &&
    (state.activeSubMenu === "promo" || state.activeSubMenu === "its-story")
  ) {
    return state.activeSubMenu === "its-story"
      ? dashboardTableMap.story
      : dashboardTableMap.promo;
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

function renderNoticeStylePanel(isStoryLayout, config) {
  const pagination = document.querySelector(".pagination");
  const dashboardContent = pagination?.closest(".dashboard-content");
  const actionArea = document.querySelector(".pagination .action-area");

  if (!pagination || !dashboardContent || !actionArea) {
    return;
  }

  if (!isStoryLayout) {
    const existingPanel = dashboardContent.querySelector(".notice-controls-panel");
    if (existingPanel) {
      if (existingPanel.contains(actionArea)) {
        pagination.appendChild(actionArea);
      }
      existingPanel.remove();
    }
    pagination.classList.remove("has-notice-search");
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
    <select class="notice-search-select" name="searchopt" aria-label="자료실 검색 조건">
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

  return controlsPanel;
}

function renderPageButtons(currentPageNumber, totalPages) {
  if (!pageCurrentBox || totalPages <= 1) {
    if (pageCurrentBox) {
      pageCurrentBox.innerHTML = `<button type="button" class="page-current" aria-current="page">1</button>`;
    }
    return;
  }
  let markup = "";
  for (let page = 1; page <= totalPages; page += 1) {
    if (page === currentPageNumber) {
      markup += `<button type="button" class="page-current" aria-current="page">${page}</button>`;
    } else {
      markup += `<button type="button" class="page-number" data-page="${page}">${page}</button>`;
    }
  }
  pageCurrentBox.innerHTML = markup;
}

function renderDashboardTable() {
  const config = getDashboardTableConfig();
  const rows = config.rows || [];
  const isPromoLayout = state.activeTab === "posts" && (state.activeSubMenu === "promo" || state.activeSubMenu === "its-story");
  const isStoryLayout = state.activeSubMenu === "its-story";
  const totalPages = paginationController.resolveTotalPages();
  state.tablePage = Math.min(Math.max(state.tablePage || 1, 1), totalPages);
  paginationController.update();
  const start = (state.tablePage - 1) * tableRowsPerPage;
  const end = start + tableRowsPerPage;
  const visibleRows = rows.slice(start, end);
  const table = tableHead.parentElement.closest("table");

  if (isPromoLayout) {
    renderNoticeStylePanel(isStoryLayout, config);

    if (paginationControls) {
      paginationControls.style.display = isStoryLayout ? "flex" : "none";
    }

    if (table) {
      table.style.display = "none";
    }

    let promoGrid = tableWrap ? tableWrap.querySelector(".promo-grid") : null;
    if (!promoGrid && tableWrap) {
      tableWrap.insertAdjacentHTML(
        "beforeend",
        '<div id="promoGrid" class="promo-grid"></div>'
      );
      promoGrid = tableWrap.querySelector(".promo-grid");
    }

    if (promoGrid) {
      promoGrid.style.display = "block";
      const gridItems = visibleRows;
      const gridRows = [];
      for (let i = 0; i < gridItems.length; i += 4) {
        const rowItems = gridItems.slice(i, i + 4).map((row) => {
          const title = row[1] || "";
          return `<div class="promo-cell"><span class="promo-title">${title}</span></div>`;
        });
        while (rowItems.length < 4) {
          rowItems.push('<div class="promo-cell"></div>');
        }
        gridRows.push(`<div class="promo-row">${rowItems.join("")}</div>`);
      }
      promoGrid.innerHTML = gridRows.join("");
    }

    if (actionArea && !isStoryLayout) {
      const createPromoButton = (label) => {
        const button = document.createElement("button");
        button.type = "button";
        button.className = "add-btn promo-action-btn";
        button.textContent = label;
        return button;
      };

      actionArea.innerHTML = "";
      actionArea.appendChild(createPromoButton(config.actionLabel || "순서변경"));
      actionArea.innerHTML += `<span class="promo-right-actions">
        ${createPromoButton("리스트").outerHTML}
        ${createPromoButton("글쓰기").outerHTML}
      </span>`;
    } else if (tableActionBtn) {
      tableActionBtn.classList.add("promo-action-btn");
      tableActionBtn.textContent = config.actionLabel;
    }
    return;
  }

  if (paginationControls) {
    paginationControls.style.display = "flex";
  }

  if (table) {
    table.style.display = "";
  }

  const existingPromoGrid = tableWrap ? tableWrap.querySelector(".promo-grid") : null;
  if (existingPromoGrid) {
    existingPromoGrid.remove();
  }

  const headerHTML = `<tr>${config.headers
    .map((header, index) =>
      index === config.headers.length - 1
        ? `<th class="center">${header}</th>`
        : `<th>${header}</th>`
    )
    .join("")}</tr>`;
  tableHead.innerHTML = headerHTML;

  tableBody.innerHTML = visibleRows
    .map((row) => {
      const cols = config.headers.map((_, idx) =>
        idx === config.headers.length - 1
          ? `<td class="center"><div class="row-actions"><button type="button" class="edit-btn">수정</button><button type="button" class="delete-btn">삭제</button></div></td>`
          : `<td>${row[idx] || ""}</td>`
      );
      return `<tr>${cols.join("")}</tr>`;
    })
    .join("");

  if (actionArea) {
    tableActionBtn.className = "add-btn";
    tableActionBtn.innerHTML = `<span class="icon"><img src="/asset/img/plus.gif" alt="" /></span>${config.actionLabel}`;
    actionArea.innerHTML = "";
    actionArea.appendChild(tableActionBtn);
  } else if (tableActionBtn) {
    tableActionBtn.className = "add-btn";
    tableActionBtn.innerHTML = `<span class="icon"><img src="/asset/img/plus.gif" alt="" /></span>${config.actionLabel}`;
  }
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
