<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Purchase Order Items Page</title>
</head>
<body>
<h1>Welcome to Purchase Order Items Page!!</h1>
<form:form action="poItemAdd" method="post" modelAttribute="poDtl">
<form:hidden path="poHdrId" readonly="true" />
<table border="1">
<tr>
<td colspan="2">Order Code: <input type="text" value="${po.orderCode}" readonly="readonly"/> <td>
</tr>
</table>
<br/>
<table border="1">
<tr>
	<td>Sl No</td><td>Select Item</td><td>Quantity</td>
</tr>
<tr>
<td><form:input path="slno" readonly="true" size="2"/></td>
<td><form:select path="itemDetails" items="${venItems}" itemValue="itemId" itemLabel="itemCode"/></td>
<td><form:input path="itemsQty"/></td>
<td><input type="submit" name="itemOpr" value="Add Item"/></td>

</tr>
</table>
<br/>
<hr/>
<table border="1">
<tr>
 <th>Sl no </th><th>Item</th><th>Base Cost</th><th>Quantity</th>
</tr>
<c:forEach items="${po.details}" var="dtl">
<tr>
 <td>${dtl.slno}</td>
 <td>${dtl.itemDetails.itemCode}</td> 
 <td>${dtl.itemDetails.baseCost}</td> 
 <td>${dtl.itemsQty}</td> 
 <td><a href="removeItem?slno=${dtl.slno}&poId=${dtl.poHdrId}">Remove Item</a></td> 
</tr>
</c:forEach>
</table>
<br/><br/>
<c:if test="${!empty po.details}">
<input type="submit" name="itemOpr" value="Save and Continue"/>
</c:if>
</form:form>


</body>
</html>