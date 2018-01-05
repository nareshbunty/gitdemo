<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ShipmentType Data</title>
</head>
<body>
<h1>Welcome to ShipmentType Data Page!!</h1>
<table border="1">
<tr>
	<th>ID</th><th>MODE</th><th>CODE</th><th>ENABLED</th><th>GRADE</th><th>NOTE</th><th>CREATED ON</th><th>LAST MODIFIED</th>
</tr>
<c:forEach items="${shipmentTypeList}" var="s">
<tr>
<td><c:out value="${s.shipmentId}"/></td>
<td><c:out value="${s.shipmentMode}"/></td>
<td><c:out value="${s.shipmentCode}"/></td>
<td>
<c:choose>
	<c:when test="${s.enabled eq 'YES'}">
   		<input type="checkbox" checked="checked" disabled="disabled">YES
   	</c:when>
   	<c:otherwise>
   	    <input type="checkbox" disabled="disabled">NO
   	</c:otherwise>
</c:choose>
</td>
<td><c:out value="${s.shipmentGrade}"/></td>
<td><c:out value="${s.description}"/></td>
<td><c:out value="${s.createdDate}"/></td>
<td><c:out value="${s.lastModifiedDate}"/></td>
<td><a href="deleteShipmentType?shipmentTypeId=${s.shipmentId}">DELETE</a></td>
<td><a href="editShipmentType?shipmentTypeId=${s.shipmentId}">EDIT</a></td>
</tr>
</c:forEach>
</table>
</body>
</html>