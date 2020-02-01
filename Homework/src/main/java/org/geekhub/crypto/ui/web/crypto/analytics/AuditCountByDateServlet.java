package org.geekhub.crypto.ui.web.crypto.analytics;

import org.geekhub.crypto.analytics.CodecUsecase;
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
import java.time.LocalDate;
import java.util.Map;

@WebServlet(urlPatterns = "/application/analytics/count-by-date")
public class AuditCountByDateServlet extends HttpServlet {

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
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html");
            out.println("<form action = \"\" method = \"POST\">\n" +
                    "Enter codec use case: <select name = \"usecase\">\n");
            for (CodecUsecase usecase : CodecUsecase.values()) {
                out.println(" <option value=\"" + usecase.name() + "\">" + usecase.name() + "</option>");
            }
            out.println("</select>" + "<br>" +
                    "<input type = \"submit\" value = \"Submit\"/>\n" +
                    "</form>\n");
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            CodecUsecase usecase = CodecUsecase.valueOf(request.getParameter("usecase"));

            Map<LocalDate, Long> res = audit.countCodingsByDate(usecase);
            for (Map.Entry<LocalDate, Long> entry : res.entrySet()) {
                out.println(entry.getKey() + "\t" + entry.getValue());
            }
            history.addToHistory(new HistoryRecord(Operation.ANALYTICS, null, null));
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e);
        }
    }
}
