package org.geekhub.reddit.subreddit;

import org.geekhub.reddit.exception.DataBaseRowException;
import org.geekhub.reddit.user.RedditUser;
import org.geekhub.reddit.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubredditService {

    private final UserRepository userRepository;
    private final SubredditRepository subredditRepository;

    public SubredditService(UserRepository userRepository, SubredditRepository subredditRepository) {
        this.userRepository = userRepository;
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
        Subreddit subreddit = new Subreddit(subredditName, userRepository.getUserByLogin(creatorName).getId());
        if (subredditRepository.createSubreddit(subreddit) == 1) {
            return subreddit;
        }
        throw new DataBaseRowException("Failed to create subreddit");
    }

    public void subscribeUser(int subredditId, String userLogin) {
        int userId = userRepository.getUserByLogin(userLogin).getId();
        if (subredditRepository.subscribeUser(subredditId, userId) != 1) {
            throw new DataBaseRowException("Failed to add comment");
        }
    }
}
