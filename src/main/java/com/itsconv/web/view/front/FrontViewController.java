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
}
