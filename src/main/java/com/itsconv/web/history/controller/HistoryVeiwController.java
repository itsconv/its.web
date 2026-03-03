package com.itsconv.web.history.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.itsconv.web.history.controller.dto.response.HistoryPeriodResponse;
import com.itsconv.web.history.service.HistoryService;

import lombok.RequiredArgsConstructor;

/**
 * 충돌방지 임시 view 테이블
 */
@Controller
@RequiredArgsConstructor
public class HistoryVeiwController {
    private final HistoryService historyService;

    @GetMapping("/history/history")
    public String historyView(Model model) {
        List<HistoryPeriodResponse> period = historyService.findPeriod();

        model.addAttribute("period", period);

        return "history/history";
    }
}
