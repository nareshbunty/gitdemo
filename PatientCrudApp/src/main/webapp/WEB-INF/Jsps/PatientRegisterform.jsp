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

ID  : <input type="text" name="pId" value="${patientBean.pId}"/>
FIRST NAME: <input type="text" name="firstName" value="${patientBean.firstName}"/>
LAST NAME: <input type="text" name="lastName" value="${patientBean.lastName}"/>
Email:<input type="text" name="email" value="${patientBean.email}"/>
MOBILE NUMBER:  <input type="text" name="phone" value="${patientBean.phone}"/>
GENDER: 
<input type="radio" name="gender" value="male">Urban
<input type="radio" name="gender" value="Rural">Rural


<input type="submit" value="Save Or Update"/>
</pre>
</form>
VIEW ALL PATIENTS <a href="viewAllPatients">
</body>
</html>