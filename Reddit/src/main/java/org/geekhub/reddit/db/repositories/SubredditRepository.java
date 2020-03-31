package org.geekhub.reddit.db.repositories;

import org.geekhub.reddit.db.models.RedditUser;
import org.geekhub.reddit.db.models.Subreddit;
import org.geekhub.reddit.util.ResourceReader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubredditRepository {

    private static final String RESOURCE_PATH = "templates/sql/subreddit/";
    private static final String SELECT_ALL = "select_all.sql";
    private static final String SELECT_SUBSCRIBERS = "select-users_id.sql";
    private static final String SELECT_BY_ID = "select_id.sql";
    private static final String INSERT_SUBREDDIT = "insert.sql";
    private static final String BIND_USER_SUBREDDIT = "bind_user-subreddit.sql";


    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Subreddit> subredditMapper;
    private final BeanPropertyRowMapper<RedditUser> userMapper;


    public SubredditRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        subredditMapper = new BeanPropertyRowMapper<>(Subreddit.class);
        userMapper = new BeanPropertyRowMapper<>(RedditUser.class);
    }

    public List<Subreddit> getAllSubreddits() {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + SELECT_ALL);
        return jdbcTemplate.query(sql, subredditMapper);
    }


    public List<RedditUser> getSubscribers(int subredditId) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + SELECT_SUBSCRIBERS);
        return jdbcTemplate.query(sql, new Object[]{subredditId}, userMapper);
    }

    public Subreddit getSubredditById(int id) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + SELECT_BY_ID);
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, subredditMapper);
    }

    public Subreddit createSubreddit(Subreddit subreddit) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + INSERT_SUBREDDIT);
        jdbcTemplate.update(sql, subreddit.getName(), subreddit.getCreatorId(), subreddit.getCreationDate());
        return subreddit;
    }

    public Subreddit subscribeUser(int subredditId, int id) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + BIND_USER_SUBREDDIT);
        jdbcTemplate.update(sql, id, subredditId);
        return getSubredditById(subredditId);
    }
}
