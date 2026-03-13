package com.itsconv.web.view.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class ViewController {

    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/admin/user/list";
    }

    @GetMapping("/bbs/bbs_list")
    public String bbsList() {
        return "admin/bbs/bbs_list";
    }
}
