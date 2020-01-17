package org.geekhub.crypto.ui.web.crypto.history;

import org.geekhub.crypto.exception.WebException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/application/history")
public class HistoryMenuServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try(PrintWriter out = response.getWriter()) {
            response.setContentType("text/html");
            out.println("<h2>History operations<h2>");
            out.println("<a href=\"/geekhub/application/history/show-history\">1. Show history</a><br>");
            out.println("<a href=\"/geekhub/application/history/remove-last\">2. Remove last element</a><br>");
            out.println("<a href=\"/geekhub/application/history/clear-history\">3. Clear history</a><br>");
        } catch (IOException e){
            throw new WebException(e.getMessage());
        }
    }
}
