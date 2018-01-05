<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Purchase Order Edit</title>
</head>
<body>
<h1>Welcome to Purchase Order Edit Page!!</h1>
<form:form action="updatePurchaseOrder" method="post" modelAttribute="po">
<pre>
Order ID   : <form:input path="orderId" readonly="true"/> 
Order Code : <form:input path="orderCode" readonly="true"/>
<form:errors path="orderCode" cssClass="error"/>
Shipment Mode: <form:select path="shipmentMode">
				<form:option value="">--select--</form:option>
				<form:options items="${shipmentTypes}" itemValue="shipmentId" itemLabel="shipmentCode"/>
			   </form:select>
<form:errors path="shipmentMode" cssClass="error"/>
<c:if test="${'OPEN' eq po.status}">
Vendor : <form:select path="vendor">
			<form:option value="">--select--</form:option>
			<form:options items="${vendorsList}" itemValue="whUserTypeId" itemLabel="whUserCode"/>		 
		 </form:select>
<form:errors path="vendor" cssClass="error"/>
</c:if>
Reference Number : <form:input path="refernceNumber" readonly="true"/>
<form:errors path="refernceNumber" cssClass="error"/>
Quality Check : <form:radiobuttons path="qualityCheck" items="${qualityChecks}"/>
<form:errors path="qualityCheck" cssClass="error"/>
Default Status : <form:input path="status" readonly="true"/>
<form:errors path="status" cssClass="error"/>
Description : <form:textarea path="description"/>
<form:errors path="description" cssClass="error"/>

<input type="submit" value="Update Order"/>

</pre>
</form:form>
</body>
</html>