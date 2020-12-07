package org.geekhub.crypto.web.iterceptor;

import org.geekhub.crypto.web.model.System;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class PasswordInterceptor extends HandlerInterceptorAdapter {

    private final JdbcTemplate jdbcTemplate;
    private final RestTemplate restTemplate;


    public PasswordInterceptor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String invalidQuery = "SELECT invalid_inputs FROM geekhub.users where username = 'user'";

        int invalids = jdbcTemplate.queryForObject(invalidQuery, Integer.class);

        if (invalids > 4) {
            try {
                String url = "http://localhost:5005/status";

                HttpHeaders headers = new HttpHeaders();

                headers.setContentType(MediaType.APPLICATION_JSON);

                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                Map<String, Object> map = new HashMap<>();
                map.put("id", 1);
                map.put("status", "blocked");

                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

                ResponseEntity<System> reqResp = this.restTemplate.postForEntity(url, entity, System.class);

                String password = new BCryptPasswordEncoder().encode("user");

                if (reqResp.getBody().getStatus().equals("unblocked")) {
                    jdbcTemplate.update("UPDATE geekhub.users SET invalid_inputs = 0, password_uses = 0, " +
                            "password = ? WHERE username = 'user'", password);
                    response.sendRedirect("/update-password");
                } else {
                    response.getWriter().println("System is blocked due to multiple failed authentication attempts. " +
                            "Contact developer in order to unblock system.");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        if (request.getRequestURI().equals("/update-password")) {
            return true;
        }

        String usesQuery = "SELECT password_uses FROM geekhub.users where username = 'user'";

        int uses = jdbcTemplate.queryForObject(usesQuery, Integer.class);

        try {
            if (uses < 6) {
                return true;
            } else {
                response.sendRedirect("/update-password");
                return false;
            }
        } catch (IOException e) {
            e.getMessage();
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
    }
}