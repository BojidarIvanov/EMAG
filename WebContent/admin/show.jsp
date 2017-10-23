
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>	
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix="sql" %>   
<!DOCTYPE html>					  
<html>
<head>
<title>Create new user</title>
<link rel = "stylesheet" href = "style.css" type = "text/css"></link>
</head>
<body>
    <sql:setDataSource
        var = "myDS"
        driver = "com.mysql.jdbc.Driver"
        url = "jdbc:mysql://localhost:3306/emag_final_project"
        user = "Ivan" 
        password = "Koparan2525"
    />

    <sql:query var = "listStuff" dataSource = "${myDS}">
	SELECT * FROM products where product_id = ${param.id};
    </sql:query>

    <div>
        <fieldset><legend class="legend">Display product</legend>
          <table>
            <tbody>
              <tr style = "background: white;}">
                <td><label for = "id">Id:</label></td><td><input id = "id" name = "id"
                                                                  style = "border:none;"
                                                                  value = "${param.id}"
                                                                  type = "text" 
                                                                  readonly/></td>
              </tr>
              <tr style="background: white;}">
                <td><label for = "product">Product:</label></td><td><input id = "product" name = "product"
                                                                           style = "border:none;"
                                                                           value = "${listStuff.rows[0].name}"
                                                                           type = "text" 
                                                                           readonly/></td>
              </tr>
              <tr style="background: white;}">
                <td><label for = "category">Category:</label></td><td><input id = "category" name = "category"
                                                                             style = "border:none;"
                                                                             value = "${listStuff.rows[0].category_id}"
                                                                             type = "text" 
                                                                             readonly/></td>
              </tr>

              <tr style="background: white;}">
                <td><label for = "price">Price:</label></td><td><input id = "price" name = "price"
                                                                       style = "border:none;"
                                                                       value = "${listStuff.rows[0].price}"
                                                                       type = "number" 
                                                                       readonly/></td>
              </tr>
            </tbody>
          </table>
        </fieldset>
    </div>
   <p><a href="${pageContext.request.contextPath}/admin/productManagement.jsp">Back</a>&nbsp;&nbsp;|&nbsp;&nbsp;
       <a href="${pageContext.request.contextPath}/admin/edit.jsp?id=${param.id}">Edit</a></p>
</body>
</html>