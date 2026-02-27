package com.itsconv.web.admin.controller;

import com.itsconv.web.admin.controller.dto.AdminUserListResponse;
import com.itsconv.web.admin.controller.dto.AdminUserUpdateRequest;
import com.itsconv.web.user.domain.User;
import com.itsconv.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminViewController {

    private final UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/basic/admin_list";
    }

    @GetMapping("/basic/admin_list")
    public String getAdminList(Model model) {
        List<AdminUserListResponse> adminUserList = userService.findUserList().users().stream()
                .map(AdminUserListResponse::from)
                .toList();
        model.addAttribute("adminUserList", adminUserList);
        return "basic/admin_list";
    }

    @GetMapping("/basic/admin_edit/{userId}")
    public String editAdminForm(@PathVariable("userId") String userId, Model model) {
        User user = userService.findUser(userId).user();
        model.addAttribute("userId", user.getUserId());
        model.addAttribute("adminUserForm", new AdminUserUpdateRequest(null, user.getName(), user.getMemo()));
        return "basic/admin_edit";
    }

    @GetMapping("/basic/admin_edit")
    public String editAdminFormRedirect() {
        return "redirect:/basic/admin_list";
    }

    @GetMapping("/basic/popup_list")
    public String popupList() {
        return "basic/popup_list";
    }

    @GetMapping("/bbs/data")
    public String bbsData() {
        return "bbs/data";
    }

    @GetMapping("/bbs/its_notice")
    public String itsNotice() {
        return "bbs/its_notice";
    }

    @GetMapping("/bbs/download")
    public String download() {
        return "bbs/download";
    }

    @GetMapping("/bbs/story")
    public String story() {
        return "bbs/story";
    }

    @GetMapping("/bbs/bbs_list")
    public String bbsList() {
        return "bbs/bbs_list";
    }

    @GetMapping("/history/history")
    public String historyList() {
        return "history/history";
    }
}
