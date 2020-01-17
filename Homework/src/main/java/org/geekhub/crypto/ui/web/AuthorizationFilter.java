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

@WebFilter(filterName = "AuthorizationFileter", urlPatterns = {"/application/analytics", "/application/analytics/*",
        "/application/history/remove-last", "/application/history/clear-history"})
public class AuthorizationFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html");
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userRole") == null) {
                out.write("Wrong login<br>");
                out.write("<a href=\"/geekhub/\">Login</a>");
            } else {
                String userRole = session.getAttribute("userRole").toString();
                if (!userRole.equals("admin")) {
                    out.write("You do not have rights to go<br>");
                    out.write("<a href=\"/geekhub/\">Login</a>");
                } else {
                    chain.doFilter(request, response);
                }
            }
        } catch (IOException | ServletException e) {
            throw new WebException(e.getMessage());
        }
    }
}
