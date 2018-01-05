<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table border="1">
<tr>
	<th>ID</th>
	<th>CODE</th>
	<th>WIDTH</th>
	<th>LENGTH</th>
	<th>HEIGHT</th>
	<th>COST</th>
	<th>CURRENCY</th>
	<th>UOM</th>
	<th>SALE ORDER CODE</th>
	<th>PURACHSE ORDER CODE</th>
	<th>VENDORS</th>
	<th>CUSTOMERS</th>
	<th>NOTE</th>
	<th>CREATED ON</th>
	<th>MODIFIED ON</th>
</tr>
<c:forEach items="${itemsList}" var="ob">
<tr>
<td><c:out value="${ob.itemId}"/></td>
<td><c:out value="${ob.itemCode}"/></td>
<td><c:out value="${ob.itemWdth}"/></td>
<td><c:out value="${ob.itemLen}"/></td>
<td><c:out value="${ob.itemheit}"/></td>
<td><c:out value="${ob.baseCost}"/></td>
<td><c:out value="${ob.baseCurrency}"/></td>
<td><c:out value="${ob.itemUom.uomModel}"/></td>
<td><c:out value="${ob.itemSaleOrdMthd.orderCode}"/></td>
<td><c:out value="${ob.itemPurchaseOrdMthd.orderCode}"/></td>
<td>
<c:forEach items="${ob.itemVendors}" var="ven">
<c:out value="${ven.whUserCode}"/> &nbsp;
</c:forEach>
</td>
<td>
<c:forEach items="${ob.itemCustomers}" var="cust">
<c:out value="${cust.whUserCode}"/> &nbsp;
</c:forEach>
</td>

<td><c:out value="${ob.description}"/></td>
<td><c:out value="${ob.createdDate}"/></td>
<td><c:out value="${ob.lastModifiedDate}"/></td>

<td><a href="deleteItem?itemId=${ob.itemId}">DELETE</a></td>
<td><a href="editItem?itemId=${ob.itemId}">EDIT</a></td>

</tr>
</c:forEach>
</table>
</body>
</html>