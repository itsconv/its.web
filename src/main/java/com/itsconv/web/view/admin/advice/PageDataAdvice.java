package com.itsconv.web.view.admin.advice;

import com.itsconv.web.menu.domain.MenuDepth;
import com.itsconv.web.menu.repository.MenuRepository;
import com.itsconv.web.view.admin.PopupViewController;
import com.itsconv.web.view.admin.ImageViewController;
import com.itsconv.web.view.admin.HistoryViewController;
import com.itsconv.web.view.admin.BoardViewController;
import com.itsconv.web.view.admin.UserViewController;
import com.itsconv.web.view.admin.ViewController;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(assignableTypes = {
        ViewController.class,
        UserViewController.class,
        PopupViewController.class,
        BoardViewController.class,
        HistoryViewController.class,
        ImageViewController.class
})
@RequiredArgsConstructor
public class PageDataAdvice {

    private final MenuRepository menuRepository;

    @ModelAttribute
    public void addPageData(HttpServletRequest request, Model model) {
        // 현재 URI에 해당하는 메뉴 Enum 추출
        String uri = request.getRequestURI();
        Menu currentMenu = Menu.fromUri(uri);

        // Model에 매핑
        model.addAttribute("pageData", currentMenu.getPageData());
        model.addAttribute("activeTab", currentMenu.getActiveTab());
        model.addAttribute("breadcrumbTitle", currentMenu.getBreadcrumbTitle());
        model.addAttribute("pageTitle", currentMenu.getPageTitle());
        model.addAttribute("pageDescription", currentMenu.getPageDescription());

        if ("media".equals(currentMenu.getActiveTab())) {
            model.addAttribute(
                    "mainMenus",
                    menuRepository.findByDepthAndUseYnOrderBySortOrderAsc(MenuDepth.MAIN, "Y")
            );
        }
    }
}
