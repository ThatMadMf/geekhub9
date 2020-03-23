package org.geekhub.reddit.web.controller;

import org.geekhub.reddit.db.dtos.RegistrationDto;
import org.geekhub.reddit.db.dtos.UserDao;
import org.geekhub.reddit.exception.RegistrationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

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
    public void registerUser(@Valid RegistrationDto registrationDto, HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        if (registrationDto.getPassword().equals(registrationDto.getMatchingPassword())) {
            userDao.register(request, registrationDto);
            response.sendRedirect("/swagger-ui.html");
        } else {
            throw new RegistrationException("Passwords not matching");
        }
    }
}
