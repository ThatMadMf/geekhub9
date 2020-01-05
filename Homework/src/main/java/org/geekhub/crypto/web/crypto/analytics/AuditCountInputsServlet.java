package org.geekhub.crypto.web.crypto.analytics;

import org.geekhub.crypto.analytics.CodingAudit;
import org.geekhub.crypto.history.CodingHistory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class AuditCountInputsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        try(PrintWriter out = resp.getWriter()) {
            CodingAudit audit = new CodingAudit(new CodingHistory("/home/the_guy/IdeaProjects/geekhub9/Homework"));
            Map<String, Integer> res = audit.countEncodingInputs();
            for(Map.Entry<String, Integer> entry : res.entrySet()) {
                out.println(entry.getKey() + "\t" + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
