<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html 5>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body bgcolor="pink">
	<jsp:include page="header.jsp"></jsp:include>
	<br>
	<br>
	<h1>Bul Bulgaria 69. et 14.</h1>
	
	<div class="contact-info">
    	<h2>We are expecting you!</h2>
	</div>
	<div class="contact-map">
		<iframe
			src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2933.863992359683!2d23.285610250982835!3d42.66423747906538!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x40aa84ea4f733295%3A0x81a4f738482633ed!2z0LHRg9C7LiDigJ7QkdGK0LvQs9Cw0YDQuNGP4oCcIDY5LCAxNDA0INC60LIuINCc0LDQvdCw0YHRgtC40YDRgdC60Lgg0LvQuNCy0LDQtNC4LCDQodC-0YTQuNGP!5e0!3m2!1sru!2sbg!4v1508471600264"
			width="400" height="250"></iframe>

	</div>
	<div class="contact-form">
		<div class="contact-info">
			<h3>CONTACT FORM</h3>
		</div>
		<form action="messageFromCustomer" message = "get">
			<div class="contact-left">
				<input type="text" placeholder="Name" required name="name">
				<input	type="text" placeholder="E-mail" required name="email"> 
				<input	type="text" placeholder="Subject" required  name="subject">
			</div>
			<div class="contact-right">
				<textarea placeholder="Message" required></textarea>
			</div>
			<div class="clearfix"></div>
			<input type="submit" value="SUBMIT">
		</form>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>