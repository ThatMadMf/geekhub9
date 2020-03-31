package org.geekhub.reddit.web.rest;

import org.geekhub.reddit.dtos.RegistrationDto;
import org.geekhub.reddit.db.models.Post;
import org.geekhub.reddit.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping("user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("feed")
    public List<Post> getUserFeed(@AuthenticationPrincipal Principal principal) {
        return userService.getUserFeed(principal.getName());
    }

    @GetMapping("posts")
    public List<Post> getUserPosts(@AuthenticationPrincipal Principal principal) {
        return userService.getUserPosts(principal.getName());
    }

    @GetMapping
    public RegistrationDto getPersonalData(@AuthenticationPrincipal Principal principal) {
        return userService.getUserData(principal.getName());
    }

    @DeleteMapping
    public void deleteUser(@AuthenticationPrincipal Principal principal) {
        userService.deleteUser(principal.getName());
    }
}
