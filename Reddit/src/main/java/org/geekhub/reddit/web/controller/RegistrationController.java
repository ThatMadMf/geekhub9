package org.geekhub.reddit.web.controller;

import org.geekhub.reddit.db.dtos.Login;
import org.geekhub.reddit.db.dtos.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private UserDao userDao;

    public RegistrationController(UserDao userDao) {
        this.userDao = userDao;
    }


    @GetMapping("registration")
    public String showRegistrationPage(Model model) {
        Login login = new Login();
        model.addAttribute("login", login);
        return "thymeleaf/registration";
    }

    @PostMapping("registration")
    public void registerUser(@Valid Login login) {
        if (login.getPassword().equals(login.getMatchingPassword())) {
            userDao.register(login);
        } else {
        }
    }
}
