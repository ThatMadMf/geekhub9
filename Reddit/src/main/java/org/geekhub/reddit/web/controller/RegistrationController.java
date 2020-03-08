package org.geekhub.reddit.web.controller;

import org.geekhub.reddit.db.models.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @GetMapping("registration")
    public String showRegistrationPage(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "registration";
    }

    @PostMapping("registration")
    public void registerUser(@Valid UserDto userDto) {
        if (userDto.getPassword() == userDto.getMatchingPassword()) {
            System.out.println("Ok");
        }
    }
}
