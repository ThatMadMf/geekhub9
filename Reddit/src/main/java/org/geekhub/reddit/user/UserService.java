package org.geekhub.reddit.user;

import org.geekhub.reddit.post.Post;
import org.geekhub.reddit.post.PostService;
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

    public PrivateRedditUser getUserPrivateData(int id) {
        PrivateRedditUser userInfo = userRepository.getUserInfo(id);
        userInfo.setPassword(null);
        return userInfo;
    }
}
