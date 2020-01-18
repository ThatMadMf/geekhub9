package org.geekhub.crypto.ui.web.crypto.coders;

import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.coders.Decoder;
import org.geekhub.crypto.coders.DecodersFactory;
import org.geekhub.crypto.exception.WebException;
import org.geekhub.crypto.history.HistoryManager;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/application/decode")
public class DecodeServlet extends HttpServlet {

    private final HistoryManager history = new HistoryManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (PrintWriter out = resp.getWriter()) {
            resp.setContentType("text/html");
            out.println("<form action = \"\" method = \"POST\">\n" +
                    "Enter decode algorithm: <select name = \"algorithm\">\n");
            for (Algorithm algorithm : Algorithm.values()) {
                out.println(" <option value=\"" + algorithm.name() + "\">" + algorithm.name() + "</option>");
            }
            out.println("</select>" + "<br>" +
                    "Enter text to decode: <input name = \"text\">\n" +
                    "<input type = \"submit\" value = \"Submit\"/>\n" +
                    "</form>\n");
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (PrintWriter out = resp.getWriter()) {

            String algorithmParameter = req.getParameter("algorithm");
            if (algorithmParameter == null) {
                out.println("Illegal usage of not existing codec");
            } else {
                Algorithm algorithm = Algorithm.valueOf(algorithmParameter);
                String text = req.getParameter("text");

                Decoder decoder = DecodersFactory.getDecoder(algorithm);
                history.addToHistory(new HistoryRecord(Operation.DECODE, text, algorithm));
                out.println(decoder.decode(text));
            }
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e);
        }
    }
}
