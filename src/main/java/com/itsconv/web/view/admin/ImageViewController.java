package com.itsconv.web.view.admin;

import com.itsconv.web.image.service.ImageService;
import com.itsconv.web.image.service.dto.view.ImageManagementView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/image")
public class ImageViewController {

    private final ImageService imageService;

    @GetMapping
    public String getImageManagement(
            @RequestParam(required = false) String mainMenuCode,
            @RequestParam(required = false) String subMenuCode,
            @RequestParam(required = false) String tabMenuCode,
            Model model
    ) {
        ImageManagementView imageManagementView = imageService.findImageManagementView(mainMenuCode, subMenuCode, tabMenuCode);
        model.addAttribute("imageManagementView", imageManagementView);
        return "admin/image/list";
    }
}
