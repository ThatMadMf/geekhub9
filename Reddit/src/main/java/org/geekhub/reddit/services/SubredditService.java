package org.geekhub.reddit.services;

import org.apache.catalina.User;
import org.geekhub.reddit.db.models.RedditUser;
import org.geekhub.reddit.db.models.Subreddit;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubredditService {

    private JdbcTemplate jdbcTemplate;
    private PostService postService;
    private UserService userService;

    public SubredditService(JdbcTemplate jdbcTemplate, PostService postService, UserService userService) {
        this.jdbcTemplate = jdbcTemplate;
        this.postService = postService;
        this.userService = userService;
    }

    public List<Subreddit> getAllSubreddits() {
        String sql = "select * from reddit.subreddits";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Subreddit.class));
    }

    public Subreddit getSubredditById(int id) {
        String sql = "select * from reddit.subreddits where id = ?";
        Subreddit subreddit = jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Subreddit.class));
        subreddit.setPosts(postService.getAllPostBySubredditId(subreddit.getId()));
        return subreddit;
    }

    public Subreddit addSubreddit(Subreddit subreddit) {
        String sql = "insert into reddit.subreddits (name, creator_login, creation_date) values (?, ?, ?)";
        jdbcTemplate.update(sql, subreddit.getName(), subreddit.getCreatorLogin(), subreddit.getCreationDate());
        return subreddit;
    }

    public List<RedditUser> getSubscribers(int subredditId) {
        return userService.findUsersBySubredditId(subredditId);
    }

    public Subreddit subscribeUser(int subredditId, String userLogin) {
        String sql = "insert into reddit.subreddit_user (user_login, subreddit_id) values (?, ?)";
        jdbcTemplate.update(sql, userLogin, subredditId);
        return getSubredditById(subredditId);
    }
}
