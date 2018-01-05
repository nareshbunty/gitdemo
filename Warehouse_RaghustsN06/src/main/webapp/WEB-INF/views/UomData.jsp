<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="../js/uom.js"></script>
</head>
<body>
<h1>Welcome to UOM Data Page!!</h1>
<hr/>
Search Screen:
<form:form action="getAllUoms" method="get" modelAttribute="uom">
<table>
<tr>
<td>UOM Type:<form:select path="uomType">
			<form:option value="">--select--</form:option>
			<form:option value="PACK">PACKING</form:option>
			<form:option value="NOPACK">NO PACKING</form:option>
			<form:option value="NA">-NA-</form:option>
			</form:select>
</td>
<td>UOM Model  :<form:input type="text" path="uomModel"/></td>
<td>Description:<form:textarea path="description"></form:textarea></td>
<td><input type="submit" value="Search"/></td>
</tr>
</table>
</form:form>

<hr/>
<c:if test="${!empty uoms}">
<table border="1">
<tr>
	<th>ID</th><th>TYPE</th><th>UOM</th><th>Created</th><th>Last Modified</th><th>Notes</th>
</tr>
<c:forEach items="${uoms}" var="uom">
<tr>
<td><c:out value="${uom.uomId}"/></td>
<td><c:out value="${uom.uomType}"/></td>
<td><c:out value="${uom.uomModel}"/></td>
<td><c:out value="${uom.createdDate}"/></td>
<td><c:out value="${uom.lastModifiedDate}"/></td>
<td><c:out value="${uom.description}"/></td>
<td><a href="deleteUom?uomId=${uom.uomId}">DELETE</a></td>
<td><a href="editUom?uomId=${uom.uomId}">EDIT</a></td>
</tr>
</c:forEach>
</table>

<!--Pagination -->
<c:if test="${!uomPage.isFirst()}">
<a href="#" onclick="setParam('page','0')">First</a>&nbsp;
</c:if>

<c:if test="${uomPage.hasPrevious()}">
<a href="#" onclick="setParam('page',${uomPage.getNumber()-1})">Previous</a>&nbsp;
</c:if>

<c:forEach begin="0" end="${uomPage.getTotalPages()-1}" var="i">
<c:choose>
<c:when test="${uomPage.getNumber() eq i}">
<c:out value="${i+1}"/>&nbsp;
</c:when>
<c:otherwise>
<a href="#" onclick="setParam('page',${i})"><c:out value="${i+1}"/></a>&nbsp;
</c:otherwise>
</c:choose>
</c:forEach>

<c:if test="${uomPage.hasNext()}">
<a href="#" onclick="setParam('page',${uomPage.getNumber()+1})">Next</a>&nbsp;
</c:if>

<c:if test="${!uomPage.isLast()}">
<a href="#" onclick="setParam('page',${uomPage.getTotalPages()-1})">Last</a>&nbsp;
</c:if>
</c:if>
<c:if test="${empty uoms}">
<h3>No Records Found</h3>
</c:if>

</body>
</html>