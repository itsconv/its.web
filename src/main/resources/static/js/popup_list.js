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
  popup: {
    headers: ["번호", "제목", "공지기간", "등록일", "기능"],
    rows: [
      ["4", "JAVA 개발자 채용 팝업", "2024-11-25~2027-11-25", "2024-11-25"],
      ["3", "USB 데스크스위치", "2023-02-03~2025-02-03", "2023-02-03"],
      ["2", "ITS 채용안내", "2021-04-28~2023-01-01", "2021-04-28"],
      ["1", "팝업 일반", "2016-04-14~2020-04-30", "2016-04-14"]
    ],
    actionLabel: "팝업등록"
  },
  promo: {
    headers: ["번호", "제목", "첨부파일", "등록일", "기능"],
    rows: [
      ["1", "ITS컨버젼스 스마트 통합관제 플랫폼 - 소방청119종합상황실", "홍보자료_01.pdf", "2026-02-01", ""],
      ["2", "2022 한글자막 WEY CCTV통합관제센터", "홍보자료_02.pdf", "2026-02-02", ""],
      ["3", "WEYTEC Workplace 01", "홍보자료_03.pdf", "2026-02-03", ""],
      ["4", "Avaya Oceana", "홍보자료_04.pdf", "2026-02-04", ""],
      ["5", "Avaya Equinox", "홍보자료_05.pdf", "2026-02-05", ""],
      ["6", "[구축사례_공공] WEY TEC_인천항만공사 - 여객터미널", "홍보자료_06.pdf", "2026-02-06", ""],
      ["7", "[구축사례_공공] WEY TEC_ELES - 전력관리 서비스", "홍보자료_07.pdf", "2026-02-07", ""],
      ["8", "[파이낸셜뉴스]GS ITM-아이티에스컨버젼스-위엠", "홍보자료_08.pdf", "2026-02-08", ""],
      ["9", "[아시아경제]1초 승부의 세계…‘절대 멈춰선 안되는 딜링룸’의 비결", "홍보자료_09.pdf", "2026-02-09", ""],
      ["10", "WEYTEC 스위스 방한 : KB 국민은행 동행 방문", "홍보자료_10.pdf", "2026-02-10", ""],
      ["11", "스마트사이버보안센터(러시아은행)", "홍보자료_11.pdf", "2026-02-11", ""],
      ["12", "WEY Smart Visual 소개자료", "홍보자료_12.pdf", "2026-02-12", ""],
      ["13", "통합관제(IT센터)", "홍보자료_13.pdf", "2026-02-13", ""],
      ["14", "통합관제(경찰청)", "홍보자료_14.pdf", "2026-02-14", ""],
      ["15", "ITS솔루션 : KB국민은행 스마트딜링룸", "홍보자료_15.pdf", "2026-02-15", ""],
      ["16", "Smart Dealing Transformation 소개자료", "홍보자료_16.pdf", "2026-02-16", ""]
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
  isEnabled: () =>
    state.activeTab === "basic" && state.activeSubMenu === "board-settings",
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
  return dashboardTableMap.admin;
}

function renderDashboardTable() {
  const config = getDashboardTableConfig();
  const totalPages = paginationController.resolveTotalPages();
  state.tablePage = Math.min(Math.max(state.tablePage || 1, 1), totalPages);
  paginationController.update();
  const start = (state.tablePage - 1) * tableRowsPerPage;
  const end = start + tableRowsPerPage;
  const rows = (config.rows || []).slice(start, end);

  if (paginationControls) {
    paginationControls.style.display = "flex";
  }

  const headerHTML = `<tr>${config.headers
    .map((header, index) => {
      if (index === config.headers.length - 1) {
        return `<th class=\"center\">${header}</th>`;
      }
      if (index === 1 && config.headers[index] === "제목") {
        return `<th class=\"table-title-col\">${header}</th>`;
      }
      if (index === 2 && config.headers[index] === "공지기간") {
        return `<th class=\"table-period-col\">${header}</th>`;
      }
      if (index === 3 && config.headers[index] === "등록일") {
        return `<th class=\"table-date-col\">${header}</th>`;
      }
      return `<th>${header}</th>`;
    })
    .join("")}</tr>`;
  tableHead.innerHTML = headerHTML;

  tableBody.innerHTML = rows
    .map((row) => {
      const cols = config.headers.map((_, idx) => {
        if (idx === config.headers.length - 1) {
          return `<td class="center"><div class="row-actions"><button type="button" class="edit-btn">수정</button><button type="button" class="delete-btn">삭제</button></div></td>`;
        }
        if (idx === 1 && config.headers[idx] === "제목") {
          return `<td class="table-title-col">${row[idx] || ""}</td>`;
        }
        if (idx === 2 && config.headers[idx] === "공지기간") {
          return `<td class="table-period-col">${row[idx] || ""}</td>`;
        }
        if (idx === 3 && config.headers[idx] === "등록일") {
          return `<td class="table-date-col">${row[idx] || ""}</td>`;
        }
        return `<td>${row[idx] || ""}</td>`;
      });
      return `<tr>${cols.join("")}</tr>`;
    })
    .join("");
  tableActionBtn.innerHTML = `<span class="icon"><img src="/asset/img/plus.gif" alt="" /></span>${config.actionLabel}`;
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
