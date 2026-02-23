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
  ...sharedDashboardTableMap
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
  const headerHTML = `<tr>${config.headers
    .map((header, index) =>
      index === config.headers.length - 1
        ? `<th class="center">${header}</th>`
        : `<th>${header}</th>`
    )
    .join("")}</tr>`;
  tableHead.innerHTML = headerHTML;

  tableBody.innerHTML = config.rows
    .map((row) => {
      const cols = config.headers.map((_, idx) =>
        idx === config.headers.length - 1
          ? `<td class="center"><div class="row-actions"><button type="button" class="edit-btn">수정</button><button type="button" class="delete-btn">삭제</button></div></td>`
          : `<td>${row[idx] || ""}</td>`
      );
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
