package org.geekhub.crypto.ui.web.crypto;

import com.google.common.base.Stopwatch;
import org.geekhub.crypto.logging.Logger;
import org.geekhub.crypto.logging.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@WebFilter(urlPatterns = "/*")
public class RequestLogger extends HttpFilter {

    private static final Logger logger = LoggerFactory.getLogger();

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
