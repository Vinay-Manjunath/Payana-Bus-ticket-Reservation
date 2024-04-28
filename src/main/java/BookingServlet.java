import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if the user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUserPhoneNumber") == null) {
            // User is not logged in, redirect to the login page
            response.sendRedirect("login.html");
            return;
        }

        String date = request.getParameter("date");
        String source = request.getParameter("source");
        String destination = request.getParameter("destination");

        // Retrieve phone number from the session
        String phoneNumber = (String) session.getAttribute("loggedInUserPhoneNumber");
        
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://mysqldb-container:3306/bus"; 
            String dbUsername = "root";
            String dbPassword = "root";

            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            PrintWriter out = response.getWriter();
            // Search for available buses
            String searchQuery = "SELECT t.route_id as rid,t.bid as bus_id, d.type as bus_type, t.fare as bus_fare, t.departure_time as dtime, t.arrival_time as atime,d.capacity as cap FROM trip t, bus_details d WHERE t.bid=d.bid AND t.source = ? AND t.destination = ? AND t.date = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(searchQuery) ;
                preparedStatement.setString(1, source);
                preparedStatement.setString(2, destination);
                preparedStatement.setString(3, date);

                ResultSet resultSet = preparedStatement.executeQuery();

                response.setContentType("text/html");
                
                out.println("<html><head><link rel='stylesheet' href='styles.css'></head><body>");
                out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
            	out.println("<body><div  style='position: fixed;top:0px'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'></div>");
            	out.println("<div style='margin-top:50px;text-align:center'><h2>Available Buses.</h2></div>");
            	out.println("<script>");
            	out.println("function redirectToIndex() {");
            	out.println("   window.location.href = 'index.jsp';");
            	out.println("}");
            	out.println("</script>");
            	
                while (resultSet.next()) {
                	String busId = resultSet.getString("bus_id");
                    String rid = resultSet.getString("rid");
                    String busType = resultSet.getString("bus_type");
                    int busFare = resultSet.getInt("bus_fare");
                    String dtime = resultSet.getString("dtime");
                    String atime = resultSet.getString("atime");
                    int capacity = resultSet.getInt("cap");
                    
                    // Get booked seat numbers for the route_id
                    Set<Integer> bookedSeatNumbers = getBookedSeatNumbers(rid, connection);
                    
                    // Calculate available seat numbers
                    List<Integer> availableSeatNumbers = calculateAvailableSeatNumbers(capacity, bookedSeatNumbers);

                    if (!availableSeatNumbers.isEmpty()) {
                    	out.println("<form action='BookingServlet' method='post'><div class='container' style='color:white;'><input type='radio' name='select' value='" + rid + "'>");
                        out.println("<p>Bus Type: " + busType + "</p>");
                        out.println("<p>Departure Time: " + dtime + "</p>");
                        out.println("<p>Arrival Time: " + atime + "</p>");
                        out.println("<p>Route ID: " + rid + "</p>");
                        out.println("<p>Bus Fare: " + busFare + "</p>");
                        out.println("<p>Available Seat Numbers: " + availableSeatNumbers + "</p></input>");
                        
                        out.println("<label for='seat_number'>Select Seat Number:</label>");
                        out.println("<select name='seat_number' style='background-color: #5E6379; color: #E6D482; font-weight: bold;border-radius:5px'>");

                        for (Integer seatNumber : availableSeatNumbers) {
                            out.println("<option value='" + seatNumber + "' style='background-color: #E6D482; color: #5E6379; font-weight: bold;'>" + seatNumber + "</option>");
                        }

                        out.println("</select>");
                        out.println("<br><input type='submit' value='Confirm Booking' class='login-button' style='margin-top:30px'></div>");
                    }
                }
            
  
                String submittedSeatNumber = request.getParameter("seat_number");
                String submittedRouteId = request.getParameter("select");
                int seat_no=Integer.parseInt(submittedSeatNumber);
                
                String searchQuery1 = "SELECT d.capacity as cap FROM trip t, bus_details d WHERE t.bid=d.bid AND t.route_id=?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(searchQuery1);
                preparedStatement1.setString(1, submittedRouteId);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                 
                int capacity=0;
                while (resultSet1.next()) {
                    capacity = resultSet1.getInt("cap");
                }
                
                if(seat_no> capacity){
                	out.println("<div style='margin:150px 50px;'><h2>Error: Entered seat is not available.</h2></div>");
                	return;
                }

                
                if (submittedRouteId != null && !submittedRouteId.isEmpty()) {
                    // Check if the submitted route_id is available in the list
                    boolean routeIdAvailable = isRouteIdAvailable(submittedRouteId, connection);
                    if (routeIdAvailable) {
                        // Add booking details to the bookings table
                        String bookingId = generateBookingId();
                        addBookingDetails(phoneNumber, submittedRouteId, bookingId,seat_no, connection);

                        // Show success message with booking ID
                        out.println("<div style='margin:150px 50px;'><h2>Booking successful! Your Booking ID is: " + bookingId + "</h2></div>");
                    } else {
                        // Show error message if route_id is not available
                        out.println("<div style='margin:150px 50px;'><h2>Error: Entered route ID is not available.</h2></div>");
                    }
                }
                
             out.println("</div></body></html>");
            
 
            }
            catch (ClassNotFoundException | SQLException e) {
            	response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><head><title>Error Page</title></head><body>");
                out.println("<div style='margin:150px 50px;'><h2>Error: " + e.getMessage() + "</h2></div>");
                e.printStackTrace(out); // Print the stack trace to the PrintWriter
                out.println("</body></html>");
            } 
    }   


    private boolean isRouteIdAvailable(String routeId, Connection connection) throws SQLException {
        String checkRouteIdQuery = "SELECT COUNT(*) as count FROM trip WHERE route_id = ?";
        try (PreparedStatement checkRouteIdStatement = connection.prepareStatement(checkRouteIdQuery)) {
            checkRouteIdStatement.setString(1, routeId);
            ResultSet routeIdResultSet = checkRouteIdStatement.executeQuery();
            return routeIdResultSet.next() && routeIdResultSet.getInt("count") > 0;
        }
    }

    private void addBookingDetails(String phoneNumber, String routeId, String bookingId,int seat_number, Connection connection) throws SQLException {
        String insertBookingQuery = "INSERT INTO booking (phone_number, route_id, booking_id,seat_number) VALUES (?, ?, ?,?)";
        try (PreparedStatement insertBookingStatement = connection.prepareStatement(insertBookingQuery)) {
            insertBookingStatement.setString(1, phoneNumber);
            insertBookingStatement.setString(2, routeId);
            insertBookingStatement.setString(3, bookingId);
            insertBookingStatement.setInt(4, seat_number);
            insertBookingStatement.executeUpdate();
        }
    }

    private String generateBookingId() {
        // Generate a random 5-digit booking ID
        Random random = new Random();
        int bookingId = 10000 + random.nextInt(90000);
        return String.valueOf(bookingId);
    }


//Method to get booked seat numbers for a given route_id
private Set<Integer> getBookedSeatNumbers(String routeId, Connection connection) throws SQLException {
 Set<Integer> bookedSeatNumbers = new HashSet<>();
 String bookedSeatsQuery = "SELECT seat_number FROM booking WHERE route_id = ?";
 try (PreparedStatement bookedSeatsStatement = connection.prepareStatement(bookedSeatsQuery)) {
     bookedSeatsStatement.setString(1, routeId);
     ResultSet bookedSeatsResultSet = bookedSeatsStatement.executeQuery();
     while (bookedSeatsResultSet.next()) {
         bookedSeatNumbers.add(bookedSeatsResultSet.getInt("seat_number"));
     }
 }
 return bookedSeatNumbers;
}

//Method to calculate available seat numbers based on capacity and booked seat numbers
private List<Integer> calculateAvailableSeatNumbers(int capacity, Set<Integer> bookedSeatNumbers) {
 List<Integer> availableSeatNumbers = new ArrayList<>();
 for (int i = 1; i <= capacity; i++) {
     if (!bookedSeatNumbers.contains(i)) {
         availableSeatNumbers.add(i);
     }
 }
 return availableSeatNumbers;
}
}
