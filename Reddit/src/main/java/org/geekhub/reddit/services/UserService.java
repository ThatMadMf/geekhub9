package org.geekhub.reddit.services;

import org.geekhub.reddit.db.dtos.Login;
import org.geekhub.reddit.db.dtos.UserDao;
import org.geekhub.reddit.db.models.RedditUser;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class UserService implements UserDetailsService, UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        RedditUser user = findUserById(login);

        User.UserBuilder builder;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(user.getLogin());
            builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
            builder.roles("USER");
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }

    @Override
    public void register(Login login) {
        String sql = "insert into reddit.users (id, login, email, password, registration_date) " +
                "values (?, ?, ?, ?, ?)";

        RedditUser redditUser = new RedditUser();

        redditUser.setLogin(login.getLogin());
        redditUser.setEmail(login.getEmail());
        redditUser.setPassword(login.getPassword());
        redditUser.setDate(LocalDate.now());
        redditUser.setUserId(UUID.randomUUID());

        jdbcTemplate.update(sql, redditUser.getUserId(), redditUser.getLogin(), redditUser.getEmail(),
                redditUser.getPassword(), redditUser.getDate());
    }

    public List<RedditUser> findUsersBySubredditId(int subredditId) {
        String sql = "select u" +
                "from reddit.users AS u INNER JOIN reddit.subreddit_user AS su ON u.login = su.user_login" +
                "where su.subreddit_id = ?";
        return jdbcTemplate.query(sql, new Object[]{subredditId}, new BeanPropertyRowMapper<>(RedditUser.class));
    }

    private RedditUser findUserById(String login) {
        String sql = "select * from reddit.users where login = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{login}, new BeanPropertyRowMapper<>(RedditUser.class));
    }

}
