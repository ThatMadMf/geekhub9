package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.Post;
import org.geekhub.reddit.db.models.RedditUser;
import org.geekhub.reddit.db.models.Subreddit;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserService {

    private final JdbcTemplate jdbcTemplate;
    private final PostService postService;

    public UserService(JdbcTemplate jdbcTemplate, PostService postService) {
        this.jdbcTemplate = jdbcTemplate;
        this.postService = postService;
    }

    public List<RedditUser> findUsersBySubredditId(int subredditId) {
        String sql = "select u.*" +
                "from reddit.users AS u INNER JOIN reddit.subreddit_user AS su ON u.login = su.user_login " +
                "where su.subreddit_id = ?";
        return jdbcTemplate.query(sql, new Object[]{subredditId}, new BeanPropertyRowMapper<>(RedditUser.class));
    }

    public List<Post> getUserFeed(String userLogin) {
        String sql = "select s.* " +
                "from reddit.subreddits as s inner join reddit.subreddit_user as su on s.id = su.subreddit_id " +
                "where su.user_login = ?";
        List<Subreddit> subscribedSubreddits = jdbcTemplate.query(sql, new Object[]{userLogin},
                new BeanPropertyRowMapper<>(Subreddit.class));
        return subscribedSubreddits.stream()
                .flatMap(subreddit -> postService.getAllPostBySubredditId(subreddit.getId()).stream())
                .collect(Collectors.toList());
    }
}
