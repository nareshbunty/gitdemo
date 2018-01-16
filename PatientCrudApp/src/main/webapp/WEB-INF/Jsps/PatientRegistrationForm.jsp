<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="savePatient" method="post">
<pre>
<!-- 
ID  : <input type="text" name="pId" /> -->
FIRST NAME: <input type="text" name="firstName" />
LAST NAME: <input type="text" name="lastName" />
Email:<input type="text" name="email" />
MOBILE NUMBER:  <input type="text" name="phone" />
GENDER: 
<input type="radio" name="gender" value="male">Urban
<input type="radio" name="gender" value="Rural">Rural


<input type="submit" value="Save Or Update"/>
</pre>
</form>
 <a href="viewAllPatients">View Employees</a>


</body>
</html>