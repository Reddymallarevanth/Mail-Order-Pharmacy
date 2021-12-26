<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
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
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,700,700i|Poppins:300,400,500,700"
	rel="stylesheet">

<!-- Vendor CSS Files -->
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet">
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
			</nav>
			<!-- #nav-menu-container -->
		</div>
	</header>
	<!-- End Header -->
	<section id="services">
		<div class="container">
			<br> <br>
			<h3>Prescription details</h3>
			<h3>${msg}</h3>
			<form name="prescriptionform" model="prescriptionDetails"
				method="post"  onsubmit="return validate()" action="subscribe">

				<div class="form-group">
					<label for="insuranceProvider">Insurance Provider</label> <input
						type="text" class="form-control" name="insuranceProvider"
						id="insuranceProvider" placeholder=""> <span
						class="text-danger" id="insuranceProviderSpan"></span>
				</div>
				<div class="form-group">
					<label for="policyNumber">Policy Number</label> <input type="text"
						class="form-control" name="policyNumber" id="policyNumber"
						placeholder=""> <span class="text-danger"
						id="policyNumberSpan"></span>
				</div>
				<div class="form-group">
					<label for="doctorName">Doctor Name</label> <input type="text"
						class="form-control" name="doctorName" id="doctorName"
						placeholder="Enter Doctor name"> <span class="text-danger"
						id="doctorNameSpan"></span>
				</div>
				<div class="form-group">
					<label for="prescriptionDate">Prescription Date</label> <input
						type="date" class="form-control" name="prescriptionDate"
						id="prescriptionDate" placeholder="Date"> <span
						class="text-danger" id="prescriptionDateSpan"></span>
				</div>
				<div class="form-group">
					<label for="Location">Your Location</label> <input type="text"
						class="form-control" name="memberLocation" id="memberLocation"
						placeholder="Enter your location"> <span class="text-danger"
						id="memberLocationSpan"></span>
				</div>
				<div class="form-group">
					<label for="drugName">Medicine Name</label> <input type="text"
						class="form-control" name="drugName" id="drugName" placeholder="">
					<span class="text-danger" id="drugNameSpan"></span>
				</div>
				<div class="form-group">
					<label for="dosageDefinition">Dosage Definitions</label> <input
						type="text" class="form-control" name="dosageDefinition"
						id="dosageDefinition" placeholder=""> <span
						class="text-danger" id="dosageDefinitionSpan"></span>
				</div>
				<div class="form-group">
					<label for="quantity">Quantity</label> <input type="number"
						class="form-control" name="quantity" id="quantity"
						placeholder=""> <span class="text-danger"
						id="quantitySpan"></span>
				</div>
				<div class="form-group">
					<label for="courseDuration">Course Duration</label> <input
						type="number" class="form-control" name="courseDuration"
						id="courseDuration" placeholder=""> <span
						class="text-danger" id="courseDurationSpan"></span>
				</div>
				<button type="submit" class="btn btn-success">Submit</button>
			</form>
			
		</div>
	</section>
	<!-- ======= Footer ======= -->
	<footer id="footer">
		<div class="footer-top">
			<div class="container"></div>
		</div>

		<div class="container">
			<div class="credits">Thank you! We are here for your needs.</div>
		</div>
	</footer>
	<!-- End Footer -->
	<script>
		document.getElementById('prescriptionDate').max = new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString().split("T")[0];
	</script>

	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="vendor/aos/aos.js"></script>

	<!-- Template Main JS File -->
	<script src="js/main.js"></script>
	<script src="js/Prescription.js"></script>
</body>
</html>