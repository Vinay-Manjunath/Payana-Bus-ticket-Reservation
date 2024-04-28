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

@WebServlet("/EmpLoginServlet")
public class EmpLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("id");
        String password = request.getParameter("password");

        // Validate the username and password against the database
        String desig = validateUser(username, password);

        if (desig != null) {
            // Set phone number in the session with a specific name
            response.setContentType("text/html");
        	PrintWriter out = response.getWriter();
        	out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
        	out.println("<body><div  style='position: fixed;'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'>");
        	out.println("<div style='margin:150px 50px'><h2>Successfully Logged In.</h2>");
        	out.println("<button onclick='redirectToBooking()' class='login-button'>Bus Details</button>");
        	out.println("<button onclick='redirectToStatus()' class='login-button'>Trip Details</button>");
        	out.println("<button onclick='redirectToReservation()' class='login-button'>Bus Reservation Details</button>");
        	if ("Manager".equals(desig)) {
                out.println("<button onclick='redirectToAddBus()' class='login-button'>Add Bus</button>");
                out.println("<button onclick='redirectToAddEmp()' class='login-button'>Add Employee</button>");
                out.println("<button onclick='redirectToAddTrip()' class='login-button'>Add Trip</button>");
            }
        	out.println("<script>");
        	out.println("function redirectToAddBus() {");
            out.println("   window.location.href = 'addbus.html';"); // Change to your actual manage bus page
            out.println("}");
            out.println("function redirectToAddEmp() {");
            out.println("   window.location.href = 'employeeRegistation.html';"); // Change to your actual manage bus page
            out.println("}");
            out.println("function redirectToAddTrip() {");
            out.println("   window.location.href = 'addtrip.html';"); // Change to your actual manage bus page
            out.println("}");
        	out.println("function redirectToIndex() {");
        	out.println("   window.location.href = 'index.jsp';");
        	out.println("}");
        	out.println("function redirectToReservation() {");
        	out.println("   window.location.href = 'ReservationDetails.html';");
        	out.println("}");
           	out.println("function redirectToBooking() {");
        	out.println("   window.location.href = 'BusDetails.html';");
        	out.println("}");
        	out.println("function redirectToStatus() {");
        	out.println("   window.location.href = 'TripDetails.html';");
        	out.println("}");
        	out.println("</script>");
        	out.println("</div></body></html>");
            
        } else {
            // Display login failure message
        	response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
            out.println("<body><div  style='position: fixed;'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'>");
            out.println("<div style='margin:150px 50px;'><h2>Invalid UserID or password.</h2>");
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
            String validationQuery = "SELECT designation FROM employee WHERE id = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(validationQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Return the phone number
                    return resultSet.getString("designation");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
