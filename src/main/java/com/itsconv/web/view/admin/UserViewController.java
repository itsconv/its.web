package com.itsconv.web.view.admin;

import com.itsconv.web.view.admin.dto.UserFormView;
import com.itsconv.web.user.service.UserService;
import com.itsconv.web.user.service.dto.result.UserListView;
import com.itsconv.web.user.service.dto.result.UserDetailView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserViewController {

    private final UserService userService;

    @GetMapping("/list")
    public String getAdminList(Model model) {
        UserListView userListView = userService.findUserList();
        List<UserDetailView> adminUserList = userListView.users();
        model.addAttribute("adminUserList", adminUserList);
        return "admin/basic/user/list";
    }

    @GetMapping("/edit/{userId}")
    public String editAdminForm(@PathVariable("userId") String userId, Model model) {
        UserDetailView user = userService.findUser(userId);
        model.addAttribute("userId", user.userId());
        model.addAttribute("isCreate", false);
        model.addAttribute("adminUserForm", UserFormView.from(user));
        return "admin/basic/user/edit";
    }

    @GetMapping("/edit")
    public String createAdminForm(Model model) {
        model.addAttribute("userId", "");
        model.addAttribute("isCreate", true);
        model.addAttribute("adminUserForm", UserFormView.empty());
        return "admin/basic/user/edit";
    }
}
