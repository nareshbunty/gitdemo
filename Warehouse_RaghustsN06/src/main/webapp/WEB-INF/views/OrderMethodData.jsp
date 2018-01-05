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
<h1>Welcome to OrderMethod Data Page!!</h1>

<table border="1">
<tr>
	<th>ID</th><th>MODE</th><th>CODE</th><th>METHOD</th><th>ACCEPT</th><th>NOTE</th><th>CREATED ON</th><th>MODIFIED ON</th>
</tr>
<c:forEach items="${orderMethodsList}" var="om">
<tr>
<td><c:out value="${om.orderMethodId}"/></td>
<td><c:out value="${om.orderMode}"/></td>
<td><c:out value="${om.orderCode}"/></td>
<td><c:out value="${om.orderMetd}"/></td>
<td>
<c:forEach items="${om.orderAccept}" var="omac">
<input type="checkbox" checked="checked" disabled="disabled"><c:out value="${omac}"/>
</c:forEach>
</td>
<td><c:out value="${om.description}"/></td>
<td><c:out value="${om.createdDate}"/></td>
<td><c:out value="${om.lastModifiedDate}"/></td>

<td><a href="deleteOrderMethod?orderMethodId=${om.orderMethodId}">DELETE</a></td>
<td><a href="editOrderMethod?orderMethodId=${om.orderMethodId}">EDIT</a></td>

</tr>
</c:forEach>
</table>
</body>
</html>