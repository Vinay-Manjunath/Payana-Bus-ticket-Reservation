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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate the username and password against the database
        String phoneNumber = validateUser(username, password);

        if (phoneNumber != null) {
            // Set phone number in the session with a specific name
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUserPhoneNumber", phoneNumber);
            response.setContentType("text/html");
        	PrintWriter out = response.getWriter();
        	out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
        	out.println("<body><div  style='position: fixed;'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'>");
        	out.println("<div style='margin:150px 50px'><h2>Successfully Logged In.</h2>");
        	out.println("<button onclick='redirectToBooking()' class='login-button'>Bookings</button>");
        	out.println("<button onclick='redirectToStatus()' class='login-button'>Booking Status</button>");
        	out.println("<button onclick='redirectToCancel()' class='login-button'>Cancel Booking</button>");
        	out.println("<script>");
        	out.println("function redirectToIndex() {");
        	out.println("   window.location.href = 'index.jsp';");
        	out.println("}");
        	out.println("function redirectToBooking() {");
        	out.println("   window.location.href = 'booking.html';");
        	out.println("}");
        	out.println("function redirectToCancel() {");
        	out.println("   window.location.href = 'CancellationServlet';");
        	out.println("}");
          	out.println("function redirectToStatus() {");
        	out.println("   window.location.href = 'BookingDetails.html';");
        	out.println("}");
        	out.println("</script>");
        	out.println("</div></body></html>");
            
        } else {
            // Display login failure message
        	response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
            out.println("<body><div  style='position: fixed;'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'>");
            out.println("<div style='margin:150px 50px;'><h2>Invalid Username or password.</h2>");
            out.println("<script>");
            out.println("function redirectToIndex() {");
            out.println("   window.location.href = 'index.jsp';");
            out.println("}");
            out.println("</script>");
            out.println("</div></body></html>");}
    }

    private String validateUser(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://mysqldb-container:3306/bus"; 
            String dbUsername = "root";
            String dbPassword = "root";

            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Perform user validation against the database
            String validationQuery = "SELECT phone_number FROM user WHERE phone_number = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(validationQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Return the phone number
                    return resultSet.getString("phone_number");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
