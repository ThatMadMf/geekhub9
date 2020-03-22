package org.geekhub.reddit.web.controller;

import org.geekhub.reddit.db.dtos.RegistrationDto;
import org.geekhub.reddit.db.dtos.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private UserDao userDao;

    public RegistrationController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("registration")
    public String showRegistrationPage(Model model) {
        RegistrationDto registrationDto = new RegistrationDto();
        model.addAttribute("login", registrationDto);
        return "thymeleaf/registration";
    }

    @PostMapping("registration")
    @ResponseBody
    public void registerUser(@Valid RegistrationDto registrationDto) {
        if (registrationDto.getPassword().equals(registrationDto.getMatchingPassword())) {
            userDao.register(registrationDto);
        } else {
        }
    }
}
