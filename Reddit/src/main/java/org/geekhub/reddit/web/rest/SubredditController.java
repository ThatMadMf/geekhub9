package org.geekhub.reddit.web.rest;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.geekhub.reddit.db.models.Subreddit;
import org.geekhub.reddit.services.SubredditService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class SubredditController {

    private SubredditService subredditService;

    public SubredditController(SubredditService subredditService) {
        this.subredditService = subredditService;
    }

    @GetMapping("subreddits")
    @ResponseBody
    public List<Subreddit> getAllSubreddits() {
        return subredditService.getAllTags();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("subreddits")
    public Subreddit createSubreddits(@RequestBody String name, Authentication authentication, String userName) {
        return subredditService.addSubreddit(new Subreddit(name,
                authentication == null ? userName : authentication.name(), LocalDate.now()));
    }
}
