package org.geekhub.reddit.web.rest;

import org.geekhub.reddit.db.models.Post;
import org.geekhub.reddit.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseBody
    public List<Post> getUserFeed(@AuthenticationPrincipal Principal principal) {
        return userService.getUserFeed(principal.getName());
    }
}
