package org.geekhub.reddit.web.controller;

import org.geekhub.reddit.db.models.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class RegistrationController {

    @GetMapping("/registration")
    public String showRegistrationPage(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }
}
