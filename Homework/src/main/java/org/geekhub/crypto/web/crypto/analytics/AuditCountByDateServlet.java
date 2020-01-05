package org.geekhub.crypto.web.crypto.analytics;

import org.geekhub.crypto.analytics.CodecUsecase;
import org.geekhub.crypto.analytics.CodingAudit;
import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.coders.Encoder;
import org.geekhub.crypto.coders.EncodersFactory;
import org.geekhub.crypto.history.CodingHistory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Map;

public class AuditCountByDateServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try(PrintWriter out = response.getWriter()) {
            response.setContentType("text/html");
            out.println("<form action = \"\" method = \"POST\">\n" +
                    "Enter codec use case: <select name = \"usecase\">\n");
            for (CodecUsecase usecase : CodecUsecase.values()) {
                out.println(" <option value=\"" + usecase.name() + "\">" + usecase.name() + "</option>");
            }
            out.println("</select>" + "<br>" +
                    "<input type = \"submit\" value = \"Submit\"/>\n" +
                    "</form>\n");
        } catch (IOException e ) {
            System.out.println(e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try(PrintWriter out = response.getWriter()) {
            CodecUsecase usecase = CodecUsecase.valueOf(request.getParameter("usecase"));

            CodingAudit audit = new CodingAudit(new CodingHistory());
            Map<LocalDate, Long> res = audit.countCodingsByDate(usecase);
            for(Map.Entry<LocalDate, Long> entry : res.entrySet()) {
                out.println(entry.getKey() + "\t" + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
