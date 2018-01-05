<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ShipmentType Register</title>
<link rel="stylesheet" type="text/css" href="../css/common.css">
</head>
<body>
<h1>Welcome to ShipmentType Register Page!!</h1>
<form:form action="insertShipmentType" method="post" modelAttribute="shipmentType">
<pre>
Shipment Mode : <form:select path="shipmentMode">
				 <form:option value="">--select--</form:option>
				 <c:forEach items="${shipmentModes}" var="s">
				 	<form:option value="${s}">${s}</form:option>
				 </c:forEach>
				</form:select>
<form:errors path="shipmentMode" cssClass="error"/>				
Shipment Code : <form:input path="shipmentCode"/>
<form:errors path="shipmentCode" cssClass="error"/>
Enable Shipment : <form:checkbox path="enabled" value="YES"/> YES
<form:errors path="enabled" cssClass="error"/>
Shipment Grade  : <form:radiobuttons path="shipmentGrade" items="${shipmentGrades}"/>
<form:errors path="shipmentGrade" cssClass="error"/>
Description  : <form:textarea path="description"/>
<form:errors path="description" cssClass="error"/>
<input type="submit" value="Create Shipment Type"/>				
</pre>
</form:form>
<c:if test="${!empty message}">
<c:out value="${message}"/>
</c:if>
<a href="getAllShipmentTypes">View All</a>
</body>
</html>