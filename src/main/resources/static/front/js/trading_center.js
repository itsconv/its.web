document.addEventListener("DOMContentLoaded", function () {
  const tabLinks = document.querySelectorAll(".sub-tab-list li a");
  const tabButtons = document.querySelectorAll(".tab-menu button");
  const tabPanels = document.querySelectorAll(".tab-contents .clearfix > li");

  const animateScrollTo = function (targetY, duration) {
    const startY = window.scrollY;
    const distance = targetY - startY;
    const startTime = performance.now();
    const totalDuration = Math.max(300, duration || 420);

    const easeInOutQuad = function (t) {
      return t < 0.5 ? 2 * t * t : 1 - Math.pow(-2 * t + 2, 2) / 2;
    };

    const step = function (timestamp) {
      const elapsed = timestamp - startTime;
      const progress = Math.min(elapsed / totalDuration, 1);
      const nextY = startY + distance * easeInOutQuad(progress);
      window.scrollTo(0, nextY);

      if (progress < 1) {
        requestAnimationFrame(step);
      }
    };

    requestAnimationFrame(step);
  };

  tabLinks.forEach(function (anchor) {
    anchor.addEventListener("click", function (event) {
      event.preventDefault();

      const href = this.getAttribute("href");
      const targetId = href ? href.replace("#", "") : "";
      const target = targetId ? document.getElementById(targetId) : null;

      if (target) {
        const top = target.getBoundingClientRect().top + window.scrollY;
        animateScrollTo(top, 480);
      }
    });
  });

  const updateTabState = function (activeIndex) {
    tabButtons.forEach(function (button, index) {
      const pressed = index === activeIndex;
      button.setAttribute("aria-pressed", String(pressed));
    });

    tabPanels.forEach(function (panel, index) {
      panel.classList.toggle("active", index === activeIndex);
    });
  };

  tabButtons.forEach(function (button, index) {
    button.addEventListener("click", function () {
      updateTabState(index);
    });
  });

  if (tabButtons.length > 0) {
    const initialIndex = Array.from(tabButtons).findIndex(function (button) {
      return button.getAttribute("aria-pressed") === "true";
    });
    updateTabState(initialIndex >= 0 ? initialIndex : 0);
  }
});
