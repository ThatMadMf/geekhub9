package org.geekhub.reddit.services;

import org.geekhub.reddit.db.models.Subreddit;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubredditService {

    private JdbcTemplate jdbcTemplate;

    public SubredditService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Subreddit> getAllSubreddits() {
        String sql = "select * from reddit.subreddits";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Subreddit.class));
    }

    public Subreddit getSubredditById(int id) {
        String sql = "select * from reddit.subreddits where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Subreddit.class));
    }

    public Subreddit addSubreddit(Subreddit subreddit) {
        String sql = "insert into reddit.subreddits (name, creator_login, creation_date) values (?, ?, ?)";
        jdbcTemplate.update(sql, subreddit.getName(), subreddit.getCreatorLogin(), subreddit.getCreationDate());
        return subreddit;
    }
}
