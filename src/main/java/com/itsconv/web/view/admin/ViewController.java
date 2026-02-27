package com.itsconv.web.view.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "redirect:/admin/user/list";
    }

    @GetMapping("/bbs/data")
    public String bbsData() {
        return "admin/bbs/data";
    }

    @GetMapping("/bbs/its_notice")
    public String itsNotice() {
        return "admin/bbs/its_notice";
    }

    @GetMapping("/bbs/download")
    public String download() {
        return "admin/bbs/download";
    }

    @GetMapping("/bbs/story")
    public String story() {
        return "admin/bbs/story";
    }

    @GetMapping("/bbs/bbs_list")
    public String bbsList() {
        return "admin/bbs/bbs_list";
    }

    @GetMapping("/history/history")
    public String historyList() {
        return "admin/history/history";
    }
}
