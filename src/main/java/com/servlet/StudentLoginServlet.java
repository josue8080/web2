package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/studentLoginServlet")
public class StudentLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC URL and credentials
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/ronald_student";
    private static final String JDBC_USER = "postgres"; // PostgreSQL username
    private static final String JDBC_PASSWORD = "ronald"; // PostgreSQL password

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Load the PostgreSQL JDBC driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            out.println("<html><body>");
            out.println("<h2>Error: PostgreSQL JDBC Driver not found.</h2>");
            out.println("</body></html>");
            e.printStackTrace();
            return; // Stop further execution if driver not found
        }

        // JDBC code to validate login credentials
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM students WHERE email = ? AND password = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.setString(2, password);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        // Login successful
                        HttpSession session = request.getSession();
                        session.setAttribute("loggedInUserEmail", email); // Store email in session
                        response.sendRedirect("welcome.html");
                    } else {
                        // Login failed
                        out.println("<html><body>");
                        out.println("<h2>Login failed. Please check your email and password.</h2>");
                        out.println("</body></html>");
                    }
                }
            }
        } catch (SQLException e) {
            out.println("<html><body>");
            out.println("<h2>Error: Login failed. Please try again later.</h2>");
            out.println("</body></html>");
            e.printStackTrace();
        }
    }
}
