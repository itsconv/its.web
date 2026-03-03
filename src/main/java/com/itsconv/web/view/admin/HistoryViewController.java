package com.itsconv.web.view.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itsconv.web.history.controller.dto.response.HistoryPeriodResponse;
import com.itsconv.web.history.service.HistoryService;

import lombok.RequiredArgsConstructor;

/**
 * 충돌 방지를 위한 임시 뷰 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/history")
public class HistoryViewController {
    private final HistoryService historyService;

    @GetMapping("/list")
    public String historyView(Model model) {
        List<HistoryPeriodResponse> period = historyService.findPeriod();

        model.addAttribute("period", period);

        return "admin/history/list";
    }
}
