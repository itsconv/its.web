package com.itsconv.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonViewController {

    @GetMapping({"", "/"})
    public String root() {
        return "redirect:/front/trading-center";
    }
}
