package com.itsconv.web.view.admin.advice;

import com.itsconv.web.view.admin.PopupViewController;
import com.itsconv.web.view.admin.UserViewController;
import com.itsconv.web.view.admin.ViewController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(assignableTypes = {
        ViewController.class,
        UserViewController.class,
        PopupViewController.class
})
public class PageDataAdvice {

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
    }
}
