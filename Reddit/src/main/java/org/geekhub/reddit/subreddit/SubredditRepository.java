package org.geekhub.reddit.subreddit;

import org.geekhub.reddit.exception.DataBaseRowException;
import org.geekhub.reddit.user.RedditUser;
import org.geekhub.reddit.util.ResourceReader;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubredditRepository {

    private static final String RESOURCE_PATH = "templates/sql/subreddit/";
    private static final String SELECT_ALL = getSql("select_all.sql");
    private static final String SELECT_SUBSCRIBERS = getSql("select-users_id.sql");
    private static final String SELECT_BY_ID = getSql("select_id.sql");
    private static final String INSERT_SUBREDDIT = getSql("insert.sql");
    private static final String BIND_USER_SUBREDDIT = getSql("bind_user-subreddit.sql");


    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Subreddit> subredditMapper;
    private final BeanPropertyRowMapper<RedditUser> userMapper;


    public SubredditRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        subredditMapper = new BeanPropertyRowMapper<>(Subreddit.class);
        userMapper = new BeanPropertyRowMapper<>(RedditUser.class);
    }

    public List<Subreddit> getAllSubreddits() {
        return jdbcTemplate.query(SELECT_ALL, subredditMapper);
    }


    public List<RedditUser> getSubscribers(int subredditId) {
        return jdbcTemplate.query(SELECT_SUBSCRIBERS, new Object[]{subredditId}, userMapper);
    }

    public Subreddit getSubredditById(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{id}, subredditMapper);
        } catch (EmptyResultDataAccessException ex) {
            throw new DataBaseRowException("Subreddit with the id do not exists");
        }

    }

    public int createSubreddit(Subreddit subreddit) {
        return jdbcTemplate.update(INSERT_SUBREDDIT, subreddit.getName(), subreddit.getCreatorId(),
                subreddit.getCreationDate());
    }

    public int subscribeUser(int subredditId, int id) {
        return jdbcTemplate.update(BIND_USER_SUBREDDIT, id, subredditId);
    }

    private static String getSql(String fileName) {
        return ResourceReader.getSql(RESOURCE_PATH + fileName);
    }
}
