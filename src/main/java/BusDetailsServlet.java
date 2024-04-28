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

@WebServlet("/BusDetailsServlet")
public class BusDetailsServlet extends HttpServlet {
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
            String query = "SELECT * FROM bus_details";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // HTML response
            out.println("<html><head><title>Bus Details</title>");
            out.println("</head><body style='background-color: #E6D482'><div><h2 style='color: #5E6379;'>Bus Details</h2><table style='color:white;font-size:20px'>");
            out.println("<tr><th>Bus ID	</th><th>Type	</th><th>Depot	</th><th>Capacity	</th></tr>");

            // Display bus details
            while (resultSet.next()) {
                out.println("<tr>");
                out.println("<td>" + resultSet.getString("bid") + "</td>");
                out.println("<td>" + resultSet.getString("type") + "</td>");
                out.println("<td>" + resultSet.getInt("Depot") + "</td>");
                out.println("<td>" + resultSet.getInt("capacity") + "</td>");
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
