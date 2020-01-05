package org.geekhub.crypto.web.crypto.history;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HistoryMenuServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try(PrintWriter out = response.getWriter()) {
            response.setContentType("text/html");
            out.println("<h2>History operations<h2>");
            out.println("<a href=\"/geekhub/application/history/show_history\">1. Show history</a><br>");
            out.println("<a href=\"/geekhub/application/history/remove_last\">2. Remove last element</a><br>");
            out.println("<a href=\"/geekhub/application/history/clear_history\">3. Clear history</a><br>");
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
