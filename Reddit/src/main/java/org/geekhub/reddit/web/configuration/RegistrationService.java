package org.geekhub.reddit.web.configuration;

import org.geekhub.reddit.db.dtos.RegistrationDto;
import org.geekhub.reddit.db.dtos.UserDao;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
@PropertySource("classpath:templates/sql/user_queries.properties")
public class RegistrationService implements UserDetailsService, UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final Environment environment;

    public RegistrationService(JdbcTemplate jdbcTemplate, Environment environment) {
        this.jdbcTemplate = jdbcTemplate;
        this.environment = environment;
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
        String sql = environment.getRequiredProperty("insert-user");

        jdbcTemplate.update(sql, UUID.randomUUID(), registrationDto.getLogin(), registrationDto.getEmail(),
                new BCryptPasswordEncoder().encode(registrationDto.getPassword()), LocalDate.now());
    }

    private RegistrationDto findUserById(String login) {
        String sql = environment.getRequiredProperty("select-user.byId");
        return jdbcTemplate.queryForObject(sql, new Object[]{login},
                new BeanPropertyRowMapper<>(RegistrationDto.class));
    }
}
