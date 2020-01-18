package org.geekhub.crypto.ui.web.crypto.analytics;

import org.geekhub.crypto.analytics.CodecUsecase;
import org.geekhub.crypto.analytics.CodingAudit;
import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.exception.WebException;
import org.geekhub.crypto.history.HistoryManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/application/analytics/find-most-popular-codec")
public class AuditFindMostPopularServlet extends HttpServlet {

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

            CodingAudit audit = new CodingAudit(new HistoryManager().readHistory());
            Algorithm res = audit.findMostPopularCodec(usecase);
            out.println(res.name());
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e);
        }
    }
}
