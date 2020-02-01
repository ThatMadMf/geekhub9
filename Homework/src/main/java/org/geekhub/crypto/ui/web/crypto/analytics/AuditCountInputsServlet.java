package org.geekhub.crypto.ui.web.crypto.analytics;

import org.geekhub.crypto.analytics.CodingAudit;
import org.geekhub.crypto.exception.WebException;
import org.geekhub.crypto.history.HistoryManager;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/application/analytics/count-inputs")
public class AuditCountInputsServlet extends HttpServlet {

    private HistoryManager history;
    private CodingAudit audit;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        final ApplicationContext applicationContext =
                (ApplicationContext) config.getServletContext().getAttribute("APPLICATION_CONTEXT");
        this.history = applicationContext.getBean(HistoryManager.class);
        this.audit = applicationContext.getBean(CodingAudit.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (PrintWriter out = resp.getWriter()) {
            Map<String, Integer> res = audit.countEncodingInputs();
            for (Map.Entry<String, Integer> entry : res.entrySet()) {
                out.println(entry.getKey() + "\t" + entry.getValue());
            }
            history.addToHistory(new HistoryRecord(Operation.ANALYTICS, null, null));
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e);
        }
    }
}
