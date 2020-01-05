package org.geekhub.crypto.web.crypto.coders;

import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.coders.Encoder;
import org.geekhub.crypto.coders.EncodersFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EncodeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<form action = \"\" method = \"POST\">\n" +
                "Enter encode algorithm: <select name = \"algorithm\">\n");
        for (Algorithm algorithm : Algorithm.values()) {
            out.println(" <option value=\"" + algorithm.name() + "\">" + algorithm.name() + "</option>");
        }
        out.println("</select>" + "<br>" +
                "Enter text to encode: <input name = \"text\">\n" +
                "<input type = \"submit\" value = \"Submit\"/>\n" +
                "</form>\n");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Algorithm algorithm = Algorithm.valueOf(req.getParameter("algorithm"));
        String text = req.getParameter("text");

        Encoder decoder = EncodersFactory.getEncoder(algorithm);
        out.println(decoder.encode(text));
    }
}
