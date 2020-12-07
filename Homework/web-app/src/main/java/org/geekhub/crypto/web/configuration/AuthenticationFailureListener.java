package org.geekhub.crypto.web.configuration;

import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final JdbcTemplate jdbcTemplate;

    public AuthenticationFailureListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        System.out.println("here");
        jdbcTemplate.execute("UPDATE geekhub.users SET invalid_inputs = invalid_inputs + 1 where username = 'user'");

    }
}