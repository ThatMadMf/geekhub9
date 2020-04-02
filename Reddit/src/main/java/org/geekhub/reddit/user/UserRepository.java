package org.geekhub.reddit.user;

import org.geekhub.reddit.post.Post;
import org.geekhub.reddit.subreddit.Subreddit;
import org.geekhub.reddit.util.ResourceReader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class UserRepository {

    private static final String RESOURCE_PATH = "templates/sql/user/";
    private static final String SELECT_BY_ID = "select_id.sql";
    private static final String SELECT_BY_LOGIN = "select-safe_login.sql";
    private static final String SELECT_SUBREDDITS = "select-subreddits_id.sql";
    private static final String SELECT_POSTS = "select-posts_id.sql";
    private static final String DELETE_BY_ID = "delete.sql";
    private static final String INSERT_USER = "insert.sql";

    private final BeanPropertyRowMapper<PrivateRedditUser> privateDataMapper;
    private final BeanPropertyRowMapper<RedditUser> userMapper;
    private final BeanPropertyRowMapper<Subreddit> subredditMapper;
    private final BeanPropertyRowMapper<Post> postMapper;
    private final JdbcTemplate jdbcTemplate;


    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        privateDataMapper = new BeanPropertyRowMapper<>(PrivateRedditUser.class);
        userMapper = new BeanPropertyRowMapper<>(RedditUser.class);
        subredditMapper = new BeanPropertyRowMapper<>(Subreddit.class);
        postMapper = new BeanPropertyRowMapper<>(Post.class);
    }

    public RedditUser getUserByLogin(String login) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + SELECT_BY_LOGIN);
        return jdbcTemplate.queryForObject(sql, new Object[]{login}, userMapper);
    }

    public PrivateRedditUser getUserInfo(int id) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + SELECT_BY_ID);
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, privateDataMapper);
    }

    public void registerUser(RegistrationDto registrationDto) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + INSERT_USER);
        jdbcTemplate.update(sql, registrationDto.getLogin(), registrationDto.getEmail(),
                new BCryptPasswordEncoder().encode(registrationDto.getPassword()), LocalDate.now());
    }

    public List<Subreddit> getSubscriptions(int id) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + SELECT_SUBREDDITS);
        return jdbcTemplate.query(sql, new Object[]{id}, subredditMapper);
    }

    public List<Post> getPosts(int id) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + SELECT_POSTS);
        return jdbcTemplate.query(sql, new Object[]{id}, postMapper);
    }

    public void deleteUser(int id) {
        String sql = ResourceReader.resourceByLocation(RESOURCE_PATH + DELETE_BY_ID);
        jdbcTemplate.update(sql, id);
    }

}
