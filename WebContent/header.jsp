
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link href="css/css/bootstrap.css" rel='stylesheet' type='text/css' />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/jquery.min.js"></script>
<!-- Custom Theme files -->
<link href="css/css/style.css" rel="stylesheet" type="text/css"
	media="all" />
<!-- Custom Theme files -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords"
	content="Eshop Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript">
	
	
	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 


</script>
<!--webfont-->
<!-- for bootstrap working -->
<script type="text/javascript" src="js/bootstrap-3.1.1.min.js"></script>
<!-- //for bootstrap working -->
<!-- cart -->
<script src="js/simpleCart.min.js">
	
</script>
<!-- cart -->
<link rel="stylesheet" href="ccs/css/flexslider.css" type="text/css"
	media="screen" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<a href="index.jsp"> <img src="image/emag.jpg">
</a>
<body>
	<div class="header">
		<form class="navi" action="about.jsp">
			<input type="submit" value="About Us">
		</form>
		<form class="navi" action="index.jsp">
			<input type="submit" value="Products on sale">
		</form>
		<form class="navi" action="orders.jsp">
			<input type="submit" value="View My orders">
		</form>
		<c:if test="${sessionScope.user == null}">
			<form class="navi" action="login.jsp" method="post">
				<input type="submit" value="Login">
			</form>
		</c:if>
		<form class="navi" action="contact.jsp">
			<input type="submit" value="Contacts">
		</form>
		<c:if test="${sessionScope.user != null}">
			<form class="navi" action="logout" method="post">
				<input type="submit" value="Logout">
			</form>
		</c:if>
		<c:if test="${sessionScope.user.isAdmin == true}">
			<form class="navi"
				action="${pageContext.request.contextPath}/admin/productManagement.jsp"
				method="post">
				<input type="submit" value="Product Management">
			</form>
		</c:if>
		<h3 class="welcome">Welcome, ${ sessionScope.user.name }</h3>
	</div>
</body>
</html>