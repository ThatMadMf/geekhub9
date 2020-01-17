package org.geekhub.crypto.ui.web;

import org.geekhub.crypto.exception.WebException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "SessionFilter",  urlPatterns = {"/application", "/application/*"})
public class SessionFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) {
        try (PrintWriter out = resp.getWriter()) {
            resp.setContentType("text/html");
            HttpSession session = req.getSession(false);
            if (session == null) {
                out.write("Wrong login<br>");
                out.write("<a href=\"/geekhub/\">Login</a>");
            } else {
                String userRole = session.getAttribute("userRole").toString();
                if (userRole == null) {
                    out.write("Wrong login");
                    out.write("a href=\"/geekhub/\">Login</a>");
                } else {
                    chain.doFilter(req, resp);
                }
            }
        } catch (IOException | ServletException e) {
            throw new WebException(e.getMessage(), e);
        }
    }
}

