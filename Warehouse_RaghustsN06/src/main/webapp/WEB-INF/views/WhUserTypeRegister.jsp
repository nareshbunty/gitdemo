<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WHUserType Register</title>
<link rel="stylesheet" type="text/css" href="../css/common.css">
</head>
<body>
<h1>Welcome to WHUserType Register page!!</h1>
<form:form action="insertWhUserType" method="post" modelAttribute="whUserType">
<pre>
User Type : <form:radiobuttons path="userType" items="${userTypes}"/>
<form:errors path="userType" cssClass="error"/>
User Code : <form:input path="whUserCode"/>
<form:errors path="whUserCode" cssClass="error"/>
User For  : <form:input path="whUserFor" />
<form:errors path="whUserFor" cssClass="error"/>
User Email: <form:input path="whUserEmail"/>
<form:errors path="whUserEmail" cssClass="error"/>
User Contact: <form:input path="whUserContact"/>
<form:errors path="whUserContact" cssClass="error"/>
User Id Type: <form:select path="whUserIdType" >
				<form:option value="">--select--</form:option>
  				<form:options items="${whUserIdTypes}"/>
			  </form:select>
<form:errors path="whUserIdType" cssClass="error"/>
If other : <form:input path="whUserIdTypeOther"/>
<form:errors path="whUserIdTypeOther" cssClass="error"/>
ID Number: <form:input path="whUserIdNumber"/>
<form:errors path="whUserIdNumber" cssClass="error"/>
<input type="submit" value="Create WH User"/>			  
<form:errors/>
</pre>
</form:form>
<c:if test="${!empty message}">
<c:out value="${message}"/>
</c:if>
<a href="getAllWhUserTypes">View All..</a>
</body>
</html>