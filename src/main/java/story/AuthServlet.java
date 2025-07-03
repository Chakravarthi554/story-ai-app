package story;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/AuthServlet")
public class AuthServlet extends HttpServlet {
    // IMPORTANT: In a real application, database credentials should be
    // externalized,
    // not hardcoded in the servlet.
    final String JDBC_URL = "jdbc:mysql://localhost:3306/storyai";
    final String DB_USER = "root";
    final String DB_PASS = "1234";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false); // Get existing session, don't create new
            if (session != null) {
                session.invalidate(); // Invalidate the session
            }
            response.sendRedirect("login.jsp"); // Redirect to login page
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection to the database
            Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            if ("register".equals(action)) {
                // Check if username already exists
                PreparedStatement check = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
                check.setString(1, username);
                ResultSet rs = check.executeQuery();

                if (rs.next()) {
                    request.setAttribute("error", "Username already exists. Please choose a different one.");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                } else {
                    // Insert new user into the database
                    PreparedStatement insert = conn
                            .prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
                    insert.setString(1, username);
                    insert.setString(2, password);
                    insert.executeUpdate();
                    response.sendRedirect("login.jsp"); // Redirect to login page after successful registration
                }

            } else if ("login".equals(action)) {
                // Validate username and password
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    // User authenticated, create session
                    HttpSession session = request.getSession();
                    session.setAttribute("user", username);
                    response.sendRedirect("homepage.jsp"); // Redirect to homepage
                } else {
                    request.setAttribute("error", "Invalid username or password.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }

            // Close the connection
            conn.close();

        } catch (Exception e) {
            // Handle database or other exceptions
            e.printStackTrace(); // Log the error for debugging
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}