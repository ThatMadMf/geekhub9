package org.geekhub.reddit.web.configuration;

import org.geekhub.reddit.db.dtos.RegistrationDto;
import org.geekhub.reddit.db.dtos.UserDao;
import org.geekhub.reddit.db.models.RedditUser;
import org.geekhub.reddit.services.PostService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public class RegistrationService implements UserDetailsService, UserDao {

    private final JdbcTemplate jdbcTemplate;

    public RegistrationService(JdbcTemplate jdbcTemplate, PostService postService) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public UserDetails loadUserByUsername(String login) {
        RegistrationDto user = findUserById(login);

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
    public void register(RegistrationDto registrationDto) {
        String sql = "insert into reddit.users (id, login, email, password, registration_date) " +
                "values (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, UUID.randomUUID(), registrationDto.getLogin(), registrationDto.getEmail(),
                new BCryptPasswordEncoder().encode(registrationDto.getPassword()), LocalDate.now());
    }

    private RegistrationDto findUserById(String login) {
        String sql = "select * from reddit.users where login = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{login}, new BeanPropertyRowMapper<>(RegistrationDto.class));
    }
}
