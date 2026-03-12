package com.itsconv.web.view.front;

import com.itsconv.web.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/front")
public class FrontViewController {

    private final ImageService imageService;

    @GetMapping("/trading-center")
    public String getTradingCenter(Model model) {
        model.addAttribute("pageImageUrls", imageService.findImageUrlsByMainMenuCode("TRADING_ROOM"));
        return "front/trading_center";
    }

    @GetMapping("/operation-center")
    public String getOperationCenter(Model model) {
        model.addAttribute("pageImageUrls", imageService.findImageUrlsByMainMenuCode("AI_MONITORING_CENTER"));
        return "front/operation_center";
    }

    @GetMapping("/contact-center")
    public String getContactCenter(Model model) {
        model.addAttribute("pageImageUrls", imageService.findImageUrlsByMainMenuCode("AI_CONTACT_CENTER"));
        return "front/contact_center";
    }

    @GetMapping("/uc-solution")
    public String getUcSolution(Model model) {
        model.addAttribute("pageImageUrls", imageService.findImageUrlsByMainMenuCode("UC_SOLUTION"));
        return "front/uc_solution";
    }
}
