package org.geekhub.crypto.ui.web.crypto.filters;

import org.geekhub.crypto.util.ApplicationContextWrapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "filter1")
public class ContexInitFilter extends HttpFilter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filterConfig.getServletContext().setAttribute(
                "APPLICATION_CONTEXT",
                new AnnotationConfigApplicationContext(ApplicationContextWrapper.class));
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        super.doFilter(req, res, chain);
    }

}
