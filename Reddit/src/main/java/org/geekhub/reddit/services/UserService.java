package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.Post;
import org.geekhub.reddit.db.models.RedditUser;
import org.geekhub.reddit.db.repositories.UserRepository;
import org.geekhub.reddit.dtos.RegistrationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final PostService postService;
    private final UserRepository userRepository;

    public UserService(PostService postService, UserRepository userRepository) {
        this.postService = postService;
        this.userRepository = userRepository;
    }

    public RedditUser getUser(String login) {
        return userRepository.getUserByLogin(login);
    }

    public List<Post> getUserFeed(int id) {
        return userRepository.getSubscriptions(id).stream()
                .flatMap(subreddit -> postService.getAllPostBySubredditId(subreddit.getId()).stream())
                .collect(Collectors.toList());
    }

    public List<Post> getUserPosts(String login) {
        return userRepository.getPosts(getUser(login).getId());
    }

    public void deleteUser(int id) {
        userRepository.deleteUser(id);
    }

    public RegistrationDto getUserPrivateData(int id) {
        return userRepository.getUserInfo(id);
    }
}
