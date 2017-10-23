<%@ page errorPage = "error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
<head>
<title>Create new product</title>
<link rel="stylesheet" href="style.css" type="text/css"></link>
</head>
<body>
	<c:if
		test="${ sessionScope.user == null || sessionScope.user.isAdmin == false}">
		<c:redirect url="../login.jsp"></c:redirect>
	</c:if>

	<div>
		<fieldset>
			<legend class="legend">Create product</legend>
			<form action="${pageContext.request.contextPath}/dataVerifier" method="post">
				<table>
					<tbody>
						<tr style="background: white;">
							<td><label for="name">Product name:</label></td>
							<td><input id="name" name="name" type="text"
								minlength="3" required /></td>
						</tr>
						<tr style="background: white;">
							<td><label for="price">Price:</label></td>
							<td><input id="price" name="price" type="number" step="0.01"
								min="0" /></td>
						</tr>
						<tr style="background: white;">
							<td><label for="description">Description:</label></td>
							<td><input id="description" name="description" type="text"
								minlength="5" required /></td>
						</tr>
						<tr style="background: white;">
							<td><label for="categoryId">Category id:</label></td>
							<td><input id="categoryId" name="categoryId" type="number"
							min="1" /></td>
						</tr>
						<tr style="background: white;">
							<td><label for="brandId">Brand id:</label></td>
							<td><input id="brandId" name="brandId" type="number"
							min="1" /></td>
						</tr>
						<tr style="background: white;">
							<td><label for="availability">Products available:</label></td>
							<td><input id="availability" name="availability" type="number"
							min="1" /></td>
						</tr>
							<tr style="background: white;">
							<td><label for="imageUrl">Image URL:</label></td>
							<td><input id="imageUrl" name="imageUrl" type="text"
								minlength="5" required /></td>
						</tr>
					</tbody>
				</table>
				<p>
					<input type="submit" value=" Create product " />
				</p>
			</form>
			<p>
				<a href="${pageContext.request.contextPath}/admin/productManagement.jsp">Back</a>
			</p>
		</fieldset>
	</div>
</body>
</html>
