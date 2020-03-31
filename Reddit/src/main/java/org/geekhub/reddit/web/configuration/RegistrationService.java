package org.geekhub.reddit.web.configuration;

import org.geekhub.reddit.dtos.RegistrationDto;
import org.geekhub.reddit.dtos.UserDao;
import org.geekhub.reddit.exception.RegistrationException;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
            builder.password(user.getPassword());
            builder.roles("USER");
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }

    @Override
    public void register(HttpServletRequest request, RegistrationDto registrationDto) {
        String sql = environment.getRequiredProperty("insert-user");
        try {
            jdbcTemplate.update(sql, UUID.randomUUID(), registrationDto.getLogin(), registrationDto.getEmail(),
                    new BCryptPasswordEncoder().encode(registrationDto.getPassword()), LocalDate.now());
        } catch (Exception ex) {
            throw new RegistrationException("Login or email is taken. Try another one");
        }
        authWithHttpServletRequest(request, registrationDto.getLogin(), registrationDto.getPassword());
    }

    private void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            throw new RegistrationException("Cannot login");
        }
    }


    private RegistrationDto findUserById(String login) {
        String sql = environment.getRequiredProperty("select-user.login");
        return jdbcTemplate.queryForObject(sql, new Object[]{login},
                new BeanPropertyRowMapper<>(RegistrationDto.class));
    }
}
