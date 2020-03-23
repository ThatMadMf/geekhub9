package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.RedditUser;
import org.geekhub.reddit.db.models.Subreddit;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PropertySource("classpath:templates/sql/subreddit_queries.properties")
public class SubredditService {

    private JdbcTemplate jdbcTemplate;
    private PostService postService;
    private UserService userService;
    private Environment environment;

    public SubredditService(JdbcTemplate jdbcTemplate, PostService postService,
                            UserService userService, Environment environment) {
        this.jdbcTemplate = jdbcTemplate;
        this.postService = postService;
        this.userService = userService;
        this.environment = environment;
    }

    public List<Subreddit> getAllSubreddits() {
        String sql = environment.getRequiredProperty("select-subreddits");
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Subreddit.class));
    }

    public Subreddit getSubredditById(int id) {
        String sql = environment.getRequiredProperty("select-subreddit.id");
        Subreddit subreddit = jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Subreddit.class));
        subreddit.setPosts(postService.getAllPostBySubredditId(subreddit.getId()));
        return subreddit;
    }

    public Subreddit addSubreddit(Subreddit subreddit) {
        String sql = environment.getRequiredProperty("insert.subreddit");
        jdbcTemplate.update(sql, subreddit.getName(), subreddit.getCreatorLogin(), subreddit.getCreationDate());
        return subreddit;
    }

    public List<RedditUser> getSubscribers(int subredditId) {
        return userService.findUsersBySubredditId(subredditId);
    }

    public Subreddit subscribeUser(int subredditId, String userLogin) {
        String sql = environment.getRequiredProperty("bind.user-subreddit");
        jdbcTemplate.update(sql, userLogin, subredditId);
        return getSubredditById(subredditId);
    }
}
