package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/welcomeServlet")
public class WelcomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve the logged-in user's email from the session
        HttpSession session = request.getSession();
        String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");

        // Display welcome message
        out.println("<html><body>");
        out.println("<h2>Welcome to Student Portal</h2>");
        out.println("<p>Welcome, " + loggedInUserEmail + "! You have successfully logged in.</p>");
        out.println("<p>Here you can manage your profile and access various features.</p>");
        out.println("<a href='logoutServlet'>Logout</a>");
        out.println("</body></html>");
    }
}
