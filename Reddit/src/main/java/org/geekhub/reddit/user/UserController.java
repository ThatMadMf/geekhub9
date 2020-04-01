package org.geekhub.reddit.user;

import org.geekhub.reddit.post.Post;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
