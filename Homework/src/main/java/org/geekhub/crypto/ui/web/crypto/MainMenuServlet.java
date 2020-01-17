package org.geekhub.crypto.ui.web.crypto;

import org.geekhub.crypto.exception.WebException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/application")
public class MainMenuServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        try(PrintWriter out = response.getWriter()) {
            out.println("<h2>Crypto application</h2>");
            out.println("<a href=\"/geekhub/application/decode\">1. Decode</a><br>");
            out.println("<a href=\"/geekhub/application/encode\">2. Encode</a><br>");
            out.println("<a href=\"/geekhub/application/analytics\">3. Analytics</a><br>");
            out.println("<a href=\"/geekhub/application/history\">4. History</a><br>");
        } catch(IOException e) {
            throw new WebException(e.getMessage(), e);
        }
    }
}
