<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page errorPage="errorpage.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>AutumnProject</title>


    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Autumn Project</title>

    <!-- Bootstrap Core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>

    <!-- Theme CSS -->
    <link href="css/agency.min.css" rel="stylesheet">
    

	<style>
		@import url(https://fonts.googleapis.com/css?family=Oswald:400,300);
		@import url(https://fonts.googleapis.com/css?family=Open+Sans);
			#inputMessages{
				font-family: 'Open Sans', sans-serif;
			}
			
			.popup-box {
			   background-color: #ffffff;
			    border: 1px solid #b0b0b0;
			    bottom: 0;
			    display: none;
			    height: 415px;
			    position: fixed;
			    right: 70px;
			    width: 300px;
			    font-family: 'Open Sans', sans-serif;
			}
			.round.hollow {
			    margin: 40px 0 0;
			}
			.round.hollow a {
			    border: 2px solid #ff6701;
			    border-radius: 35px;
			    color: red;
			    color: #ff6701;
			    font-size: 23px;
			    padding: 10px 21px;
			    text-decoration: none;
			    font-family: 'Open Sans', sans-serif;
			}
			.round.hollow a:hover {
			    border: 2px solid #000;
			    border-radius: 35px;
			    color: red;
			    color: #000;
			    font-size: 23px;
			    padding: 10px 21px;
			    text-decoration: none;
			}
			.popup-box-on {
			    display: block !important;
			}
			.popup-box .popup-head {
			    background-color: #fff;
			    clear: both;
			    color: #7b7b7b;
			    display: inline-table;
			    font-size: 21px;
			    padding: 7px 10px;
			    width: 100%;
			     font-family: Oswald;
			}
			.bg_none i {
			    border: 1px solid #ff6701;
			    border-radius: 25px;
			    color: #ff6701;
			    font-size: 17px;
			    height: 33px;
			    line-height: 30px;
			    width: 33px;
			}
			.bg_none:hover i {
			    border: 1px solid #000;
			    border-radius: 25px;
			    color: #000;
			    font-size: 17px;
			    height: 33px;
			    line-height: 30px;
			    width: 33px;
			}
			.bg_none {
			    background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
			    border: medium none;
			}
			.popup-box .popup-head .popup-head-right {
			    margin: 11px 7px 0;
			}
			.popup-box .popup-messages {
			}
			.popup-head-left img {
			    border: 1px solid #7b7b7b;
			    border-radius: 50%;
			    width: 44px;
			}
			.popup-messages-footer > textarea {
			    border-bottom: 1px solid #b2b2b2 !important;
			    height: 34px !important;
			    margin: 7px;
			    padding: 5px !important;
			     border: medium none;
			    width: 95% !important;
			}
			.popup-messages-footer {
			    background: #fff none repeat scroll 0 0;
			    bottom: 0;
			    position: absolute;
			    width: 100%;
			}
			.popup-messages-footer .btn-footer {
			    overflow: hidden;
			    padding: 2px 5px 10px 6px;
			    width: 100%;
			}
			.simple_round {
			    background: #d1d1d1 none repeat scroll 0 0;
			    border-radius: 50%;
			    color: #4b4b4b !important;
			    height: 21px;
			    padding: 0 0 0 1px;
			    width: 21px;
			}
			
			
			
			
			
			.popup-box .popup-messages {
			    background: #3f9684 none repeat scroll 0 0;
			    height: 275px;
			    overflow: auto;
			}
			.direct-chat-messages {
			    overflow: auto;
			    padding: 10px;
			    transform: translate(0px, 0px);
			    
			}
			.popup-messages .chat-box-single-line {
			    border-bottom: 1px solid #a4c6b5;
			    height: 12px;
			    margin: 7px 0 20px;
			    position: relative;
			    text-align: center;
			}
			.popup-messages abbr.timestamp {
			    background: #3f9684 none repeat scroll 0 0;
			    color: #fff;
			    padding: 0 11px;
			}
			
			.popup-head-right .btn-group {
			    display: inline-flex;
				margin: 0 8px 0 0;
				vertical-align: top !important;
			}
			.chat-header-button {
			    background: transparent none repeat scroll 0 0;
			    border: 1px solid #636364;
			    border-radius: 50%;
			    font-size: 14px;
			    height: 30px;
			    width: 30px;
			}
			.popup-head-right .btn-group .dropdown-menu {
			    border: medium none;
			    min-width: 122px;
				padding: 0;
			}
			.popup-head-right .btn-group .dropdown-menu li a {
			    font-size: 12px;
			    padding: 3px 10px;
				color: #303030;
			}
			
			.popup-messages abbr.timestamp {
			    background: #3f9684  none repeat scroll 0 0;
			    color: #fff;
			    padding: 0 11px;
			}
			.popup-messages .chat-box-single-line {
			    border-bottom: 1px solid #a4c6b5;
			    height: 12px;
			    margin: 7px 0 20px;
			    position: relative;
			    text-align: center;
			}
			.popup-messages .direct-chat-messages {
			    height: auto;
			}
			.popup-messages .direct-chat-text {
			    background: #dfece7 none repeat scroll 0 0;
			    border: 1px solid #dfece7;
			    border-radius: 2px;
			    color: #1f2121;
			}
			
			.popup-messages .direct-chat-timestamp {
			    color: #fff;
			    opacity: 0.6;
			}
			
			.popup-messages .direct-chat-name {
				font-size: 15px;
				font-weight: 600;
				margin: 0 0 0 49px !important;
				color: #fff;
				opacity: 0.9;
			}
			.popup-messages .direct-chat-info {
			    display: block;
			    font-size: 12px;
			    margin-bottom: 0;
			}
			.popup-messages  .big-round {
			    margin: -9px 0 0 !important;
			}
			.popup-messages  .direct-chat-img {
			    border: 1px solid #fff;
				background: #3f9684  none repeat scroll 0 0;
			    border-radius: 50%;
			    float: left;
			    height: 40px;
			    margin: -21px 0 0;
			    width: 40px;
			}
			.direct-chat-reply-name {
			    color: #fff;
			    font-size: 15px;
			    margin: 0 0 0 10px;
			    opacity: 0.9;
			}
			
			.direct-chat-img-reply-small
			{
			    border: 1px solid #fff;
			    border-radius: 50%;
			    float: left;
			    height: 20px;
			    margin: 0 8px;
			    width: 20px;
				background:#3f9684;
			}
			
			.popup-messages .direct-chat-msg {
			    margin-bottom: 10px;
			    position: relative;
			}
			
			.popup-messages .doted-border::after {
				background: transparent none repeat scroll 0 0 !important;
			    border-right: 2px dotted #fff !important;
				bottom: 0;
			    content: "";
			    left: 17px;
			    margin: 0;
			    position: absolute;
			    top: 0;
			    width: 2px;
				 display: inline;
				  z-index: -2;
			}
			
			.popup-messages .direct-chat-msg::after {
			    background: #fff none repeat scroll 0 0;
			    border-right: medium none;
			    bottom: 0;
			    content: "";
			    left: 17px;
			    margin: 0;
			    position: absolute;
			    top: 0;
			    width: 2px;
				 display: inline;
				  z-index: -2;
			}
			.direct-chat-text::after, .direct-chat-text::before {
			   
			    border-color: transparent #dfece7 transparent transparent;
			    
			}
			.direct-chat-text::after, .direct-chat-text::before {
			    -moz-border-bottom-colors: none;
			    -moz-border-left-colors: none;
			    -moz-border-right-colors: none;
			    -moz-border-top-colors: none;
			    border-color: transparent #d2d6de transparent transparent;
			    border-image: none;
			    border-style: solid;
			    border-width: medium;
			    content: " ";
			    height: 0;
			    pointer-events: none;
			    position: absolute;
			    right: 100%;
			    top: 15px;
			    width: 0;
			}
			.direct-chat-text::after {
			    border-width: 5px;
			    margin-top: -5px;
			}
			.popup-messages .direct-chat-text {
			    background: #dfece7 none repeat scroll 0 0;
			    border: 1px solid #dfece7;
			    border-radius: 2px;
			    color: #1f2121;
			}
			.direct-chat-text {
			    background: #d2d6de none repeat scroll 0 0;
			    border: 1px solid #d2d6de;
			    border-radius: 5px;
			    color: #444;
			    margin: 5px 0 0 50px;
			    padding: 5px 10px;
			    position: relative;
			}
	
	</style>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->


</head>

<body id="page-top" class="index">

    <!-- Navigation -->
    <nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
                </button>
                <a class="navbar-brand page-scroll" href="#page-top">Myplace</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li class="hidden">
                        <a href="#page-top"></a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#services">Services</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#portfolio">Details</a>
                    </li>
                    
                    <li>
                        <a class="page-scroll" href="#contact">Contact</a>
                    </li>
                    <li>
                    	<a href="list.cdo">PlacePost</a>
                    </li>
                    <!-- 
                    <li>
                        <a class="page-scroll" href="#about">About</a>
                    </li>
                     -->
                    <li>
                        <a href="#" id="addClass" onclick="livechatstart();">LiveChat</a>
                    </li>
                    
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
	
    <!-- Header -->
    <header>
        <div class="container">
            <div class="intro-text">
                <div class="intro-lead-in">Autumn to Winter!</div>
                <div class="intro-heading">Pick your place</div>
                <%if(session.getAttribute("id") == null) {
                    %>
                 	<a href="login.html" id="loginbutton" class="btn btn-primary">Sign in</a>
                    <%
                } 
                else {
                    System.out.println(session.getAttribute("id") + " 님 환영합니다");
                    %>
                    <h2> Good to see you! <%=session.getAttribute("id")%> </h2><br/>
                    <a href="memberlistlogout.do" id="logoutbutton" class="btn btn-primary">Log out</a> 
                    <%
                }
                	%>
            </div>
        </div>
        <!-- <a href="#" onclick="window.open('webscchat.jsp','name','resizable=no width=600 height=500');return false">test</a> -->
    </header>

    <!-- Services Section -->
    <section id="services">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Services</h2>
                    <h3 class="section-subheading text-muted">Make your own place map.</h3>
                </div>
            </div>
            <div class="row text-center">
                <div class="col-md-4">
                    <span class="fa-stack fa-4x">
                        <i class="fa fa-circle fa-stack-2x text-primary"></i>
                        <i class="fa fa-map-marker fa-stack-1x fa-inverse"></i>
                    </span>
                    <h4 class="service-heading">Mark your place to remember</h4>
                    <p class="text-muted">Mark your favorite spots on the google map and make your own ratings.</p>
                </div>
                <div class="col-md-4">
                    <span class="fa-stack fa-4x">
                        <i class="fa fa-circle fa-stack-2x text-primary"></i>
                        <i class="fa fa-book fa-stack-1x fa-inverse"></i>
                    </span>
                    <h4 class="service-heading">Hand it over your android phone</h4>
                    <p class="text-muted">Store the place information on your android phone locally or on our server. Get recommended places from others!</p>
                </div>
                <div class="col-md-4">
                    <span class="fa-stack fa-4x">
                        <i class="fa fa-circle fa-stack-2x text-primary"></i>
                        <i class="fa fa-heart fa-stack-1x fa-inverse"></i>
                    </span>
                    <h4 class="service-heading">Share your place with friends </h4>
                    <p class="text-muted">Instant feelings can be spreaded from you. Share the moment and experience with your friends</p>
                </div>
            </div>
        </div>
    </section>

    <!-- Portfolio Grid Section -->
    <section id="portfolio" class="bg-light-gray">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Functions</h2>
                    <h3 class="section-subheading text-muted">Meet the easy, simple and handy UI.</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6 portfolio-item">
                    <a href="#portfolioModal1" class="portfolio-link" data-toggle="modal">
                        <div class="portfolio-hover">
                            <div class="portfolio-hover-content">
                                <i class="fa fa-plus fa-3x"></i>
                            </div>
                        </div>
                        <img src="img/portfolio/roundicons.png" class="img-responsive" alt="">
                    </a>
                    <div class="portfolio-caption">
                        <h4>Round Icons</h4>
                        <p class="text-muted">Graphic Design</p>
                    </div>
                </div>
                <div class="col-md-4 col-sm-6 portfolio-item">
                    <a href="#portfolioModal2" class="portfolio-link" data-toggle="modal">
                        <div class="portfolio-hover">
                            <div class="portfolio-hover-content">
                                <i class="fa fa-plus fa-3x"></i>
                            </div>
                        </div>
                        <img src="img/portfolio/startup-framework.png" class="img-responsive" alt="">
                    </a>
                    <div class="portfolio-caption">
                        <h4>Startup Framework</h4>
                        <p class="text-muted">Website Design</p>
                    </div>
                </div>
                <div class="col-md-4 col-sm-6 portfolio-item">
                    <a href="#portfolioModal3" class="portfolio-link" data-toggle="modal">
                        <div class="portfolio-hover">
                            <div class="portfolio-hover-content">
                                <i class="fa fa-plus fa-3x"></i>
                            </div>
                        </div>
                        <img src="img/portfolio/treehouse.png" class="img-responsive" alt="">
                    </a>
                    <div class="portfolio-caption">
                        <h4>Treehouse</h4>
                        <p class="text-muted">Website Design</p>
                    </div>
                </div>
                <div class="col-md-4 col-sm-6 portfolio-item">
                    <a href="#portfolioModal4" class="portfolio-link" data-toggle="modal">
                        <div class="portfolio-hover">
                            <div class="portfolio-hover-content">
                                <i class="fa fa-plus fa-3x"></i>
                            </div>
                        </div>
                        <img src="img/portfolio/golden.png" class="img-responsive" alt="">
                    </a>
                    <div class="portfolio-caption">
                        <h4>Golden</h4>
                        <p class="text-muted">Website Design</p>
                    </div>
                </div>
                <div class="col-md-4 col-sm-6 portfolio-item">
                    <a href="#portfolioModal5" class="portfolio-link" data-toggle="modal">
                        <div class="portfolio-hover">
                            <div class="portfolio-hover-content">
                                <i class="fa fa-plus fa-3x"></i>
                            </div>
                        </div>
                        <img src="img/portfolio/escape.png" class="img-responsive" alt="">
                    </a>
                    <div class="portfolio-caption">
                        <h4>Escape</h4>
                        <p class="text-muted">Website Design</p>
                    </div>
                </div>
                <div class="col-md-4 col-sm-6 portfolio-item">
                    <a href="#portfolioModal6" class="portfolio-link" data-toggle="modal">
                        <div class="portfolio-hover">
                            <div class="portfolio-hover-content">
                                <i class="fa fa-plus fa-3x"></i>
                            </div>
                        </div>
                        <img src="img/portfolio/dreams.png" class="img-responsive" alt="">
                    </a>
                    <div class="portfolio-caption">
                        <h4>Dreams</h4>
                        <p class="text-muted">Website Design</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- About Section -->
    <!-- 
    <section id="about">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">About</h2>
                    <h3 class="section-subheading text-muted">Lorem ipsum dolor sit amet consectetur.</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <ul class="timeline">
                        <li>
                            <div class="timeline-image">
                                <img class="img-circle img-responsive" src="img/about/1.jpg" alt="">
                            </div>
                            <div class="timeline-panel">
                                <div class="timeline-heading">
                                    <h4>2009-2011</h4>
                                    <h4 class="subheading">Our Humble Beginnings</h4>
                                </div>
                                <div class="timeline-body">
                                    <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sunt ut voluptatum eius sapiente, totam reiciendis temporibus qui quibusdam, recusandae sit vero unde, sed, incidunt et ea quo dolore laudantium consectetur!</p>
                                </div>
                            </div>
                        </li>
                        <li class="timeline-inverted">
                            <div class="timeline-image">
                                <img class="img-circle img-responsive" src="img/about/2.jpg" alt="">
                            </div>
                            <div class="timeline-panel">
                                <div class="timeline-heading">
                                    <h4>March 2011</h4>
                                    <h4 class="subheading">An Agency is Born</h4>
                                </div>
                                <div class="timeline-body">
                                    <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sunt ut voluptatum eius sapiente, totam reiciendis temporibus qui quibusdam, recusandae sit vero unde, sed, incidunt et ea quo dolore laudantium consectetur!</p>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="timeline-image">
                                <img class="img-circle img-responsive" src="img/about/3.jpg" alt="">
                            </div>
                            <div class="timeline-panel">
                                <div class="timeline-heading">
                                    <h4>December 2012</h4>
                                    <h4 class="subheading">Transition to Full Service</h4>
                                </div>
                                <div class="timeline-body">
                                    <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sunt ut voluptatum eius sapiente, totam reiciendis temporibus qui quibusdam, recusandae sit vero unde, sed, incidunt et ea quo dolore laudantium consectetur!</p>
                                </div>
                            </div>
                        </li>
                        <li class="timeline-inverted">
                            <div class="timeline-image">
                                <img class="img-circle img-responsive" src="img/about/4.jpg" alt="">
                            </div>
                            <div class="timeline-panel">
                                <div class="timeline-heading">
                                    <h4>July 2014</h4>
                                    <h4 class="subheading">Phase Two Expansion</h4>
                                </div>
                                <div class="timeline-body">
                                    <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sunt ut voluptatum eius sapiente, totam reiciendis temporibus qui quibusdam, recusandae sit vero unde, sed, incidunt et ea quo dolore laudantium consectetur!</p>
                                </div>
                            </div>
                        </li>
                        <li class="timeline-inverted">
                            <div class="timeline-image">
                                <h4>Be Part
                                    <br>Of Our
                                    <br>Story!</h4>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </section>
	 -->
    <!-- Team Section -->
    <!-- 
    <section id="team" class="bg-light-gray">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Our Amazing Team</h2>
                    <h3 class="section-subheading text-muted">Lorem ipsum dolor sit amet consectetur.</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4">
                    <div class="team-member">
                        <img src="img/team/1.jpg" class="img-responsive img-circle" alt="">
                        <h4>Kay Garland</h4>
                        <p class="text-muted">Lead Designer</p>
                        <ul class="list-inline social-buttons">
                            <li><a href="#"><i class="fa fa-twitter"></i></a>
                            </li>
                            <li><a href="#"><i class="fa fa-facebook"></i></a>
                            </li>
                            <li><a href="#"><i class="fa fa-linkedin"></i></a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="team-member">
                        <img src="img/team/2.jpg" class="img-responsive img-circle" alt="">
                        <h4>Larry Parker</h4>
                        <p class="text-muted">Lead Marketer</p>
                        <ul class="list-inline social-buttons">
                            <li><a href="#"><i class="fa fa-twitter"></i></a>
                            </li>
                            <li><a href="#"><i class="fa fa-facebook"></i></a>
                            </li>
                            <li><a href="#"><i class="fa fa-linkedin"></i></a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="team-member">
                        <img src="img/team/3.jpg" class="img-responsive img-circle" alt="">
                        <h4>Diana Pertersen</h4>
                        <p class="text-muted">Lead Developer</p>
                        <ul class="list-inline social-buttons">
                            <li><a href="#"><i class="fa fa-twitter"></i></a>
                            </li>
                            <li><a href="#"><i class="fa fa-facebook"></i></a>
                            </li>
                            <li><a href="#"><i class="fa fa-linkedin"></i></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2 text-center">
                    <p class="large text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aut eaque, laboriosam veritatis, quos non quis ad perspiciatis, totam corporis ea, alias ut unde.</p>
                </div>
            </div>
        </div>
    </section>
	 -->
    <!-- Clients Aside -->
    <!-- 
    <aside class="clients">
        <div class="container">
            <div class="row">
                <div class="col-md-3 col-sm-6">
                    <a href="#">
                        <img src="img/logos/envato.jpg" class="img-responsive img-centered" alt="">
                    </a>
                </div>
                <div class="col-md-3 col-sm-6">
                    <a href="#">
                        <img src="img/logos/designmodo.jpg" class="img-responsive img-centered" alt="">
                    </a>
                </div>
                <div class="col-md-3 col-sm-6">
                    <a href="#">
                        <img src="img/logos/themeforest.jpg" class="img-responsive img-centered" alt="">
                    </a>
                </div>
                <div class="col-md-3 col-sm-6">
                    <a href="#">
                        <img src="img/logos/creative-market.jpg" class="img-responsive img-centered" alt="">
                    </a>
                </div>
            </div>
        </div>
    </aside>
	 -->
    <!-- Contact Section -->
    <section id="contact">
		<div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Contact Us</h2>
                    <h3 class="section-subheading text-muted">Lorem ipsum dolor sit amet consectetur.</h3>
                    <iframe width="600" height="450" frameborder="0" style="border:0"
						src="https://www.google.com/maps/embed/v1/place?q=%EC%8B%9C%EB%85%BC%ED%98%84%EC%97%AD&key=AIzaSyB-3w5GHzUBnp4Ub8l4Gt08Vn51QTU3RxM" allowfullscreen></iframe>
					<br/>
					<br/>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <form name="sentMessage" id="contactForm" novalidate>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Your Name *" id="name" required data-validation-required-message="Please enter your name.">
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <input type="email" class="form-control" placeholder="Your Email *" id="email" required data-validation-required-message="Please enter your email address.">
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <input type="tel" class="form-control" placeholder="Your Phone *" id="phone" required data-validation-required-message="Please enter your phone number.">
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <textarea class="form-control" placeholder="Your Message *" id="message" required data-validation-required-message="Please enter a message."></textarea>
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-lg-12 text-center">
                                <div id="success"></div>
                                <button type="submit" class="btn btn-xl">Send Message</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <footer>
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <span class="copyright">Copyright &copy; Your Website 2016</span>
                </div>
                <div class="col-md-4">
                    <ul class="list-inline social-buttons">
                        <li><a href="#"><i class="fa fa-twitter"></i></a>
                        </li>
                        <li><a href="#"><i class="fa fa-facebook"></i></a>
                        </li>
                        <li><a href="#"><i class="fa fa-linkedin"></i></a>
                        </li>
                    </ul>
                </div>
                <div class="col-md-4">
                    <ul class="list-inline quicklinks">
                        <li><a href="#">Privacy Policy</a>
                        </li>
                        <li><a href="#">Terms of Use</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </footer>

    <!-- Portfolio Modals -->
    <!-- Use the modals below to showcase details about your portfolio projects! -->

    <!-- Portfolio Modal 1 -->
    <div class="portfolio-modal modal fade" id="portfolioModal1" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="close-modal" data-dismiss="modal">
                    <div class="lr">
                        <div class="rl">
                        </div>
                    </div>
                </div>
                <div class="container">
                    <div class="row">
                        <div class="col-lg-8 col-lg-offset-2">
                            <div class="modal-body">
                                <!-- Project Details Go Here -->
                                <h2>Project Name</h2>
                                <p class="item-intro text-muted">Lorem ipsum dolor sit amet consectetur.</p>
                                <img class="img-responsive img-centered" src="img/portfolio/roundicons-free.png" alt="">
                                <p>Use this area to describe your project. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Est blanditiis dolorem culpa incidunt minus dignissimos deserunt repellat aperiam quasi sunt officia expedita beatae cupiditate, maiores repudiandae, nostrum, reiciendis facere nemo!</p>
                                <p>
                                    <strong>Want these icons in this portfolio item sample?</strong>You can download 60 of them for free, courtesy of <a href="https://getdpd.com/cart/hoplink/18076?referrer=bvbo4kax5k8ogc">RoundIcons.com</a>, or you can purchase the 1500 icon set <a href="https://getdpd.com/cart/hoplink/18076?referrer=bvbo4kax5k8ogc">here</a>.</p>
                                <ul class="list-inline">
                                    <li>Date: July 2014</li>
                                    <li>Client: Round Icons</li>
                                    <li>Category: Graphic Design</li>
                                </ul>
                                <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i> Close Project</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Portfolio Modal 2 -->
    <div class="portfolio-modal modal fade" id="portfolioModal2" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="close-modal" data-dismiss="modal">
                    <div class="lr">
                        <div class="rl">
                        </div>
                    </div>
                </div>
                <div class="container">
                    <div class="row">
                        <div class="col-lg-8 col-lg-offset-2">
                            <div class="modal-body">
                                <h2>Project Heading</h2>
                                <p class="item-intro text-muted">Lorem ipsum dolor sit amet consectetur.</p>
                                <img class="img-responsive img-centered" src="img/portfolio/startup-framework-preview.png" alt="">
                                <p><a href="http://designmodo.com/startup/?u=787">Startup Framework</a> is a website builder for professionals. Startup Framework contains components and complex blocks (PSD+HTML Bootstrap themes and templates) which can easily be integrated into almost any design. All of these components are made in the same style, and can easily be integrated into projects, allowing you to create hundreds of solutions for your future projects.</p>
                                <p>You can preview Startup Framework <a href="http://designmodo.com/startup/?u=787">here</a>.</p>
                                <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i> Close Project</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Portfolio Modal 3 -->
    <div class="portfolio-modal modal fade" id="portfolioModal3" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="close-modal" data-dismiss="modal">
                    <div class="lr">
                        <div class="rl">
                        </div>
                    </div>
                </div>
                <div class="container">
                    <div class="row">
                        <div class="col-lg-8 col-lg-offset-2">
                            <div class="modal-body">
                                <!-- Project Details Go Here -->
                                <h2>Project Name</h2>
                                <p class="item-intro text-muted">Lorem ipsum dolor sit amet consectetur.</p>
                                <img class="img-responsive img-centered" src="img/portfolio/treehouse-preview.png" alt="">
                                <p>Treehouse is a free PSD web template built by <a href="https://www.behance.net/MathavanJaya">Mathavan Jaya</a>. This is bright and spacious design perfect for people or startup companies looking to showcase their apps or other projects.</p>
                                <p>You can download the PSD template in this portfolio sample item at <a href="http://freebiesxpress.com/gallery/treehouse-free-psd-web-template/">FreebiesXpress.com</a>.</p>
                                <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i> Close Project</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Portfolio Modal 4 -->
    <div class="portfolio-modal modal fade" id="portfolioModal4" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="close-modal" data-dismiss="modal">
                    <div class="lr">
                        <div class="rl">
                        </div>
                    </div>
                </div>
                <div class="container">
                    <div class="row">
                        <div class="col-lg-8 col-lg-offset-2">
                            <div class="modal-body">
                                <!-- Project Details Go Here -->
                                <h2>Project Name</h2>
                                <p class="item-intro text-muted">Lorem ipsum dolor sit amet consectetur.</p>
                                <img class="img-responsive img-centered" src="img/portfolio/golden-preview.png" alt="">
                                <p>Start Bootstrap's Agency theme is based on Golden, a free PSD website template built by <a href="https://www.behance.net/MathavanJaya">Mathavan Jaya</a>. Golden is a modern and clean one page web template that was made exclusively for Best PSD Freebies. This template has a great portfolio, timeline, and meet your team sections that can be easily modified to fit your needs.</p>
                                <p>You can download the PSD template in this portfolio sample item at <a href="http://freebiesxpress.com/gallery/golden-free-one-page-web-template/">FreebiesXpress.com</a>.</p>
                                <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i> Close Project</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Portfolio Modal 5 -->
    <div class="portfolio-modal modal fade" id="portfolioModal5" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="close-modal" data-dismiss="modal">
                    <div class="lr">
                        <div class="rl">
                        </div>
                    </div>
                </div>
                <div class="container">
                    <div class="row">
                        <div class="col-lg-8 col-lg-offset-2">
                            <div class="modal-body">
                                <!-- Project Details Go Here -->
                                <h2>Project Name</h2>
                                <p class="item-intro text-muted">Lorem ipsum dolor sit amet consectetur.</p>
                                <img class="img-responsive img-centered" src="img/portfolio/escape-preview.png" alt="">
                                <p>Escape is a free PSD web template built by <a href="https://www.behance.net/MathavanJaya">Mathavan Jaya</a>. Escape is a one page web template that was designed with agencies in mind. This template is ideal for those looking for a simple one page solution to describe your business and offer your services.</p>
                                <p>You can download the PSD template in this portfolio sample item at <a href="http://freebiesxpress.com/gallery/escape-one-page-psd-web-template/">FreebiesXpress.com</a>.</p>
                                <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i> Close Project</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Portfolio Modal 6 -->
    <div class="portfolio-modal modal fade" id="portfolioModal6" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="close-modal" data-dismiss="modal">
                    <div class="lr">
                        <div class="rl">
                        </div>
                    </div>
                </div>
                <div class="container">
                    <div class="row">
                        <div class="col-lg-8 col-lg-offset-2">
                            <div class="modal-body">
                                <!-- Project Details Go Here -->
                                <h2>Project Name</h2>
                                <p class="item-intro text-muted">Lorem ipsum dolor sit amet consectetur.</p>
                                <img class="img-responsive img-centered" src="img/portfolio/dreams-preview.png" alt="">
                                <p>Dreams is a free PSD web template built by <a href="https://www.behance.net/MathavanJaya">Mathavan Jaya</a>. Dreams is a modern one page web template designed for almost any purpose. It’s a beautiful template that’s designed with the Bootstrap framework in mind.</p>
                                <p>You can download the PSD template in this portfolio sample item at <a href="http://freebiesxpress.com/gallery/dreams-free-one-page-web-template/">FreebiesXpress.com</a>.</p>
                                <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i> Close Project</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    
		
	<div class="popup-box chat-popup" id="qnimate">
		    		<div class="popup-head">
						<div class="popup-head-left pull-left">
						<!-- <img src="img/chatimg.jpg"> Joohyeok Jeong -->
						<!-- <img src="http://bootsnipp.com/img/avatars/bcf1c0d13e5500875fdd5a7e8ad9752ee16e7462.jpg" alt="iamgurdeeposahan"> Joohyeok Jeong -->
						</div>
							  <div class="popup-head-right pull-right">
								<div class="btn-group">
		    								  <button class="chat-header-button" data-toggle="dropdown" type="button" aria-expanded="false">
											   <i class="glyphicon glyphicon-cog"></i> </button>
											  <ul role="menu" class="dropdown-menu pull-right">
												<li><a href="#">Media</a></li>
												<li><a href="#">Block</a></li>
												<li><a href="#">Clear Chat</a></li>
												<li><a href="#">Email Chat</a></li>
											  </ul>
								</div>
								
								<button data-widget="remove" id="removeClass" class="chat-header-button pull-right" type="button"><i class="glyphicon glyphicon-off"></i></button>
		                      </div>
					  </div>
					
					<textarea class="popup-messages" id="messageWindow" rows="10" cols="35" readonly="true"></textarea>
					
					<div class="popup-messages-footer">
						<input id="inputMessage" onkeydown="if(event.keyCode == 13) chkEnter();" placeholder="Type a message..." rows="10" cols="40" name="message"></input>
						<button type="submit" id="sendbuttons" onclick="send()" value="send">send</button>
						<div class="btn-footer">
							<button class="bg_none"><i class="glyphicon glyphicon-film"></i> </button>
							<button class="bg_none"><i class="glyphicon glyphicon-camera"></i> </button>
				            <button class="bg_none"><i class="glyphicon glyphicon-paperclip"></i> </button>
							<button class="bg_none pull-right"><i class="glyphicon glyphicon-thumbs-up"></i> </button>
						</div>
					</div>
		  </div>



    <!-- jQuery -->
    <script src="vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>

<!-- 
	 <script type="text/javascript">
		  $(function(){
			  $("#addClass").click(function () {
			            $('#qnimate').addClass('popup-box-on');
			              });
			            
			              $("#removeClass").click(function () {
			            $('#qnimate').removeClass('popup-box-on');
			              });
			    })

		  </script>
		  
	    <script type="text/javascript">
		    function chkEnter(){
	        	if(event.which || event.keyCode){
	        		if((event.which == 13) || (event.keyCode==13)){
	        			document.getElementById('sendbuttons').click();
	        			return false;
	        		}
	        	}else{
	        		return true;
	        	}
	        }

	        var textarea = document.getElementById("messageWindow");
	        var webSocket = new WebSocket('ws://localhost:8181/AutumnToWinter/broadcasting');
	        var inputMessage = document.getElementById("inputMessage");
	    	function livechatstart(){
		        
		       
		        $(document).on('click', '.panel-heading span.icon_minim', function (e) {
		            var $this = $(this);
		            if (!$this.hasClass('panel-collapsed')) {
		                $this.parents('.panel').find('.panel-body').slideUp();
		                $this.addClass('panel-collapsed');
		                $this.removeClass('glyphicon-minus').addClass('glyphicon-plus');
		            } else {
		                $this.parents('.panel').find('.panel-body').slideDown();
		                $this.removeClass('panel-collapsed');
		                $this.removeClass('glyphicon-plus').addClass('glyphicon-minus');
		            }
		        });
		        $(document).on('focus', '.panel-footer input.chat_input', function (e) {
		            var $this = $(this);
		            if ($('#minim_chat_window').hasClass('panel-collapsed')) {
		                $this.parents('.panel').find('.panel-body').slideDown();
		                $('#minim_chat_window').removeClass('panel-collapsed');
		                $('#minim_chat_window').removeClass('glyphicon-plus').addClass('glyphicon-minus');
		            }
		        });
		        $(document).on('click', '#new_chat', function (e) {
		            var size = $( ".chat-window:last-child" ).css("margin-left");
		             size_total = parseInt(size) + 400;
		            alert(size_total);
		            var clone = $( "#chat_window_1" ).clone().appendTo( ".container" );
		            clone.css("margin-left", size_total);
		        });
		        $(document).on('click', '.icon_close', function (e) {
		            //$(this).parent().parent().parent().parent().remove();
		            $( "#chat_window_1" ).remove();
		        });
	
	    	}

		    webSocket.onerror = function(event) {
		      onError(event)
		    };
		    webSocket.onopen = function(event) {
		      onOpen(event)
		    };
		    webSocket.onmessage = function(event) {
		      onMessage(event)
		    };
		    webSocket.onclose = function(event){
		    	onClose(event)
		    };
		    function onMessage(event) {
		        textarea.value += "Others : " + event.data + "\n";
		    }
		    function onOpen(event) {
		        textarea.value += "Entered!\n";
		    }
		    function onError(event) {
		      alert(event.data);
		    }
		    function onClose(event){
		    	 inputMessage.value="I'm leaving..";
		    	 document.getElementById('sendbuttons').click();
		    	 
		    }
		    function send() {
		        textarea.value += "Me : " + inputMessage.value + "\n";
		        webSocket.send(inputMessage.value);
		        inputMessage.value = "";
		    }
		 
		</script>
		 -->
    <!-- Contact Form JavaScript -->
    <script src="js/jqBootstrapValidation.js"></script>
    <script src="js/contact_me.js"></script>

    <!-- Theme JavaScript -->
    <script src="js/agency.min.js"></script>

</body>

</html>
