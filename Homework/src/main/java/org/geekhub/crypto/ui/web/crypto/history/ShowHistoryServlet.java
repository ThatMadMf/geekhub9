package org.geekhub.crypto.ui.web.crypto.history;

import org.geekhub.crypto.exception.WebException;
import org.geekhub.crypto.history.HistoryManager;
import org.geekhub.crypto.history.ResponseHistoryPrinter;
import org.geekhub.crypto.util.ApplicationContextWrapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/application/history/show-history")
public class ShowHistoryServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            HistoryManager history = ApplicationContextWrapper.getBean(HistoryManager.class);
            ResponseHistoryPrinter printer = new ResponseHistoryPrinter(out);
            printer.print(history.readHistory());
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e);
        }
    }
}
