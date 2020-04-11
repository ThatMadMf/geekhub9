package org.geekhub.reddit.user;

import org.geekhub.reddit.post.Post;
import org.geekhub.reddit.user.dto.PrivateRedditUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("feed")
    public List<Post> getUserFeed(@AuthenticationPrincipal Principal principal) {
        int userId = userService.getUser(principal.getName()).getId();
        return userService.getUserFeed(userId);
    }

    @GetMapping("posts")
    public List<Post> getUserPosts(@AuthenticationPrincipal Principal principal) {
        int userId = userService.getUser(principal.getName()).getId();
        return userService.getUserPosts(userId);
    }

    @GetMapping
    public PrivateRedditUser getPersonalData(@AuthenticationPrincipal Principal principal) {
        int userId = userService.getUser(principal.getName()).getId();
        return userService.getUserPrivateData(userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteUser(@AuthenticationPrincipal Principal principal) {
        int userId = userService.getUser(principal.getName()).getId();
        userService.deleteUser(userId);
    }
}
