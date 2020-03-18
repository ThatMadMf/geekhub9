package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.Subreddit;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubredditService {

    private JdbcTemplate jdbcTemplate;
    private PostService postService;

    public SubredditService(JdbcTemplate jdbcTemplate, PostService postService) {
        this.jdbcTemplate = jdbcTemplate;
        this.postService = postService;
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
}
