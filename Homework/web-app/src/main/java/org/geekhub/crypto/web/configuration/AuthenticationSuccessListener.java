package org.geekhub.crypto.web.configuration;

import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    private final JdbcTemplate jdbcTemplate;

    public AuthenticationSuccessListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        String invalidQuery = "SELECT invalid_inputs FROM geekhub.users where username = 'user'";

        int invalids = jdbcTemplate.queryForObject(invalidQuery, Integer.class);

        if (invalids < 6) {
            System.out.println(invalids);
            jdbcTemplate.execute("UPDATE geekhub.users SET password_uses = password_uses + 1, invalid_inputs = 0 " +
                    "where username = 'user'");
        }
    }
}
