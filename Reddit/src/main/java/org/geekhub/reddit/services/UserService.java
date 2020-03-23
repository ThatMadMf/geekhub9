package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.Post;
import org.geekhub.reddit.db.models.RedditUser;
import org.geekhub.reddit.db.models.Subreddit;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@PropertySource("classpath:templates/sql/user_queries.properties")
public class UserService {

    private final JdbcTemplate jdbcTemplate;
    private final PostService postService;
    private final Environment environment;

    public UserService(JdbcTemplate jdbcTemplate, PostService postService, Environment environment) {
        this.jdbcTemplate = jdbcTemplate;
        this.postService = postService;
        this.environment = environment;
    }

    public List<RedditUser> findUsersBySubredditId(int subredditId) {
        String sql = environment.getRequiredProperty("select-user.subredditId");
        return jdbcTemplate.query(sql, new Object[]{subredditId}, new BeanPropertyRowMapper<>(RedditUser.class));
    }

    public List<Post> getUserFeed(String userLogin) {
        String sql = environment.getRequiredProperty("select-posts.subredditId");
        List<Subreddit> subscribedSubreddits = jdbcTemplate.query(sql, new Object[]{userLogin},
                new BeanPropertyRowMapper<>(Subreddit.class));
        return subscribedSubreddits.stream()
                .flatMap(subreddit -> postService.getAllPostBySubredditId(subreddit.getId()).stream())
                .collect(Collectors.toList());
    }
}
