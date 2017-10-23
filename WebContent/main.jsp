<%@page import="db.ProductDAO"%>
<%@page import="model.ProductPojo"%>
<%@page import="java.util.HashSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/style.css" type="text/css"></link>

</head>
<body>

	<c:if test="${ sessionScope.user == null }">
		<c:redirect url="login.jsp"></c:redirect>
	</c:if>
	<c:if test="${ sessionScope.newUser != null }">
		<tr>
			<td>You have successfully registered an account ${ sessionScope.newUser.name}
				. It is time to enjoy our services!</td>
		</tr>
	</c:if>

	<jsp:include page="header.jsp"></jsp:include>

	<table class="products" border="1" cellpadding="5">
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Quantity</th>
			<th>Price</th>
		</tr>
		<c:forEach items="${applicationScope.products}" var="product">
			<tr>
				<td class="cent">${ product.productID }</td>
				<td class="cent"><c:out value="${ product.name }"></c:out></td>
				<td class="cent"><c:out value="${ product.quantity }"></c:out></td>
				<td class="cent"><c:out value="${ product.price }"></c:out></td>
			</tr>
		</c:forEach>
	</table>


	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>