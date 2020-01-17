package org.geekhub.crypto.ui.web.crypto.analytics;

import org.geekhub.crypto.exception.WebException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/application/analytics")
public class AnalyticsMenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (PrintWriter out = resp.getWriter()) {
            resp.setContentType("text/html");
            out.println("<h2>Analytics</h2>");
            out.println("<a href=\"/geekhub/application/analytics/count-inputs\">1. Count inputs</a><br>");
            out.println("<a href=\"/geekhub/application/analytics/count-by-date\">2. Count by date</a><br>");
            out.println("<a href=\"/geekhub/application/analytics/find-most-popular-codec\">" +
                    "3. Find most popular algorithm</a><br>");
        } catch (IOException e) {
            throw new WebException(e.getMessage());
        }
    }
}
