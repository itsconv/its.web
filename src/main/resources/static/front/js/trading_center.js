document.addEventListener("DOMContentLoaded", function () {
  const tabLinks = document.querySelectorAll(".sub-tab-list li a");
  const solutionGroups = document.querySelectorAll(".solution-group");
  const imageGalleries = Array.from(document.querySelectorAll(".tab-contents .con1 .float"))
    .map(function (wrapper) {
      return wrapper.querySelector(".f-left");
    })
    .filter(function (gallery) {
      return gallery && gallery.querySelector("img");
    });

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

  const activateGroupTab = function (group, activeIndex) {
    const tabButtons = group.querySelectorAll(".tab-menu button");
    const tabPanels = group.querySelectorAll(".tab-contents .clearfix > li");

    tabButtons.forEach(function (button, index) {
      button.setAttribute("aria-pressed", String(index === activeIndex));
    });

    tabPanels.forEach(function (panel, index) {
      panel.classList.toggle("active", index === activeIndex);
    });
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

  solutionGroups.forEach(function (group) {
    const tabButtons = group.querySelectorAll(".tab-menu button");

    tabButtons.forEach(function (button, index) {
      button.addEventListener("click", function () {
        activateGroupTab(group, index);
      });
    });

    if (tabButtons.length > 0) {
      const initialIndex = Array.from(tabButtons).findIndex(function (button) {
        return button.getAttribute("aria-pressed") === "true";
      });

      activateGroupTab(group, initialIndex >= 0 ? initialIndex : 0);
    }
  });

  const activateGalleryImage = function (gallery, activeIndex) {
    const images = gallery.querySelectorAll(".gallery-image");
    const dots = gallery.querySelectorAll(".gallery-dot");

    images.forEach(function (image, index) {
      image.classList.toggle("active", index === activeIndex);
    });

    dots.forEach(function (dot, index) {
      dot.classList.toggle("is-active", index === activeIndex);
      dot.setAttribute("aria-pressed", String(index === activeIndex));
    });
  };

  imageGalleries.forEach(function (gallery) {
    gallery.classList.add("image-gallery-enabled");

    const rawImages = Array.from(gallery.querySelectorAll("img"));
    const images = rawImages.map(function (image, index) {
      image.classList.add("gallery-image");
      if (!image.classList.contains("active") && index === 0) {
        image.classList.add("active");
      }
      return image;
    });

    let dotsContainer = gallery.querySelector(".gallery-dots");
    if (!dotsContainer) {
      dotsContainer = document.createElement("div");
      dotsContainer.className = "gallery-dots";
      dotsContainer.setAttribute("aria-label", "image selector");
      gallery.appendChild(dotsContainer);
    }

    if (!dotsContainer || images.length === 0) {
      return;
    }

    dotsContainer.innerHTML = "";

    images.forEach(function (_, index) {
      const dot = document.createElement("button");
      dot.type = "button";
      dot.className = "gallery-dot";
      dot.setAttribute("aria-label", "이미지 " + (index + 1));
      dot.setAttribute("aria-pressed", "false");

      dot.addEventListener("click", function () {
        activateGalleryImage(gallery, index);
      });

      dotsContainer.appendChild(dot);
    });

    const initialIndex = Array.from(images).findIndex(function (image) {
      return image.classList.contains("active");
    });

    activateGalleryImage(gallery, initialIndex >= 0 ? initialIndex : 0);
  });

  const activateTabByHash = function () {
    const hash = window.location.hash;
    if (!hash) {
      return;
    }

    const targetId = hash.replace("#", "");
    const target = document.getElementById(targetId);
    if (!target) {
      return;
    }

    solutionGroups.forEach(function (group) {
      const tabPanels = Array.from(group.querySelectorAll(".tab-contents .clearfix > li"));
      const panelIndex = tabPanels.findIndex(function (panel) {
        return panel.contains(target);
      });

      if (panelIndex >= 0) {
        activateGroupTab(group, panelIndex);
      }
    });

    window.setTimeout(function () {
      const top = target.getBoundingClientRect().top + window.scrollY;
      animateScrollTo(top, 480);
    }, 20);
  };

  activateTabByHash();

  window.addEventListener("hashchange", function () {
    activateTabByHash();
  });
});
