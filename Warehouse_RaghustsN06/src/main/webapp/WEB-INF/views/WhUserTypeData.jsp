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
<h1>Welcome to WHUserType Data Page!!</h1>
				
<table border="1">
<tr>
	<th>ID</th><th>User Type</th> <th>User Code</th><th>User For</th> <th>Email</th><th>Contact</th> <th>ID Type</th><th>IF Other</th> <th>ID NUMBER</th><th>CREATED ON</th> <th>MODIFIED ON</th>
</tr>
<c:forEach items="${whUserTypesList}" var="whu">
<td><c:out value="${whu.whUserTypeId}"/></td>
<td><c:out value="${whu.userType}"/></td>
<td><c:out value="${whu.whUserCode }"/></td>
<td><c:out value="${whu.whUserFor}"/></td>
<td><c:out value="${whu.whUserEmail}"/></td>
<td><c:out value="${whu.whUserContact}"/></td>
<td><c:out value="${whu.whUserIdType}"/></td>
<td><c:out value="${whu.whUserIdTypeOther}"/></td>
<td><c:out value="${whu.whUserIdNumber}"/></td>
<td><c:out value="${whu.createdDate}"/></td>
<td><c:out value="${whu.lasModifiedDate}"/></td>
<td><a href="deleteWhUserType?whUserTypeId=${whu.whUserTypeId}">DELETE</a></td>
<td><a href="editWhUserType?whUserTypeId=${whu.whUserTypeId}">EDIT</a></td>
</c:forEach>
</table>
</body>
</html>