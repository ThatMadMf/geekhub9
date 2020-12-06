package org.geekhub.crypto.web.configuration;

import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    private final JdbcTemplate jdbcTemplate;

    public AuthenticationSuccessListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        jdbcTemplate.execute("UPDATE geekhub.users SET password_uses = password_uses + 1 where username = 'user'");
    }
}
