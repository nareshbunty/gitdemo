<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UOM Register Page</title>
<link rel="stylesheet" type="text/css" href="../css/common.css">
</head>
<body>
<h1>Welcome to UOM Register Page!!</h1>
<form:form action="insertUom" method="post" modelAttribute="uom">
<pre>
UOM Type   :<form:select path="uomType">
			<form:option value="">--select--</form:option>
			<c:forEach items="${uomTypes}" var="ob">
			<form:option value="${ob.key}">${ob.value}</form:option>
			</c:forEach>
			</form:select>
<form:errors path="uomType" cssClass="error"/>
UOM Model  :<form:input type="text" path="uomModel"/>
<form:errors path="uomModel" cssClass="error"/>
Description:<form:textarea path="description"></form:textarea>
<form:errors path="description" cssClass="error"/>
<input type="submit" value="Create UOM"/>
</pre>
</form:form>
${message}
<br/>
<a href="getAllUoms">View All UOMs</a>
</body>
</html>