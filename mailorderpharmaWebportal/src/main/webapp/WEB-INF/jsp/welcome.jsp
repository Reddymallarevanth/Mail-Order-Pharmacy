<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>Mail Order Pharmacy</title>
  

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


<script type="text/javascript">
        function preventBack() { window.history.forward(); }
        setTimeout("preventBack()", 0);
        window.onunload = function () { null };
    </script>
</head>

<body>

  <!-- ======= Header ======= -->
  <header id="header" class="header-transparent">
    <div class="container">

      <div id="logo" class="pull-left">
      <h3><a href="home">Mail Order Pharmacy</a></h3>
      </div>

      <nav id="nav-menu-container">
        <ul class="nav-menu">
          <li class="menu-active"><a href="home">Home</a></li>
          <li><a href="#subscribe">Subscribe</a></li>
          <li><a href="#services">Drugs Available</a></li>
          <li><a href="#view-subscribed-drugs">Subscribed medicines</a></li>
          <li><a href="#due-for-refill">Due for refill</a></li>
          <li><a href="#ad-hoc-request">Refill Request</a></li>
          <li><a href="logout">Logout</a></li>
        </ul>
      </nav><!-- #nav-menu-container -->
    </div>
  </header><!-- End Header -->

  <!-- ======= Hero Section ======= -->
  <section id="hero">
    <div class="hero-container" data-aos="zoom-in" data-aos-delay="100">
    <h1>Hello <%= session.getAttribute("memberId") %></h1>
      <h1>Welcome to our Pharmacy</h1>
      <a href="#subscribe" class="btn-get-started">Subscribe Here</a>
    </div>
  </section><!-- End Hero Section -->

  <main id="main">

    <!-- ======= Subscribe Section ======= -->
    <section id="subscribe">
      <div class="container" data-aos="fade-up">
        <div class="row subscribe-container">

          <div class="col-lg-6 content order-lg-1 order-2">
            <h2 class="title">Subscribe with us!</h2>
            

            <div class="icon-box" data-aos="fade-up" data-aos-delay="100">
              <div class="icon"><i class="fa fa-thumbs-up"></i></div><br>
              <h4 class="title"><a href="prescriptionform">Subscribe</a></h4>
            </div>
			<br><br>
            <div class="icon-box" data-aos="fade-up" data-aos-delay="200">
              <div class="icon"><i class="fa fa-thumbs-down"></i></div><br>
              <h4 class="title"><a href="subscriptions">Unsubscribe</a></h4>
            </div>

          </div>

          <div class="col-lg-6 background order-lg-2 order-1" data-aos="fade-left" data-aos-delay="100"></div>
        </div>

      </div>
    </section><!-- End Subscribe Section -->

    

    <!-- ======= Supported Drugs Section ======= -->
    <section id="services">
      <div class="container" data-aos="fade-up">
        <div class="section-header">
          <h3 class="section-title">Drugs Available</h3>
        </div><br>
        <div class="md-form md-bg input-with-pre-icon">
          <form name="getSupportedDrugs" method="post" action="supportedDrugs">
            <center><button type="submit" class="btn btn-outline-success btn-lg ">View</button></center>
          </form>
         </div> 
        </div>
    </section><!-- End Services Section -->

    <!-- ======= View Subscribed Drugs Section ======= -->
    <section id="view-subscribed-drugs">
      <div class="container">
        <div class="row" data-aos="zoom-in">
          <div class="col-lg-9 text-center text-lg-left">
            <h3 class="cta-title">Subscribed Drugs</h3>
          </div>
          <div class="col-lg-3 cta-btn-container text-center">
            <a class="cta-btn align-middle" href="subscriptions">View</a>
          </div>
        </div>

      </div>
    </section><!-- End View Subscribed Drugs Section -->

    <!-- ======= Due for Refill Section ======= -->
    <section id="due-for-refill">
      <div class="container" data-aos="fade-up">
        <div class="section-header">
          <h3 class="section-title">Due for refill</h3>
          <p class="section-description">Please enter the date to check for dues.</p>
        </div>
        <div class="md-form md-bg input-with-pre-icon">
          <form name="dueForRefill" model="dateModel" action="/webportal/refillDueAsOfDate" method="post">
            <label for="example-date-input" class="col-2 col-form-label">Date</label>
            <input class="form-control" type="date" name ="date" id="example-date-input">
            <br>
            <button type="submit" class="btn btn-outline-success btn-lg ">Submit</button>
          </form>
          </div>
        </div>
    </section><!-- End Services Section -->

    <!-- ======= Adhoc Request Section Section ======= -->
    <section id="ad-hoc-request">
      <div class="container">
        <div class="row" data-aos="zoom-in">
          <div class="col-lg-9 text-center text-lg-left">
            <h3 class="cta-title">Adhoc Refill Request</h3>
          </div>
          <div class="col-lg-3 cta-btn-container text-center">
            <a class="cta-btn align-middle" href="subscriptions">Click to order</a>
          </div>
        </div>

      </div>
    </section><!-- End View Subscribed Drugs Section -->

  </main><!-- End #main -->

  <!-- ======= Footer ======= -->
  <footer id="footer">
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

  <a href="#" class="back-to-top"><i class="fa fa-chevron-up"></i></a>
	
	<script>
		document.getElementById('example-date-input').max = new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString().split("T")[0];
	</script>
	
  <!-- Vendor JS Files -->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="vendor/jquery.easing/jquery.easing.min.js"></script>
  <script src="vendor/counterup/counterup.min.js"></script>
  <script src="vendor/waypoints/jquery.waypoints.min.js"></script>
  <script src="vendor/aos/aos.js"></script>

  <!-- Template Main JS File -->
  <script src="js/main.js"></script>

</body>

</html>