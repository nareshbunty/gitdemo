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
	<th>ID</th><th>FNAME</th><th>LNAME</th><th>EMAIL</th><th>MOBILE</th><th>GENDER</th>
</tr>
<c:forEach items="${uipatientslist}" var="patientBean">
<tr>
<td><c:out value="${patientBean.pId}"/></td>
<td><c:out value="${patientBean.firstName}"/></td>
<td><c:out value="${patientBean.lastName}"/></td>
<td><c:out value="${patientBean.email}"/></td>
<td><c:out value="${patientBean.phone}"/></td>
<td><c:out value="${patientBean.gender}"/></td>


<td><a href="deletePatient?pId=${patientBean.pId}"><img src="images/delete.png" width="20" height="20"/></a></td>
<td><a href="editPatient?pId=${patientBean.pId}"><img src="images/edit.png" width="20" height="20"/></a></td>
</tr>
</c:forEach>
</table>
</body>
</html>