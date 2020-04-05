package org.geekhub.reddit.subreddit;

import org.geekhub.reddit.user.RedditUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/r")
public class SubredditController {

    private SubredditService subredditService;

    public SubredditController(SubredditService subredditService) {
        this.subredditService = subredditService;
    }

    @GetMapping
    @ResponseBody
    public List<Subreddit> getAllSubreddits() {
        return subredditService.getAllSubreddits();
    }

    @GetMapping("{id}")
    @ResponseBody
    public Subreddit getSubredditById(@PathVariable int id) {
        return subredditService.getSubredditById(id);
    }

    @GetMapping("{id}/subscribers")
    @ResponseBody
    public List<RedditUser> getSubscribers(@PathVariable int id) {
        return subredditService.getSubscribers(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Subreddit createSubreddits(@RequestBody String name, @AuthenticationPrincipal Principal principal) {
        return subredditService.addSubreddit(name, principal.getName());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{id}/subscribe")
    public void subscribeUser(@PathVariable int id, @AuthenticationPrincipal Principal principal) {
        subredditService.subscribeUser(id, principal.getName());
    }
}
