package org.geekhub.reddit.web.rest;

import org.geekhub.reddit.db.models.Post;
import org.geekhub.reddit.db.models.RedditUser;
import org.geekhub.reddit.db.models.Subreddit;
import org.geekhub.reddit.services.SubredditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "api/r", produces = MediaType.APPLICATION_JSON_VALUE)
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
    public Subreddit subscribeUser(@PathVariable int id, @AuthenticationPrincipal Principal principal) {
        return subredditService.subscribeUser(id, principal.getName());
    }
}
