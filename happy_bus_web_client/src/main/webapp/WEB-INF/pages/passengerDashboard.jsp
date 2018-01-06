<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@include file="includers.jsp"%> 
<script type="text/javascript">
/* write your script here */

</script>
</head>

<%
response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
	response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility



if(session.getAttribute("userId")==null ){
	
		response.setStatus(301);
	response.setHeader("location","/HappyBus");
}
%> 




<body class="hbb-container">


	<img class="hb-ddown" style="float: left;" src="images/logontitle.png"
		width="500px" height="150px">


	<div>
		<%@include file="passengerMenu.jsp"%>
	</div>

<div class="hbb-container hbb-card ">

<!-- body left -->
	<div class="hb-nav">
		<div class="hb-label-marign" >
			<img src="images/passenger_family.png" width="250px" height="350px">
		</div>

	</div>

	<!-- body right  -->
	<div class="hb-article">

		<div class="" style="max-width: 500px">
		
		
		</div>

	</div>


</div>


	

	<!-- bottom footer -->

	<div class="hbb-card hb-mar-toponeper">
		<div class="">
			<img src="images/Banner.png" width="100%" height="120px">
		</div>
	</div>

</body>

</html>