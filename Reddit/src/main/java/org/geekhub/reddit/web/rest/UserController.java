package org.geekhub.reddit.web.rest;

import org.geekhub.reddit.db.models.Post;
import org.geekhub.reddit.db.models.RedditUser;
import org.geekhub.reddit.dtos.RegistrationDto;
import org.geekhub.reddit.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("feed")
    public List<Post> getUserFeed(@AuthenticationPrincipal Principal principal) {
        RedditUser redditUser = userService.getUser(principal.getName());
        return userService.getUserFeed(redditUser.getId());
    }

    @GetMapping("posts")
    public List<Post> getUserPosts(@AuthenticationPrincipal Principal principal) {
        return userService.getUserPosts(principal.getName());
    }

    @GetMapping
    public RegistrationDto getPersonalData(@AuthenticationPrincipal Principal principal) {
        RedditUser redditUser = userService.getUser(principal.getName());
        return userService.getUserPrivateData(redditUser.getId());
    }

    @DeleteMapping
    public void deleteUser(@AuthenticationPrincipal Principal principal) {
        RedditUser redditUser = userService.getUser(principal.getName());
        userService.deleteUser(redditUser.getId());
    }
}
