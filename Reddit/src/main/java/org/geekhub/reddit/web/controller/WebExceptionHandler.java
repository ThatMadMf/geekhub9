package org.geekhub.reddit.web.controller;

import org.geekhub.reddit.exception.RegistrationException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class WebExceptionHandler implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @GetMapping("/error")
    @ExceptionHandler
    public String exceptionHandler(HttpServletRequest request, Model model) {
        int status = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String message;
        if (status == 500) {
            message = "Internal Server Error. We'll fix that";
        } else if (status == 403) {
            message = "Forbidden.";
        } else if (status == 404) {
            message = "Not Found";
        } else {
            message = "Unknown Error. Its not our fault.";
        }

        model.addAttribute("status", status);
        model.addAttribute("message", message);

        return "thymeleaf/ErrorPage";
    }
}

