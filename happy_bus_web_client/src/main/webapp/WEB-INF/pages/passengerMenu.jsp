<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<div class="hbb-bar hbb-amber hbb-card hbb-left-align hbb-medium">

	<!--<a class="hbb-bar-item hbb-button hbb-hide-medium hbb-hide-large hbb-right hbb-padding-large hbb-hover-white hbb-large hbb-red"
       href="javascript:void(0);" onclick="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>-->
	<a href="/HappyBus"
		class="hbb-bar-item hbb-button hbb-padding-large hbb-hover-white hbb-white">Home</a>
	<a href="mybooking"
		class="hbb-bar-item hbb-button hbb-hide-small hbb-padding-large hbb-hover-white">My
		Bookings</a><a onclick="document.getElementById('contact_frame_div').style.display='block'"
		class="hbb-bar-item hbb-button hbb-hide-small hbb-padding-large hbb-hover-white">Contact
		Us</a>
		<!-- <a href="submitQuery"
		class="hbb-bar-item hbb-button hbb-hide-small hbb-padding-large hbb-hover-white">SubmitQuery
		</a> -->
		<!-- <a href="#"
		class="hbb-bar-item hbb-button hbb-hide-small hbb-padding-large hbb-hover-white">YourQueries
		</a> -->
   
   <div class="hbb-dropdown-hover hbb-hide-small hbb-left">
		<button class="hbb-button hbb-padding-large">Customer Support</button>
		<div class="hbb-dropdown-content hbb-card-2 hbb-bar-block "
			>

			<a href="submitQuery" class="hbb-bar-item hbb-button hbb-right">SubmitQuery</a>
 
			<a href="showQuery" class="hbb-bar-item hbb-button hbb-right">YourQueries</a> 
			
		</div>
	</div>

	<div class="hbb-dropdown-hover hbb-hide-small hbb-right">
		<button class="hbb-button hbb-padding-large">Welcome  ${userName}</button>
		<div class="hbb-dropdown-content hbb-card-2 hbb-bar-block "
			>
			<a href="getProfile?userId=${userId}" class="hbb-bar-item hbb-button hbb-right">Edit Profile</a> 
			
			<a onclick="document.getElementById('changepassword_frame_div').style.display='block'" class="hbb-bar-item hbb-button hbb-right">Change Password</a>
			<a href ="logoutUser" class="hbb-bar-item hbb-button hbb-right">Sign Out</a>
		</div>
	</div>
</div>
<jsp:include page="changePasswordForm.jsp" />
<div id="contact_frame_div" class="hbb-modal">
	<div class="hbb-modal-content hbb-card-4 hbb-animate-zoom"
		style="max-width: 600px">
		<div class="hbb-center">
			<br> <span class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <span
				onclick="document.getElementById('contact_frame_div').style.display='none'"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span>
				 <img src="images/logontitle.png"
				alt="HappyBus logo" style="width: 50%" class=" hbb-margin-top ">
				
<br><br>
<div align="center" style="color:green;font-family:verdana;font-size:14px;">
			<span><b align="center" style="color:blue;font-family:verdana;font-size:15px;">For e-Ticketing/Refund Related Queries</b><br>
			E-Mail :online.support@happybus.com</span><br></div>
			
<div align="center" style="color:green;font-family:verdana;font-size:14px;">
		<span><b align="center" style="color:blue;font-family:verdana;font-size:15px;">For Other Queries</b><br>
		E-Mail : customercare@happybus.com <br>
		Phone No : 040 30102829</span><br>
			<span><b align="center" style="color:blue;font-family:verdana;font-size:15px;">Only For SMS & WhatsUp</b><br>		
				 (+91) 8499900900</span><br>
		</div>	
		<br>			
		</div>
		<br>
	</div>
</div>
</html>