package com.itsconv.web.view.admin;

import com.itsconv.web.view.admin.dto.PopupFormView;
import com.itsconv.web.popup.service.PopupService;
import com.itsconv.web.popup.service.dto.result.PopupDetailView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/popup")
public class PopupViewController {

    private final PopupService popupService;

    @GetMapping("/list")
    public String popupList(Model model) {
        model.addAttribute("popupList", popupService.findPopupList().popups());
        return "admin/basic/popup/list";
    }

    @GetMapping("/edit")
    public String createPopupForm(Model model) {
        model.addAttribute("popupSeq", "");
        model.addAttribute("isCreate", true);
        model.addAttribute("popupForm", PopupFormView.createDefault());
        return "admin/basic/popup/edit";
    }

    @GetMapping("/edit/{seq}")
    public String editPopupForm(@PathVariable("seq") Long seq, Model model) {
        PopupDetailView popup = popupService.findPopup(seq);

        model.addAttribute("popupSeq", seq);
        model.addAttribute("isCreate", false);
        model.addAttribute("popupForm", PopupFormView.from(popup));
        return "admin/basic/popup/edit";
    }
}
