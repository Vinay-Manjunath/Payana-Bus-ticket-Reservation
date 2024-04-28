<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Payana</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            color: #E6D482;
            margin: 0;
            background-image: url(./images/background.jpg);
            background-size: cover;
            background-position: center center;
            background-repeat: no-repeat;
            background-attachment: fixed;
        }

        img {
            float: left;
            width: 160px;
            display: block;
        }

        marquee {
            color: #5E6379;
            animation: marquee 30s linear infinite;
            height: 20px;
            margin: 20px 0px;
        }

        #header-bar {
            background-color: #5E6379;
            display: block;
            font-size: 20px;
            float: left;
            width: 100%;
            position: fixed;
            z-index: 2;
        }

        .header-items {
            float: left;
            margin: 20px;
            padding: 20px;
            color: #E6D482;
        }
        
        .dropdown-menu{
        	background-color: #5E6379;
        }
        
        .dropdown-item{
        	color: #E6D482;
        }
        
        a {
			text-decoration: none
		}
		
		.btns {
            font-weight: 550;
            padding: 14px 26px;
            background: #E6D482;
            border-radius: 50px;
            border: 2px solid #fff;
            cursor: pointer;
            transition: all 0.3s ease;
            color: #5E6379;
            margin-left: auto;
            display: flex;
            align-items: center;
            margin: 0;
            font-size: 20px;
            transition: .5s;
        }
        .btn{
            cursor: pointer;
        }
    
        .btns:hover {
            background-color: #fff;
            color: #333;
            cursor: pointer;   
        }
        
    </style>
</head>

<body>
    <div>
        <marquee scrollamount="10" direction="left">Premium buses from Bengaluru to hassan operational from 20th
            November Bookings open for buses from Mysuru to Chennai via Bengaluru Premium buses from Bengaluru to hassan
            operational from 20th November Bookings open for buses from Mysuru to Chennai via Bengaluru</marquee>
        <br />
    </div>
    <div id="header-bar">
        <img src="./images/logo.jpg" alt="payana:travel with comfort" onclick="window.open('index.jsp','_self')">

        <div class="dropdown">
            <a href="#" class="header-items dropdown-toggle" data-bs-toggle="dropdown">Bookings</a>
            <ul class="dropdown-menu">
            	<li><a class="dropdown-item" href="booking.html">New Booking</a></li>
                <li><a class="dropdown-item" href="BookingDetails.html">Booking Status</a></li>
                <li><a class="dropdown-item" href="cancelation.html">Cancel Bookings</a></li>
            </ul>
        </div>
        
        <div class="dropdown">
            <a href="#" class="header-items dropdown-toggle" data-bs-toggle="dropdown">Details</a>
            <ul class="dropdown-menu">
            	<li><a class="dropdown-item" href="BusDetails.html">Bus Details</a></li>
                <li><a class="dropdown-item" href="TripDetails.html">Trip Details</a></li>
            </ul>
        </div>
        
        <div><a href="about.html" class=header-items>Contact Us</a></div>
		<div><a href="emplogin.html" class=header-items>Employee Login</a></div>
		

        <!-- Add similar dropdowns for other menu items as needed -->

        <% HttpSession session1 = request.getSession(); %>
        <% if (session1.getAttribute("loggedInUserPhoneNumber") == null) { %>
            <button type="button" onclick="location.href='registration.html'" class="btns" style="float:right; margin:20px">Registration</button>
            <button type="button" onclick="location.href='login.html'" class="btns" style="float:right; margin:20px">Login</button>
        <% } else { %>
            <a href="LogoutServlet" class="btn btn-light" style="float:right;margin:20px">Logout</a>
        <% } %>
    </div>

    <!-- Bootstrap JS and Popper.js (required for dropdown) -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>

</html>
