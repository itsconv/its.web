package com.itsconv.web.view;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

@Configuration
public class ErrorRedirectConfig {

    @Bean
    public ErrorViewResolver frontRedirectErrorViewResolver() {
        return (request, status, model) -> {
            if (status != HttpStatus.NOT_FOUND || !shouldRedirectToTradingCenter(request)) {
                return null;
            }
            return new ModelAndView("redirect:/front/trading-center");
        };
    }

    private boolean shouldRedirectToTradingCenter(HttpServletRequest request) {
        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            return false;
        }

        String requestUri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        if (requestUri == null) {
            return false;
        }

        return !requestUri.startsWith("/api/")
                && !requestUri.startsWith("/asset/")
                && !requestUri.startsWith("/admin/css/")
                && !requestUri.startsWith("/admin/js/")
                && !requestUri.startsWith("/favicon.ico");
    }
}
