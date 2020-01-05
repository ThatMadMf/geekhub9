package org.geekhub.crypto.web.crypto.history;

import org.geekhub.crypto.history.CodingHistory;
import org.geekhub.crypto.history.ResponseHistoryPrinter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ShowHistoryServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try(PrintWriter out = response.getWriter()) {
            CodingHistory history = new CodingHistory();
            ResponseHistoryPrinter printer = new ResponseHistoryPrinter(out);
            printer.print(history.getHistoryRecords());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
