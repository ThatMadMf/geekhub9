package org.geekhub.crypto.web.crypto.analytics;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AnalyticsMenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try(PrintWriter out = resp.getWriter()) {
            resp.setContentType("text/html");
            out.println("<h2>Analytics</h2>");
            out.println("<a href=\"/geekhub/application/analytics/count_inputs\">1. Count inputs</a><br>");
            out.println("<a href=\"/geekhub/application/analytics/count_by_date\">2. Count by date</a><br>");
            out.println("<a href=\"/geekhub/application/analytics/find_most_popular_codec\">" +
                    "3. Find most popular algorithm</a><br>");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
