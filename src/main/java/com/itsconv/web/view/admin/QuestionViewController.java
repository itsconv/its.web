package com.itsconv.web.view.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itsconv.web.question.service.QuestionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/question")
public class QuestionViewController {
    private final QuestionService questionService;

    @GetMapping("/list")
    public String questionList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        model.addAttribute("questionPage", questionService.findQuestionList(page, size));
        return "admin/question/list";
    }

    @GetMapping("/detail")
    public String questionDetail(
        @RequestParam Long id, Model model
    ) {
        model.addAttribute("detailPage", questionService.findQuestionOneById(id));
        return "admin/question/detail";
    }
}
