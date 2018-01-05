<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../css/common.css">
</head>
<body>
<h2>Welcome to UOM Multipart Operations</h2>
<h2>Export Data into Excel Sheet</h2>
<a href="uomExport">Export UOM Data</a>
<hr/>
<h2>Import Data using Excel Sheet</h2>
<form action="uomImport" method="post" enctype="multipart/form-data">
<pre>
Select File : <input type="file" name="uomFile"/> <input type="submit" value="Import Uoms"/>
</pre>
</form>
<!-- Final Message -->
<c:if test="${!empty importMessage}">
<c:out value="${importMessage}"/> 
</c:if>

<!-- Row by Row Errors -->
<c:if test="${!empty importErrors}">
<table border="1">
<tr>
<th colspan="2">Errors in Excel Sheet (Row by Row)</th>
</tr>
<c:forEach items="${importErrors}" var="error">
<tr>
<td><c:out value="${error.key}"/></td>
<td class="error"><c:out value="${error.value}"/></td>
</tr>
</c:forEach>
</table>
</c:if>

</body>
</html>
