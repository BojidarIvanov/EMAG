
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
<head>
<title>The products in stock</title>
<link rel="stylesheet" href="style.css" type="text/css"></link>
</head>
<body>

	<c:if
		test="${sessionScope.user == null || sessionScope.user.isAdmin == false}">
		<c:redirect url="../login.jsp"></c:redirect>
	</c:if>

	<center>
		<h3>Products in store</h3>
	</center>
	<div align="center">
		<p>
			<a href="${pageContext.request.contextPath}/admin/create.jsp">Create
				a new product</a>
		</p>
		<p>
			<a href="${pageContext.request.contextPath}/index.jsp">Back</a>
		</p>
		<table class="products" border="1" cellpadding="5">
			<tr>
				<th>Id</th>
				<th>Product</th>
				<th>Category</th>
				<th>Price</th>
			</tr>
			<c:forEach var="item" items="${products}">
				<tr>
					<td class="rght"><c:out value="${item.productID}" /></td>
					<td class="cent"><c:out value="${item.name}" /></td>
					<td class="cent"><c:out value="${item.category.name}" /></td>
					<td class="rght"><c:out value="$${item.price}" /></td>
					<td><a
						href="${pageContext.request.contextPath}/admin/show.jsp?id=${item.productID}">Show</a></td>
					<td><a
						href="${pageContext.request.contextPath}/admin/edit.jsp?id=${item.productID}">Edit</a></td>

					<td><a href="${item.imageURL}">Display Image</a></td>
				</tr>

			</c:forEach>
		</table>
		<p>
			<a href="${pageContext.request.contextPath}/admin/create.jsp">Create
				a new product</a>
		</p>
		<p>
			<a href="${pageContext.request.contextPath}/index.jsp">Back</a>
		</p>
	</div>
</body>
</html>