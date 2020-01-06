package org.geekhub.crypto.web.crypto;

import org.geekhub.crypto.exception.WebException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class FrontServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>\n" +
                    "   <body>\n" +
                    "      <form action = \"\" method = \"POST\">\n" +
                    "         Enter User role: <input type = \"text\" name = \"user\">\n" +
                    "\t <input type = \"submit\" value = \"Submit\" />\n" +
                    "      </form>\n" +
                    "   </body>\n" +
                    "</html>");
        } catch (IOException e) {
            throw new WebException(e.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html");
            String user = request.getParameter("user");
            HttpSession session = request.getSession();
            if (user.equals("admin")) {
                session.setAttribute("userRole", "admin");
                response.sendRedirect("/geekhub/application");
            } else if (user.equals("user")) {
                session.setAttribute("userRole", "user");
                response.sendRedirect("/geekhub/application");
            } else {
                response.sendRedirect("/geekhub");
            }
        } catch (IOException e) {
            throw new WebException(e.getMessage());
        }
    }
}
