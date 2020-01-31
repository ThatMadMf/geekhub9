package org.geekhub.crypto.ui.web.crypto.analytics;

import org.geekhub.crypto.analytics.CodingAudit;
import org.geekhub.crypto.exception.WebException;
import org.geekhub.crypto.history.HistoryManager;
import org.geekhub.crypto.util.ApplicationContextWrapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/application/analytics/count-inputs")
public class AuditCountInputsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (PrintWriter out = resp.getWriter()) {
            CodingAudit audit = ApplicationContextWrapper.getBean(CodingAudit.class);
            Map<String, Integer> res = audit.countEncodingInputs();
            for (Map.Entry<String, Integer> entry : res.entrySet()) {
                out.println(entry.getKey() + "\t" + entry.getValue());
            }
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e);
        }
    }
}
