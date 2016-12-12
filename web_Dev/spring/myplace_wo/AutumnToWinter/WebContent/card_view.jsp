<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="summer.CardDto" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<style>
	body{
		background-image:url('img/west-palm-beach-city.jpg');
	}
	.im-centered{
		margin:auto;max-width:500px;
	}
	.navbar{
		display: inline-block;
		margin-left: auto;
		margin-right: auto;
	}
	</style>
    <!-- Required meta tags always come first -->
    
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge; charset=utf-8"/>
	<meta charset="utf-8">



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


    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.4/css/bootstrap.min.css" integrity="2hfp1SzUoho7/TsGGGDaFdsuuDL0LX2hnUp6VkX3CUQ2K4K+xjboZdsXyp4oUHZj" crossorigin="anonymous">
  
	<!-- <meta http-equiv="Content-Type" content="text/html; charset=utf-8"> -->
<title>Insert title here</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
	%>

	<nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
                </button>
                <a class="navbar-brand page-scroll" href="index.jsp">Myplace</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-center">
                    <li class="hidden">
                        <a href="#page-top"></a>
                    </li>
                    <li>
                        <a href="write_view.jsp">Post</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
    <c:forEach items="${clist}" var="dtos" varStatus="status">
    	<c:if test="${status.index % 2 == 0}">
	    	<div class="im-centered">
			    <div class="row">
			      <div class="col-sm-6">
			        <div class="card">
			          <img class="card-img-top" src="${dtos.imagepath}" alt="Card image cap" style="max-width: 100%; height: auto;">
			          <div class="card-block text-xs-center">
			            <h4 class="card-title">${dtos.title}</h4>
			            <p class="card-text">${dtos.content}</p>
			            <div class="row">
				            <a href="#" class="btn btn-primary">Menu</a>
				            <a href="#" class="btn btn-secondary">Location</a>
				            <form action="details.cdo" method="post" name="detailbuttonclicked">    
				            	<input type="submit" class="btn btn-secondary" name="Details" value="Details">
				            	<input type="hidden" name="cardnumber" value="${dtos.cardnumber}">
				            	<input type="hidden" name="username" value="${dtos.username}">
				            </form>
			            </div>
			          </div>
			        </div>
			      </div>
			    
		    
	    </c:if>
	    <c:if test="${status.index % 2 == 1}">
			      <div class="col-sm-6">
			        <div class="card">
			          <img class="card-img-top" src="${dtos.imagepath}" alt="Card image cap" style="max-width: 100%; height: auto;">
			          <div class="card-block text-xs-center">
			            <h4 class="card-title">${dtos.title}</h4>
			            <p class="card-text">${dtos.content}</p>
			            <div class="row">
			            
				            <a href="#" class="btn btn-primary">Menu</a>
				            <a href="#" class="btn btn-secondary">Location</a>
				        	<form action="details.cdo" method="post" name="detailbuttonclicked">    
				            	<input type="submit" class="btn btn-secondary" name="Details" value="Details">
				            	<input type="hidden" name="cardnumber" value="${dtos.cardnumber}">
				            	<input type="hidden" name="username" value="${dtos.username}">
				            </form>
			            </div>
			          </div>
			        </div>
			      </div>
			    </div>
		    </div>
	    </c:if>
    </c:forEach>
    
	<table width="500" cellpadding="0" border="1">
		<tr>
			<td>number</td>
			<td>name</td>
			<td>title</td>
			<td>Date</td>
			<td>Hit</td>
		</tr>
		<c:forEach items="${list}" var="dto">
		<tr>
			<td>${dto.cardnumber}</td>
			<td>${dto.username}</td>
			<td>
				<a href="content_view.do?username=${dto.username}">${dto.title}</a>
			</td>
			<td>${dto.time}</td>
			<td>${dto.hit}</td>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="5"><a href="write_view.do">write</a></td>

		</tr>		
	</table>
	
	
    <!-- jQuery -->
    <script src="vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>

    <!-- Contact Form JavaScript -->
    <script src="js/jqBootstrapValidation.js"></script>
    <script src="js/contact_me.js"></script>

    <!-- Theme JavaScript -->
    <script src="js/agency.min.js"></script>
	
</body>
</html>