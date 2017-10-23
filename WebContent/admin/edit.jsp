<%@ page import="model.ProductPojo"%>
<%@ page import="model.CategoryPojo"%>
<%@ page import="java.util.TreeSet"%>
<%@ page import="java.util.Iterator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Create new user</title>
<link rel="stylesheet" href="style.css" type="text/css"></link>
</head>
<body>


	<c:if
		test="${sessionScope.user == null || sessionScope.user.isAdmin == false}">
		<c:redirect url="../login.jsp"></c:redirect>
	</c:if>

	<%
		ProductPojo p = null;
		String productName = "";
		TreeSet<ProductPojo> prod = (TreeSet) application.getAttribute("products");
		Iterator<ProductPojo> itr = prod.iterator();
		while (itr.hasNext()) {
			p = itr.next();
			if (p.getProductID() == Integer.parseInt(request.getParameter("id"))) {
				productName = p.getName();
				break;
			}
		}
	%>

	<div>
		<fieldset>
			<legend class="legend">Edit product</legend>
			<form action="${pageContext.request.contextPath}/dataVerifier"
				method="post">
				<table>
					<tbody>
						<tr style="background: white;">
							<td><label for="id">Id:</label></td>
							<td><input id="id" name="id" placeholder='${param.id}'
								type="text" readonly /></td>
						</tr>
						<tr style="background: white;">
							<td><label for="product">Product name:</label></td>
							<td><input id="product" placeholder=<%=productName%>
								name="name" type="text" minlength="3" required /></td>
						</tr>
						<tr style="background: white;">
							<td><label for="category">Category id:</label></td>
							<td><input id="categoryId"
								placeholder=<%=p.getCategory().getCategoryID()%>
								name="categoryId" type="number" type="number" step="1" min="1" /></td>
						</tr>
						<tr style="background: white;">
							<td><label for="price">Price:</label></td>
							<td><input id="price" placeholder=<%= p.getPrice() %>
								name="price" value="${p.price}" type="number" step="0.01"
								min="0" /></td>
						</tr>

						<tr style="background: white;">
							<td><label for="description">Description:</label></td>
							<td><input id="description"
								placeholder=<%=p.getDescription()%> name="description"
								type="text" minlength="3" required /></td>
						</tr>

						<tr style="background: white;">
							<td><label for="brand">Brand id:</label></td>
							<td><input id="brandId"
								placeholder=<%=p.getBrand().getBrandID()%> name="brandId"
								type="number" step="1" min="1" /></td>
						</tr>

						<tr style="background: white;">
							<td><label for="availability">Available quantity:</label></td>
							<td><input id="availability"
								placeholder=<%=p.getQuantity()%> name="availability"
								type="number" step="1" min="1" /></td>
						</tr>

						<tr style="background: white;">
							<td><label for="image">Image url:</label></td>
							<td><input id="imageURL" placeholder=<%=p.getImageURL()%>
								name="imageUrl" type="text" /></td>
						</tr>
					</tbody>
				</table>
				<p>
					<input type="submit" value=" Save edits " />
				</p>
				<p>
					<a
						href="${pageContext.request.contextPath}/admin/productManagement.jsp"">Back</a>
				</p>
			</form>
		</fieldset>
	</div>
</body>
</html>
