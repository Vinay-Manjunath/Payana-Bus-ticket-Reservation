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

@WebServlet("/TripDetailsServlet")
public class TripDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            String query = "SELECT * FROM trip";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // HTML response
            out.println("<html><head><title>Trip Details</title>");
            out.println("</head><body style='background-color: #E6D482'><div><h2 style='color: #5E6379;'>Trip Details</h2><table style='color:white;font-size:20px'>");
            out.println("<tr><th>Route ID</th><th>Bus ID</th><th>Source</th><th>Destination</th><th>Date</th><th>Departure Time</th><th>Arrival Time</th><th>Fare</th></tr>");

            // Display bus details
            while (resultSet.next()) {
                out.println("<tr>");
                out.println("<td>" + resultSet.getString("route_id") + "</td>");
                out.println("<td>" + resultSet.getString("bid") + "</td>");
                out.println("<td>" + resultSet.getString("source") + "</td>");
                out.println("<td>" + resultSet.getString("destination") + "</td>");
                out.println("<td>" + resultSet.getString("date") + "</td>");
                out.println("<td>" + resultSet.getString("departure_time") + "</td>");
                out.println("<td>" + resultSet.getString("arrival_time") + "</td>");
                out.println("<td>" + resultSet.getInt("fare") + "</td>");
                out.println("</tr>");
            }

            out.println("</table></div></body></html>");

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
