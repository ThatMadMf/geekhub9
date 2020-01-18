package org.geekhub.crypto.ui.web.crypto.history;

import org.geekhub.crypto.exception.WebException;
import org.geekhub.crypto.history.HistoryManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/application/history/clear-history")
public class ClearHistoryServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html");
            out.println("<form action = \"\" method = \"POST\">\n" +
                    "<input type = \"submit\" value = \"Clear History\"/>\n" +
                    "</form>");
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html");
            HistoryManager history = new HistoryManager();
            history.clearHistory();
            out.println("<p>Removing is success</p>");
            out.println("<a href=\"/geekhub/application>Go to menu</a>");
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e);
        }
    }
}
