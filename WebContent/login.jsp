<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<jsp:include page="header.jsp"></jsp:include>

<c:if test="${ requestScope.error == null }">
	<h1>Welcome, please login:</h1>
	<br>
</c:if>
<c:if test="${ requestScope.error != null }">
	<h1 style="color: red">Sorry, username or password missmatch.
		Reason: ${requestScope.error }</h1>
</c:if>

<c:if test="${ sessionScope.user != null }">
	<c:redirect url="main.jsp"></c:redirect>
	<h1 style="color: black">You are already logged in.</h1>

</c:if>

<form action="login" method="post">
	<fieldset>
	<label><span>Email:</span> <input type="text" name="user"></label> <br>
	<label><span>Password:</span><input type="password" name="pass"></label>
	<br>
	<label><input type="submit" value="Login"> </label>
	<br>
		</fieldset>
		<br>
</form>

Don`t have an account yet? Please register
<a href="register.jsp">here</a>
<br>
<a href="index.jsp" title="Go to Home Page">Home</a>
<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>