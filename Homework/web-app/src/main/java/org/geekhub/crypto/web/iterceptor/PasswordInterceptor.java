package org.geekhub.crypto.web.iterceptor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class PasswordInterceptor extends HandlerInterceptorAdapter {

    private final JdbcTemplate jdbcTemplate;

    public PasswordInterceptor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (request.getRequestURI().toString().equals("/update-password")) {
            return true;
        }

        String query = "SELECT password_uses FROM geekhub.users where username = 'user'";

        int uses = jdbcTemplate.queryForObject(query, Integer.class);

        try {
            if (uses < 6) {
                return true;
            } else {
                response.sendRedirect("/update-password");
                return false;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
    }
}