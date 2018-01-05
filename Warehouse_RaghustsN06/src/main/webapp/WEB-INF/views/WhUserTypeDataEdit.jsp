<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WHUserType Edit</title>
</head>
<body>
<h1>Welcome to WHUserType Edit page!!</h1>
<form:form action="updateWhUserType" method="post" modelAttribute="whUserType">
<pre>
User Type Id: <form:input path="whUserTypeId" readonly="true"/>

User Type : <form:radiobuttons path="userType" items="${userTypes}" disabled="true"/>
<form:hidden path="userType"/>
<form:errors path="userType"/>
User Code : <form:input path="whUserCode"/>
<form:errors path="whUserCode"/>
User For  : <form:input path="whUserFor" readonly="true"/>
<form:errors path="whUserFor"/>
User Email: <form:input path="whUserEmail"/>
<form:errors path="whUserEmail"/>
User Contact: <form:input path="whUserContact"/>
<form:errors path="whUserContact"/>
User Id Type: <form:select path="whUserIdType" >
				<form:option value="">--select--</form:option>
  				<form:options items="${whUserIdTypes}"/>
			  </form:select>
<form:errors path="whUserIdType"/>
If other : <form:input path="whUserIdTypeOther"/>
<form:errors path="whUserIdTypeOther"/>
ID Number: <form:input path="whUserIdNumber"/>
<form:errors path="whUserIdNumber"/>
<input type="submit" value="Update WH User"/>			  
</pre>
</form:form>
</body>
</html>