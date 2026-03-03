package com.itsconv.web.view.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/image")
public class ImageViewController {

    @GetMapping
    public String imageManagement() {
        return "admin/image/list";
    }

    @GetMapping("/trading-room")
    public String imageTradingRoom() {
        return "admin/image/list";
    }

    @GetMapping("/ai-monitoring-center")
    public String imageAiMonitoringCenter() {
        return "admin/image/list";
    }

    @GetMapping("/ai-contact-center")
    public String imageAiContactCenter() {
        return "admin/image/list";
    }

    @GetMapping("/uc-solution")
    public String imageUcSolution() {
        return "admin/image/list";
    }
}
