package com.itsconv.web.view.admin;

import com.itsconv.web.user.service.UserService;
import com.itsconv.web.user.service.dto.result.UserDetailView;
import com.itsconv.web.view.admin.dto.UserFormView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserViewController {

    private final UserService userService;

    @GetMapping("/list")
    public String getAdminList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        Page<UserDetailView> adminUserPage = userService.findUserList(page, size);
        model.addAttribute("adminUserPage", adminUserPage);
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
