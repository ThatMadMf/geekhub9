package org.geekhub.crypto.web.crypto;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ErrorHandler extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html");
            out.write("<h2>:( Something went wrong</h2>");
            List.of(RequestDispatcher.ERROR_STATUS_CODE,
                    RequestDispatcher.ERROR_EXCEPTION_TYPE,
                    RequestDispatcher.ERROR_MESSAGE)
                    .forEach(e -> out.println(request.getAttribute(e)));

        }
    }
}
