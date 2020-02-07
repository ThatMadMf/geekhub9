package org.geekhub.crypto.ui.web.iterceptor;

import com.google.common.base.Stopwatch;
import org.geekhub.crypto.logging.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class RequestLogger extends HandlerInterceptorAdapter {

    private final Logger logger;

    public RequestLogger(@Qualifier("getLogger") Logger logger) {
        this.logger = logger;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            Stopwatch stopwatch = Stopwatch.createStarted();

            request.setAttribute("REQUEST_START", stopwatch);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        if (handler instanceof HandlerMethod) {
            if (Objects.isNull(ex)) {
                Stopwatch requestStart = (Stopwatch) request.getAttribute("REQUEST_START");

                Stopwatch requestEnd = requestStart.stop();

                String requestURI = request.getRequestURI();

                String logMessage = "request to " + requestURI + " completed in: "
                        + requestEnd.elapsed(TimeUnit.MILLISECONDS) + " ms";

                logger.log(logMessage);
            }
        }
    }
}
