package org.geekhub.crypto.ui.web.crypto.history;

import org.geekhub.crypto.exception.WebException;
import org.geekhub.crypto.history.HistoryManager;
import org.geekhub.crypto.history.ResponseHistoryPrinter;
import org.geekhub.crypto.util.ApplicationContextWrapper;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/application/history/show-history")
public class ShowHistoryServlet extends HttpServlet {

    private HistoryManager history;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        final ApplicationContext applicationContext =
                (ApplicationContext) config.getServletContext().getAttribute("APPLICATION_CONTEXT");
        this.history = applicationContext.getBean(HistoryManager.class);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            ResponseHistoryPrinter printer = new ResponseHistoryPrinter(out);
            printer.print(history.readHistory());
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e);
        }
    }
}
