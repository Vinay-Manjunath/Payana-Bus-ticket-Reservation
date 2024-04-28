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

@WebServlet("/ReservationOfBusServlet")
public class ReservationOfBusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    	String rid = request.getParameter("route_id");
    	
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
            String query = "SELECT t.bid,t.source,t.destination,t.date,t.departure_time,t.arrival_time,t.fare,b.type,b.depot FROM trip t,bus_details b where t.bid=b.bid AND t.route_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,rid );
            ResultSet resultSet = preparedStatement.executeQuery();

            out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
        	out.println("<body><div  style='position: fixed;position:absolute;top:0px'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'></div>");
        	out.println("<div style='margin-top:70px;text-align:center;'><h2>Booking Details</h2></div>");
        	out.println("<script>");
        	out.println("function redirectToIndex() {");
        	out.println("   window.location.href = 'index.jsp';");
        	out.println("}");
        	out.println("</script>");
        	
        	            
            String query1 = "SELECT phone_number, booking_id, seat_number FROM booking WHERE route_id=?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            preparedStatement1.setString(1, rid);
            ResultSet resultSet1 = preparedStatement1.executeQuery();

            out.println("<table class='container' style='color:white;'><tr><th>Phone Number	</th><th>Booking ID	</th><th>Seat Number</th></tr>");
            while (resultSet1.next()) {
            	out.println("<tr>");
                out.println("<td>" + resultSet1.getString("phone_number") + "</td>");
                out.println("<td>" + resultSet1.getString("booking_id") + "</td>");
                out.println("<td>" + resultSet1.getInt("seat_number") + "</td>");
                out.println("</tr>");
            }
                       
            out.println("</table>");
            

        
        	
        	while (resultSet.next()) {
                out.println("<div class='container' style='color:white'>");
                out.println("<p>Bus ID: " + resultSet.getString("t.bid") + "</p>");
                out.println("<p>Source: " + resultSet.getString("t.source") + "</p>");
                out.println("<p>Destination: " + resultSet.getString("t.destination") + "</p>");
                out.println("<p>Date: " + resultSet.getString("t.date") + "</p>");
                out.println("<p>Departure Time: " + resultSet.getString("t.departure_time") + "</p>");
                out.println("<p>Arrival Time: " + resultSet.getString("t.arrival_time") + "</p>");
                out.println("<p>Bus Fare: " + resultSet.getInt("t.fare") + "</p>");
                out.println("<p>Bus Type: " + resultSet.getInt("b.type") + "</p>");
                out.println("<p>Bus Fare: " + resultSet.getInt("b.depot") + "</p></div>");
            }

        	out.println("</body></html>");

            // Close resources
            resultSet1.close();
            preparedStatement1.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) { 
            e.printStackTrace();
        }
    }
}

