// BookSeatServlet.java

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/BookSeatServlet")
public class BookSeatServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve information from the hidden fields and user input
        String selectedBusId = request.getParameter("selectedBusId");
        String selectedBusDate = request.getParameter("selectedBusDate");
        String selectedSeat = request.getParameter("selectedSeat");

        // Validate and perform necessary actions based on selected seat

        // Generate booking ID and save to the database

        // Display confirmation message
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<div class='container'>");
        out.println("<h2>Booking Confirmation</h2>");
        out.println("<p>Your booking has been confirmed.</p>");
        out.println("<p>Bus ID: " + selectedBusId + "</p>");
        out.println("<p>Selected Seat: " + selectedSeat + "</p>");
        out.println("</div></body></html>");
    }
}
