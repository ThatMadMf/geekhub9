package org.geekhub.reddit.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestUserRole {

    @GetMapping("/role")
    public String testSecurity() {
        return "Current user has at least USER role.";
    }

    @GetMapping("/admin")
    public String testIfAdmin() {
        return "Current user has at ADMIN role.";
    }

}
