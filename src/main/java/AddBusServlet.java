import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddBusServlet")
public class AddBusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bid = request.getParameter("bid");
        String type = request.getParameter("type");
        String depot = request.getParameter("depot");
        String capacity = request.getParameter("capacity");

        try {
            // Update the JDBC connection details
        	Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://mysqldb-container:3306/bus"; 
            String username = "root";
            String dbPassword = "root";

            Connection connection = DriverManager.getConnection(url, username, dbPassword);

            String insertQuery = "INSERT INTO bus_details (bid, type, Depot,capacity) VALUES (?, ?, ?, ?)";
            
            	PreparedStatement preparedStatement = connection.prepareStatement(insertQuery); 
                preparedStatement.setString(1, bid);
                preparedStatement.setString(2, type);
                preparedStatement.setString(3, depot);
                preparedStatement.setString(4, capacity);
                
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) 
                {
                	response.setContentType("text/html");
                	PrintWriter out = response.getWriter();
                	out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
                	out.println("<body><div  style='position: fixed;'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'>");
                	out.println("<div style='margin:150px 50px 0px;'><h2>Bus Added Successfully.</h2></div>");
                	out.println("<button onclick='redirectToLogin()' class='login-button' style='margin:50px 50px;'>Bus Details</button>");
                	out.println("<script>");
                	out.println("function redirectToIndex() {");
                	out.println("   window.location.href = 'index.jsp';");
                	out.println("}");
                	out.println("function redirectToLogin() {");
                	out.println("   window.location.href = 'BusDetails.html';");
                	out.println("}");
                	out.println("</script>");
                	out.println("</div></body></html>");
                } else {
                	response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
                    out.println("<body><div  style='position: fixed;'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'>");
                    out.println("<div style='margin:150px 50px;'><h2>Error in Adding Bus</h2></div>");
                    out.println("<script>");
                    out.println("function redirectToIndex() {");
                    out.println("   window.location.href = 'index.jsp';");
                    out.println("}");
                    out.println("</script>");
                    out.println("</div></body></html>");
                }
        }
             catch (SQLException e) {
                e.printStackTrace();
            }

         catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
