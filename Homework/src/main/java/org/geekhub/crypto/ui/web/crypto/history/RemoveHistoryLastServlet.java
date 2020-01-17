package org.geekhub.crypto.ui.web.crypto.history;

import org.geekhub.crypto.exception.WebException;
import org.geekhub.crypto.history.CodingHistory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/application/history/remove-last")
public class RemoveHistoryLastServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html");
            out.println("<form action = \"\" method = \"POST\">\n" +
                    "<input type = \"submit\"/>\n" +
                    "</form>");
        } catch (IOException e) {
            throw new WebException(e.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html");
            CodingHistory history = new CodingHistory();
            history.removeLastRecord();
            out.println("Removing is success");
            out.println("<a href=\"/geekhub/application>Go to menu</a>");
        } catch (IOException e) {
            throw new WebException(e.getMessage());
        }
    }
}
