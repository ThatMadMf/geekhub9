package org.geekhub.reddit.web.rest;

import org.geekhub.reddit.db.dtos.SubredditDto;
import org.geekhub.reddit.db.models.RedditUser;
import org.geekhub.reddit.db.models.Subreddit;
import org.geekhub.reddit.services.SubredditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/subreddits", produces = MediaType.APPLICATION_JSON_VALUE)
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
    public Subreddit createSubreddits(@RequestBody SubredditDto subredditDto) {
        return subredditService.addSubreddit(new Subreddit(subredditDto));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{id}/subscribe")
    public Subreddit subscribeUser(@PathVariable int id, String login) {
        return subredditService.subscribeUser(id, login);
    }
}
