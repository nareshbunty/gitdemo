<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Uom Data Edit Page</title>
<link rel="stylesheet" type="text/css" href="../css/common.css">
</head>
<body>
<form:form action="updateUom" method="post" modelAttribute="uom">
<pre>
UOM ID  : <form:input type="text" path="uomId" readonly="true" />

UOM Type: <form:select path="uomType">
			<form:option value="">--select--</form:option>
			<c:forEach items="${uomTypes}" var="ob">
			 	 <form:option value="${ob.key}">${ob.value}</form:option>
			</c:forEach>
		  </form:select>
<form:errors path="uomType" cssClass="error"/>		  
UOM     : <form:input type="text" path="uomModel" />
<form:errors path="uomModel" cssClass="error"/>	
Description:<form:textarea path="description"></form:textarea>
<form:errors path="description" cssClass="error"/>
<input type="submit" value="Update" />	  
</pre>
</form:form>
</body>
</html>


