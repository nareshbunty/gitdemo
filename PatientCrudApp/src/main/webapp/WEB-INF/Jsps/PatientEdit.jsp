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
<h1>Welcome to emp Edit page</h1>

<form action="updatePatient" method="post">
<table>
<tr>
 <td>Id</td> <td><input type="text" name="pId" value="${patientBean.pId}" readonly="readonly"/></td>
</tr> 
<tr>
<td>FIRST Name</td><td><input type="text" name="firstName" value="${patientBean.firstName}"/></td>
</tr>
<tr>
<td>LAST NAME</td><td><input type="text" name="lastName" value="${patientBean.lastName}"/></td>
</tr>
<tr>
<td>Email</td><td><input type="text" name="email" value="${patientBean.email}"/></td>
</tr>
<tr>
<td>PHONE</td><td><input type="text" name="phone" value="${patientBean.phone}"/></td>
</tr>
<tr>
<td>GENDER</td>
<td><c:choose>
	<c:when test="${patientBean.gender eq 'male' }">
		<input type="radio" name="gender" value="male" checked="checked">MALE
        <input type="radio" name="gender" value="female">female
	</c:when>
	<c:otherwise>
		<input type="radio" name="gender" value="male">Male
        <input type="radio" name="gender" value="female" checked="checked">Female	
	</c:otherwise>
 </c:choose>
 </td>
</tr>


<tr>
<td><input type="submit" value="Update"/></td>
</tr>	  
</table>
</form>

</body>
</html>