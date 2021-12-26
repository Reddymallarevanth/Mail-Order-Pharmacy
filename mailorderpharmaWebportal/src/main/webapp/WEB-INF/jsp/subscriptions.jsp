<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

<!-- cards -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,700,700i|Poppins:300,400,500,700"
	rel="stylesheet">

<!-- Vendor CSS Files -->
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">

<!-- Template Main CSS File -->
<link href="style/style.css" rel="stylesheet">



<script
	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>


</head>
<body >

	<!-- ======= Header ======= -->
	 <header id="header">
		 <div class="container">

			<div id="logo" class="pull-left">
				<h3>
					<a href="home">Mail Order Pharmacy</a>
				</h3>
			</div>

			<nav id="nav-menu-container">
				<ul class="nav-menu">
					<li class="menu-active"><a href="home">Home</a></li>
					<li><a href="prescriptionform">Subscribe</a></li>
					<li><a href="supportedDrugs">Supported drugs</a></li>
					<li><a href="subscriptions">Subscribed medicines</a></li>
					<li><a href="home#due-for-refill">Due for refill</a></li>
					<li><a href="subscriptions">Request now</a></li>
					<li><a href="logout">Logout</a></li>
				</ul>
			</nav>

		</div>
	</header> 
	
	<section id="services">
		<div class="container">
			
			 <br> <br>
			<h1>Your Subscriptions</h1>
			

			  <div class="container bcontent"> 
				<hr />
				<p style="text-align: center; color: red;" >${errormsg}</p>
				<div class="container">
				
					<table class="table table-fluid" id="myTable">
						<thead>
							<tr>
								<th>drugName</th>
								<th>quantity</th>
								<th>Refill Cycle</th>
								<th>Status</th>
								<th>Location</th>
								<th>Subscribed on</th>
								<th>Subscription EndsOn</th>
								<th>unsubscribe</th>
								<th>Request refill</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${not empty subscriptionList}">
									<c:forEach items="${subscriptionList}" var="subscription">

										<tr>
											<td>${subscription.drugName }</td>
											<td>${subscription.quantity }</td>
											<td>${subscription.refillCycle }</td>
											<td>${subscription.subscriptionStatus }</td>
											<td>${subscription.memberLocation }</td>
											<td>${subscription.subscriptionDate }</td>
											<td>${subscription.subscriptionEndDate}</td>
												<form method=post
													action="unsubscribe/${subscription.subscriptionId }">
													<td><input class="btn btn-success" type=submit
														value="Unsubscribe"> </td>
													<td><a class="btn btn-secondary"
														href="adhocRefill/${subscription.subscriptionId}">
														Request refill</a></td>

												</form>
										</tr>


									</c:forEach>
								</c:when>
								<c:otherwise>
									<h3>${msg}</h3>
									<br>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					
					<script>
						$(document).ready(function() {
							$('#myTable').DataTable();
						});
					</script>
				</div>

			</div>
	 </div>  
	</section>

	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<!-- ======= Footer ======= -->
  
		<footer id="footer" class="mt-auto fixed-bottom">
			<div class="footer-top">
				<div class="container"></div>
			</div>

			<div class="container">
				<div class="credits">Thank you! We are here for your needs.</div>
			</div>
		</footer>
		
	 
	<!-- Template Main JS File -->
	  <script src="js/main.js"></script>  
</body>
</html>