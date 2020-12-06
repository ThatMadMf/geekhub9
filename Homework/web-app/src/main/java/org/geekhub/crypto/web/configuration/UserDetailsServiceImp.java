package org.geekhub.crypto.web.configuration;

import org.geekhub.crypto.web.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<User> userMapper;

    public UserDetailsServiceImp(JdbcTemplate jdbcTemplate) {
        userMapper = new BeanPropertyRowMapper<>(User.class);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findUserByUsername(username);

        UserBuilder builder;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(user.getPassword());
            builder.roles("USER");
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }

    public void changePassword(String password) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);

        jdbcTemplate.update("UPDATE geekhub.users SET password = ?, password_uses = 0 where username = 'user'",
                encryptedPassword);
    }

    private User findUserByUsername(String username) {
        String query = "select * from geekhub.users where username = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{username}, userMapper);
    }
}
