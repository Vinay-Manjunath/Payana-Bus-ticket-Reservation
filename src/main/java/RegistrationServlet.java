import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String idType = request.getParameter("idType"); // New parameter for ID type
        String id = request.getParameter("id");
        int age = Integer.parseInt(request.getParameter("age"));
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            // Passwords do not match, display an error message
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
            out.println("<body><div  style='position: fixed;'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'>");
            out.println("<div style='margin:150px 50px;'><h2>Passwords do not match</h2>");
            out.println("<script>");
            out.println("function redirectToIndex() {");
            out.println("   window.location.href = 'index.jsp';");
            out.println("}");
            out.println("</script>");
            out.println("</div></body></html>");
            return;
        }

        try {
            // Update the JDBC connection details
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://mysqldb-container:3306/bus";
            String username = "root";
            String dbPassword = "root";

            Connection connection = DriverManager.getConnection(url, username, dbPassword);

            String insertQuery = "INSERT INTO user (first_name, last_name, phone_number, email, id_type, id, age, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, phoneNumber);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, idType);
                preparedStatement.setString(6, id);
                preparedStatement.setInt(7, age);
                preparedStatement.setString(8, password);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Registration successful, display success message
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
                    out.println("<body><div  style='position: fixed;'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'>");
                    out.println("<div style='margin:150px 50px 0px;'><h2>Registered Successfully.</h2></div>");
                    out.println("<button onclick='redirectToLogin()' class='login-button' style='margin:50px 50px;'>Login Now</button>");
                    out.println("<script>");
                    out.println("function redirectToIndex() {");
                    out.println("   window.location.href = 'index.jsp';");
                    out.println("}");
                    out.println("function redirectToLogin() {");
                    out.println("   window.location.href = 'login.html';");
                    out.println("}");
                    out.println("</script>");
                    out.println("</div></body></html>");
                } else {
                    // Registration failed, display error message
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
                    out.println("<body><div  style='position: fixed;'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'>");
                    out.println("<div style='margin:150px 50px;'><h2>Registration Failed</h2></div>");
                    out.println("<script>");
                    out.println("function redirectToIndex() {");
                    out.println("   window.location.href = 'index.jsp';");
                    out.println("}");
                    out.println("</script>");
                    out.println("</div></body></html>");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
