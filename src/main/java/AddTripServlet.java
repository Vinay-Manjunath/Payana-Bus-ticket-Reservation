import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddTripServlet")
public class AddTripServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bid = request.getParameter("bid");
        String route_id = request.getParameter("rid");
        String source = request.getParameter("source");
        String destination = request.getParameter("destination");
        String date = request.getParameter("date");
        String dtime = request.getParameter("dtime");
        String atime = request.getParameter("atime");
        String fare = request.getParameter("fare");

        
        try {
            // Update the JDBC connection details
        	Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://mysqldb-container:3306/bus"; 
            String username = "root";
            String dbPassword = "root";

            Connection connection = DriverManager.getConnection(url, username, dbPassword);

            String insertQuery = "INSERT INTO trip VALUES (?,?,?,?,?,?,?,?)";
            
            	PreparedStatement preparedStatement = connection.prepareStatement(insertQuery); 
                preparedStatement.setString(1, route_id);
                preparedStatement.setString(2, bid);
                preparedStatement.setString(3, source);
                preparedStatement.setString(4, destination);
                preparedStatement.setString(5, date);
                preparedStatement.setString(6, dtime);
                preparedStatement.setString(7, atime);
                preparedStatement.setString(8, fare);
                
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) 
                {
                	response.setContentType("text/html");
                	PrintWriter out = response.getWriter();
                	out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
                	out.println("<body><div  style='position: fixed;'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'>");
                	out.println("<div style='margin:150px 50px 0px;'><h2>Trip Added Successfully.</h2></div>");
                	out.println("<button onclick='redirectToLogin()' class='login-button' style='margin:50px 50px;'>Trip Details</button>");
                	out.println("<script>");
                	out.println("function redirectToIndex() {");
                	out.println("   window.location.href = 'index.jsp';");
                	out.println("}");
                	out.println("function redirectToLogin() {");
                	out.println("   window.location.href = 'TripDetails.html';");
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
            	 response.setContentType("text/html");
             	PrintWriter out = response.getWriter();
             	out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
             	out.println("<body><div  style='position: fixed;'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'>");
             	out.println("<div style='margin:150px 50px 0px;'><h2>"+ e.getMessage()+"</h2></div>");
             	out.println("<script>");
             	out.println("function redirectToIndex() {");
             	out.println("   window.location.href = 'index.jsp';");
             	out.println("}");
             	
                e.printStackTrace();
            }

         catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
