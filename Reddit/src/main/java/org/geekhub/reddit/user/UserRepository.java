package org.geekhub.reddit.user;

import org.geekhub.reddit.exception.DataBaseRowException;
import org.geekhub.reddit.exception.NoRightsException;
import org.geekhub.reddit.post.Post;
import org.geekhub.reddit.subreddit.Subreddit;
import org.geekhub.reddit.user.dto.*;
import org.geekhub.reddit.util.ResourceReader;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public class UserRepository {

    private static final String RESOURCE_PATH = "static/sql/user/";
    private static final String SELECT_BY_ID = getSql("select_id.sql");
    private static final String SELECT_BY_LOGIN = getSql("select-safe_login.sql");
    private static final String SELECT_SUBREDDITS = getSql("select-subreddits_id.sql");
    private static final String SELECT_POSTS = getSql("select-posts_id.sql");
    private static final String DELETE_BY_ID = getSql("delete_id.sql");
    private static final String INSERT_USER = getSql("insert.sql");
    private static final String INSERT_USER_ROLE = getSql("insert-role_id.sql");
    private static final String UPDATE_USER = getSql("update_id.sql");
    private static final String UPDATE_USER_ROLE = getSql("update-role_id.sql");


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
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_LOGIN, new Object[]{login}, userMapper);
        } catch (EmptyResultDataAccessException ex) {
            throw new DataBaseRowException("There is no user with this login");
        }
    }

    public PrivateRedditUser getUserInfo(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{id}, privateDataMapper);
        } catch (EmptyResultDataAccessException ex) {
            throw new DataBaseRowException("There is no user with this id");
        }
    }

    @Transactional
    public int registerUser(RegistrationDto registrationDto) {
        jdbcTemplate.update(INSERT_USER, registrationDto.getLogin(), registrationDto.getEmail(),
                new BCryptPasswordEncoder().encode(registrationDto.getPassword()), LocalDate.now());

        int id = getUserByLogin(registrationDto.getLogin()).getId();

        return jdbcTemplate.update(INSERT_USER_ROLE, id);
    }

    public List<Subreddit> getSubscriptions(int id) {
        return jdbcTemplate.query(SELECT_SUBREDDITS, new Object[]{id}, subredditMapper);
    }

    public List<Post> getPosts(int id) {
        return jdbcTemplate.query(SELECT_POSTS, new Object[]{id}, postMapper);
    }

    public int deleteUser(int id) {
        if (getUserInfo(id).getRole().equals("SUPER_ADMIN")) {
            throw new NoRightsException("You cant delete super admin user");
        }
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    private static String getSql(String fileName) {
        return ResourceReader.getSql(RESOURCE_PATH + fileName);
    }

    public PrivateRedditUser editUser(int id, UserDto userDto) {
        int queryResult = jdbcTemplate.update(UPDATE_USER, userDto.getLogin(), userDto.getEmail(), id);
        if (queryResult == 1) {
            PrivateRedditUser redditUser = getUserInfo(id);
            redditUser.setPassword(null);
            return redditUser;
        }
        throw new DataBaseRowException("Failed to update user data");
    }

    public PrivateRedditUser editUserRole(int id, Role role) {
        int queryResult = jdbcTemplate.update(UPDATE_USER_ROLE, role.name(), id);
        if (queryResult == 1) {
            PrivateRedditUser redditUser = getUserInfo(id);
            redditUser.setPassword(null);
            return redditUser;
        }
        throw new DataBaseRowException("Failed to update user role");
    }
}
