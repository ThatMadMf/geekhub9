package org.geekhub.reddit.subreddit;

import org.geekhub.reddit.user.RedditUser;
import org.geekhub.reddit.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubredditService {

    private final UserService userService;
    private final SubredditRepository subredditRepository;

    public SubredditService(UserService userService, SubredditRepository subredditRepository) {
        this.userService = userService;
        this.subredditRepository = subredditRepository;
    }

    public List<Subreddit> getAllSubreddits() {
        return subredditRepository.getAllSubreddits();
    }

    public List<RedditUser> getSubscribers(int subredditId) {
        return subredditRepository.getSubscribers(subredditId);
    }

    public Subreddit getSubredditById(int id) {
        return subredditRepository.getSubredditById(id);
    }

    public Subreddit addSubreddit(String subredditName, String creatorName) {
        Subreddit subreddit = new Subreddit(subredditName, userService.getUser(creatorName).getId());
        return subredditRepository.createSubreddit(subreddit);
    }

    public Subreddit subscribeUser(int subredditId, String userLogin) {
        return subredditRepository.subscribeUser(subredditId, userService.getUser(userLogin).getId());
    }
}
