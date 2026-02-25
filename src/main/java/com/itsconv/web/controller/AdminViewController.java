package com.itsconv.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminViewController {

    @GetMapping("/")
    public String index() {
        return "redirect:/basic/admin_list";
    }

    @GetMapping("/basic/admin_list")
    public String adminList() {
        return "basic/admin_list";
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
    public String yearList() {
        return "history/history";
    }
}
