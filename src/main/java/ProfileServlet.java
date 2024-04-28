import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Get the session object
        HttpSession session = request.getSession();

        // Check if the session variable (phone_number) exists
        if (session.getAttribute("loggedInUserPhoneNumber") != null) {
            // Retrieve phone_number from the session
            String phone_number = (String) session.getAttribute("loggedInUserPhoneNumber");

            // JDBC variables
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Set up the connection
                String jdbcURL = "jdbc:mysql://mysqldb-container:3306/bus";
                String dbUser = "root";
                String dbPassword = "root";
                conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

                // Prepare and execute the SQL query
                String sql = "SELECT * FROM user WHERE phone_number = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, phone_number);
                rs = stmt.executeQuery();

                // Display user data
                PrintWriter out = response.getWriter();
                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<link rel='stylesheet' href='styles1.css'>");
                out.println("<title>User Profile</title>");
                out.println("</head>");
                out.println("<body>");

                out.println("<div style='text-align: center;'>");
                out.println("<div style='width: 1000px; margin: 120px auto 0;'>");

                out.println("<h1 style='color: #5E6379;'>User Profile</h1>");

                if (rs.next()) {
                    out.println("<div><img src='./images/icon.jpg' alt='user'></div>");
                    out.println("<p style='color: #9F933D;'><strong>Phone Number:</strong> " + rs.getString("phone_number") + "</p>");
                    out.println("<p style='color: #9F933D;'><strong>Firstname:</strong> " + rs.getString("first_name") + "</p>");
                    out.println("<p style='color: #9F933D;'><strong>Last Name:</strong> " + rs.getString("last_name") + "</p>");
                    out.println("<p style='color: #9F933D;'><strong>Email:</strong> " + rs.getString("email") + "</p>");
                    out.println("<p style='color: #9F933D;'><strong>Id:</strong> " + rs.getString("id") + "</p>");
                    out.println("<p style='color: #9F933D;'><strong>Age:</strong> " + rs.getString("age") + "</p>");
                    out.println("<button type='button' onclick='location.href=\"LogoutServlet\"' style='font-weight: 550; padding: 14px 26px; background: #E6D482; border-radius: 50px; border: 2px solid #fff; cursor: pointer; transition: all 0.3s ease; color: #5E6379; margin-top: 20px; font-size: 20px; transition: 0.5s;'>Logout</button>");
                    out.println("</div>");
                } else {
                    out.println("<p>User not found.</p>");
                }

                out.println("</div>");
                out.println("</body>");
                out.println("</html>");

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                // Close resources
                try {
                    if (rs != null)
                        rs.close();
                    if (stmt != null)
                        stmt.close();
                    if (conn != null)
                        conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // If session variable doesn't exist, redirect to login page
            response.sendRedirect("login.html");
        }
    }
}
