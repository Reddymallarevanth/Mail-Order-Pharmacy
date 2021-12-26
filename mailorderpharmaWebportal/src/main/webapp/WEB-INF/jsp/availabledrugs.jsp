<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<!DOCTYPE html>
<html>

	<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>Mail Order Pharmacy</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="images/favicon.png" rel="icon">
  <link href="images/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,700,700i|Poppins:300,400,500,700" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet">
  <link href="vendor/aos/aos.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link href="style/style.css" rel="stylesheet">
</head>
<body>
  <!-- ======= Header ======= -->
  <header id="header">
    <div class="container">

      <div id="logo" class="pull-left">
				<h3>
					<a href="/webportal/home">Mail Order Pharmacy</a>
				</h3>
			</div>

      <nav id="nav-menu-container">
        <ul class="nav-menu">
          <li class="menu-active"><a href="home">Home</a></li>
					<li><a href="home#subscribe">Subscribe</a></li>
					<li><a href="home#services">Supported drugs</a></li>
					<li><a href="home#view-subscribed-drugs">Subscribed medicines</a></li>
					<li><a href="home#due-for-refill">Due for refill</a></li>
					<li><a href="home#ad-hoc-request">Request now</a></li>
					<li><a href="logout">Logout</a></li>
        </ul>
      </nav><!-- #nav-menu-container -->
    </div>
  </header><!-- End Header -->
  <br>
  <section id="services">
   <div class="container">
   <table class="table table-striped">
   <thead class="thead-dark">
   <tr>
   	<th>Drug Name</th>
   	<th>Manufacturer</th>
   	<th>ManufactureDate</th>
   	<th>ExpiryDate</th>
   	<th>LocationWise Quantities</th>
   </tr>
   </thead>
    <c:forEach items="${drugList}" var="itr">
    <tr>
	     <td>${itr.drugName}<br></td>
	     <td>${itr.manufacturer}</td>
	     <td>${itr.manufactureDate}</td>
	     <td>${itr.expiryDate}</td>
	     <td><c:forEach items="${itr.druglocationQuantities}" var="itr1">
	     ${itr1.location} : 
	     ${itr1.quantity}<br>
     
     </c:forEach></td>
     </tr>
     <br><br>
   </c:forEach>
   </table>
   </div>
  </section>
    <!-- ======= Footer ======= -->
  <footer id="footer" class="mt-auto fixed-bottom">
    <div class="footer-top">
      <div class="container">

      </div>
    </div>

    <div class="container">
      <div class="credits">
        Thank you! We are here for your needs.
      </div>
    </div>
  </footer><!-- End Footer -->
</body>
</html>