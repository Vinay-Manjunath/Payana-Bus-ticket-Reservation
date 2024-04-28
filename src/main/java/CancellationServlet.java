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

@WebServlet("/CancellationServlet")
public class CancellationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if the user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUserPhoneNumber") == null) {
            // User is not logged in, redirect to the login page
            response.sendRedirect("login.html");
            return;
        }

        String phone_number = (String) session.getAttribute("loggedInUserPhoneNumber");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Establish database connection
        	Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://mysqldb-container:3306/bus"; 
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(url, username, password);

            // Query to retrieve bus details
            String query = "SELECT b.booking_id,b.route_id,b.seat_number,t.source,t.destination,t.date,t.departure_time,t.arrival_time,t.fare,d.type FROM booking b,trip t,bus_details d where t.bid=d.bid AND t.route_id=b.route_id AND b.phone_number=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, phone_number);
            ResultSet resultSet = preparedStatement.executeQuery();

            out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
            out.println("<body><div  style='position: fixed;position:absolute;top:0px'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'></div>");
            out.println("<div style='margin:100px;text-align:center;'><h2>Bookings</h2>");
            out.println("<script>");
            out.println("function redirectToIndex() {");
            out.println("   window.location.href = 'index.jsp';");
            out.println("}");
            out.println("</script>");

            // Display bus details and cancellation form
            while (resultSet.next()) {
                out.println("<div class='container' style='color:white;text-align:left'>");
                out.println("<form action='CancellationServlet' method='post'>");
                out.println("<label style='float:left;'>");
                out.println("<input type='radio' name='selectedBookingId' value='" + resultSet.getString("b.booking_id") + "' >");
                out.println("<p>Bus Type: " + resultSet.getString("d.type") + "</p>");
                out.println("<p>Source: " + resultSet.getString("t.source") + "</p>");
                out.println("<p>Destination: " + resultSet.getString("t.destination") + "</p>");
                out.println("<p>Date: " + resultSet.getString("t.date") + "</p>");
                out.println("<p>Departure Time: " + resultSet.getString("t.departure_time") + "</p>");
                out.println("<p>Arrival Time: " + resultSet.getString("t.arrival_time") + "</p>");
                out.println("<p>Booking ID: " + resultSet.getString("b.booking_id") + "</p>");
                out.println("<p>Route ID: " + resultSet.getString("b.route_id") + "</p>");
                out.println("<p>Bus Fare: " + resultSet.getInt("t.fare") + "</p>");
                out.println("<p>Seat Number: " + resultSet.getInt("b.seat_number") + "</p>");
                out.println("</label>");
                
                // Add cancellation form with radio button
                out.println("<input type='submit' value='Cancel Booking' class='login-button'>");
                out.println("</form>");

                out.println("</div>");
            }

            out.println("</div></body></html>");

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            displayError(response, "Error occurred during cancellation. Please try again.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String selectedBookingId = request.getParameter("selectedBookingId");

        if (selectedBookingId != null && !selectedBookingId.isEmpty()) {
            try {
                // Establish database connection
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/bus";
                String username = "root";
                String password = "";
                Connection connection = DriverManager.getConnection(url, username, password);

                // Delete the selected booking from the database
                String deleteQuery = "DELETE FROM booking WHERE booking_id=?";
                try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                    deleteStatement.setString(1, selectedBookingId);
                    deleteStatement.executeUpdate();
                    
                    connection.close();
                }
                	                    
                

                // Close resources
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                displayError(response, "Error occurred during cancellation. Please try again.");
            }
        }
        PrintWriter out = response.getWriter();

    	out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
    	out.println("<body><div  style='position: fixed;'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'>");
    	out.println("<div style='margin:150px 50px 0px;'><h2>Booking Cancelled Successfully.</h2></div>");
    	out.println("<button onclick='redirectToLogin()' class='login-button' style='margin:50px 50px;'>Booking Status</button>");
    	out.println("<script>");
    	out.println("function redirectToIndex() {");
    	out.println("   window.location.href = 'index.jsp';");
    	out.println("}");
    	out.println("function redirectToLogin() {");
    	out.println("   window.location.href = 'BookingDetails.html';");
    	out.println("}");
    	out.println("</script>");
    	out.println("</div></body></html>");

        // Redirect to the cancellation page to refresh the booking details
        //response.sendRedirect("CancellationServlet");
    }

    private void displayError(HttpServletResponse response, String errorMessage) throws IOException {
        // Display an error message
        PrintWriter out = response.getWriter();
        out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
        out.println("<body><div style='margin:100px;text-align:center;'>");
        out.println("<h2>Error: " + errorMessage + "</h2>");
        out.println("</div></body></html>");
    }
}
