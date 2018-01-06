
<%@page import="com.happybus.util.RolesConstants"%>
<%@page isELIgnored="false" %>
<html>
<body>
<h2>AdminDasboard</h2>
<h3>${status}</h3>
</body><br/><hr/>
<a href="addTravel">Add Travel</a>
<a href="addCustomerSupport">Add CustomerSupport</a>
<a href="searchRoutes">Search Routes</a>
<a href="editRoutes">Edit Routes</a>
<a href="searchBookingForSuperAdmin">Bookings History</a>
<a href="searchAllBus">Search Bus</a>
<a href="addroutes">add routes</a>
</html>

<%
response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility

if(session.getAttribute("userId")!=null 
&& session.getAttribute("userRole")!=null){
	
	if( session.getAttribute("userRole").equals(RolesConstants.ROLE_CUSTOMER_SUPPORT)){
	response.setStatus(301);
response.setHeader("location","/HappyBus/customerSupportDashboard");
}}
	

%>