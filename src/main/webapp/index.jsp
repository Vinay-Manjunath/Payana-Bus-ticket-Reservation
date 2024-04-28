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
            height: 40px;
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
        
        .dropdown-toggle::after {
            display: none !important; 
        }
        
        .bg figure{animation:25s bg infinite;width:400%;position:relative;margin:20px 0px;left:0}
.bg{overflow:hidden}
.bg1{background-color:#5E6379;width:100%;color:#E6D482;margin:50px 0px 0px;height:50px}

.bg figure img{
float:left;
width:25%;
}

.bg figure img1{
float:left;
width:25%;
}


@keyframes bg{
0% {
left:0;
}
30%{
left:0;
}
35%{
left:-100%;
}
65%{
left:-100%;
}
75%{
left:-200%;
}
100%{
left:-200%
}
}
        

        
    </style>
</head>

<body>
    <div style="height:60px;">
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
                <li><a class="dropdown-item" href="CancellationServlet">Cancel Bookings</a></li>
            </ul>
        </div>
        
        <div class="dropdown">
            <a href="#" class="header-items dropdown-toggle" data-bs-toggle="dropdown">Details</a>
            <ul class="dropdown-menu">
            	<li><a class="dropdown-item" href="BusDetails.html">Bus Details</a></li>
                <li><a class="dropdown-item" href="TripDetails.html">Trip Details</a></li>
            </ul>
        </div>
        
        <div><a href="contact.html" class=header-items>Contact Us</a></div>
		<div><a href="emplogin.html" class=header-items>Employee Login</a></div>
		

       
        <% HttpSession session1 = request.getSession(); %>
        <% if (session1.getAttribute("loggedInUserPhoneNumber") == null) { %>
            <button type="button" onclick="location.href='registration.html'" class="btns" style="float:right; margin:20px">Registration</button>
            <button type="button" onclick="location.href='login.html'" class="btns" style="float:right; margin:20px">Login</button>
        <% } else { %>
            <div class="dropdown" style="float:right;">
            	<a href="#" class="header-items dropdown-toggle" data-bs-toggle="dropdown">
            	<img src="./images/icon.jpg" alt="payana:travel with comfort" style="height:30px;width:30px"></a>
            	<ul class="dropdown-menu">
            	<li><a class="dropdown-item" href="LogoutServlet">Logout</a></li>
                <li><a class="dropdown-item" href="ProfileServlet">Profile</a></li>
            </ul>
        </div>
        <% } %>
    </div>
    
        

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

	<div class="bg" style="width:600px;margin:60px 10px;float:right;position:relative;top:50px;right:50px">
<figure>
<img src="images/pic3.jpg" width="800px">
<img src="images/pic4.jpg" width="800px">
<img src="images/pic1.jpg" width="800px">
<img src="images/pic2.jpg" width="800px">
</figure>
</div>

<div>
<div style="position:relative;float:left;width:700px;padding:10px 10px;margin:0px 10px;color:#5E6379;top:150px;left:50px">
<h3>Welocme to Payana!</h3>
<p>Welcome to Payana – where every journey is more than a commute; it's an immersive experience. Nestled in the heart of Bengaluru, our story unfolds as a premier bus service company committed to redefining travel across South India. Payana, derived from Kannada, embodies the spirit of exploration, promising seamless connectivity and unparalleled comfort as you traverse the landscapes of this vibrant region. 
</p>

<p>Embark on a seamless odyssey with Payana as we bridge the gap between major South Indian cities. Hyderabad, Chennai, Cochin, Mysore, Mangaluru, Hubli – our extensive network ensures that your journey is not just a passage but a rich tapestry of memories woven through diverse destinations.
</p>

<button type="button" onclick="location.href='about.html'" class="btns" style="float:right; margin:20px">Read More</button>

</div>



</div>

<div class="bg1" style="float:left">

<h3 align="center">© Rights Reserved</h3>

</div>
	

</body>

</html>
