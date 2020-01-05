package org.geekhub.crypto.web.crypto;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class CheckSessionServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try(PrintWriter out = response.getWriter()){
            HttpSession session = request.getSession();
            out.println(session.getAttribute("userRole"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
