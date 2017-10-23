<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style media="screen" type="text/css">
label {
	display: block;
	position: relative;
}

label span {
	font-weight: bold;
	position: absolute;
	left: 3px;
}

label input, label textarea, label select {
	margin-left: 120px;
}
</style>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<jsp:include page="header.jsp"></jsp:include>
<c:if test="${ requestScope.error == null }">
	<h1>Welcome, please register an account</h1>
	<br>
</c:if>
<c:if test="${ requestScope.error != null }">
	<h1 style="color: red">Sorry, registration unsuccessful. Reason:
		${requestScope.error}</h1>
</c:if>
<form action="register" method="post">
	<fieldset>
		<legend>Personal information:</legend>
		<label><span>Full name </span> <input type="text" 
			name="name" required></label> <br>
			 <label><span>Email</span> <input
			type="text"  name="email" required > </label><br> <label><span>Year
				of birth</span> <input type="text" name="DOB"> <br></label> <br>
				<label><span>Phone</span><input
			type="text" name="phone"> </label><br> <label><span>Address</span><input
			type="text" name="address"> </label><br> <label><span>Password</span><input
			type="password" name="pass" required> </label><br> <label><span>Confirm
				password</span><input type="password"  name="pass2" required> </label><br>
		<input type="submit" value="Register"><br>
	</fieldset>
</form>
Already have an account? Please login:
<a href="login.jsp">here</a>
<br>
<a href="index.jsp" title="Go to Home Page">Home</a>
<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>