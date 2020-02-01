package org.geekhub.crypto.ui.web.crypto.filters;

import com.google.common.base.Stopwatch;
import org.geekhub.crypto.logging.CompositeLogger;
import org.geekhub.crypto.logging.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@WebFilter(urlPatterns = "/*", filterName = "filter2")
public class RequestLogger extends HttpFilter {

    private Logger logger;

    @Override
    public void init(FilterConfig config) throws ServletException {
        super.init();
        final ApplicationContext applicationContext =
                (ApplicationContext) config.getServletContext().getAttribute("APPLICATION_CONTEXT");
        this.logger = applicationContext.getBean(CompositeLogger.class);
    }

    @Override
    public void doFilter(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain
    ) throws IOException, ServletException {

        Stopwatch stopwatch = Stopwatch.createStarted();

        chain.doFilter(request, response);

        Stopwatch endStopwatch = stopwatch.stop();

        String requestURI = request.getRequestURI();

        String logMessage = "request to " + requestURI + " completed in: "
                + endStopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms";

        logger.log(logMessage);
    }

}
