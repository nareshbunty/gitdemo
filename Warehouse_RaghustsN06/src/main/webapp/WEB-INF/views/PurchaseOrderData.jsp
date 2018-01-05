<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Purchase Order Data</title>
</head>
<body>
<h1>Welcome to Purchase Order Data Page!!</h1>
<table border="1">
	<tr>
		<th>ID</th><th>CODE</th><th>SHIPMENT</th><th>VENDOR</th><th>REF NUM</th><th>QUALITY CHECK</th><th>STATUS</th><th>NOTE</th>	
	</tr>

<c:forEach items="${posList}" var="ob">
<tr>
	<td><c:out value="${ob.orderId}"/></td>
	<td><c:out value="${ob.orderCode}"/></td>
	<td><c:out value="${ob.shipmentMode.shipmentCode}"/></td>
	<td><c:out value="${ob.vendor.whUserCode}"/></td>
	<td><c:out value="${ob.refernceNumber}"/></td>
	<td><c:out value="${ob.qualityCheck}"/></td>
	<td><c:out value="${ob.status}"/></td>
	<td><c:out value="${ob.description}"/></td>
	<c:if test="${'OPEN' eq ob.status || 'PICKING' eq ob.status}">
	<td>
	 <a href="addPoItems?poId=${ob.orderId}">Add Items</a>
	</td>
	<td>
	 <a href="editPurchaseOrder?poId=${ob.orderId}">Edit Order</a>
	</td>
	<td>
	 <a href="cancelOrder?poId=${ob.orderId}">Cancel Order</a>
	</td>
	</c:if>
	<c:if test="${'ORDERED' eq ob.status}">
	 <td>
	  <a href="poConfirm?poId=${ob.orderId}">Confirm (Invoice)</a>
	 </td>
	 <td>
	  <a href="cancelOrder?poId=${ob.orderId}">Cancel Order</a>
	 </td>
	</c:if>
	<c:if test="${'INVOICED' eq ob.status}">
	 <td>
	  <a href="poInvoceGen?poId=${ob.orderId}">Generate Invoice</a>
	 </td>
	</c:if>
</tr>
</c:forEach>
</table>
</body>
</html>