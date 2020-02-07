package org.geekhub.crypto.ui.web.configuration;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
public class GlobalExceptionHandler implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String exceptionHandler(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "<h2>404</h2><h3>Page not found</h3>";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "<h2>500</h2>" +
                        "<h3>Internal Server Error</h3>";
            }
        }
        return "<h2>Unknown Error</h2>";
    }
}
