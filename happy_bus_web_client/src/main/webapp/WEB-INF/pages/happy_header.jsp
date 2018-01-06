<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> --%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->

<html ng-app="hea.controllers">

<head >

<%@include file="includers.jsp"%>
<script type="text/javascript">
angular.module('hea.controllers', [])
.controller('hea_controller', ['$scope', '$window','$http',function($scope,$window,$http) {
	$scope.flag1=true;
	$scope.flag2=false;	 
}
]);
</script>
</head>

<div ng-controller="hea_controller">
<img class="hb-ddown" style="float: left;" src="images/logontitle.png" width="500px" height="200px">
<div class="hb-ddown" style="float: right;">

    <br><br><br><br><br><br><br>
    <button onclick="document.getElementById('login_frame_div').style.display='block'" class="hb-margin-btn hbb-btn  hbb-blue  hbb-large ">Sign In</button>
    <a href="userRegistration1" class="hb-margin-btn hbb-btn hbb-blue hbb-large ">SignUp</a>
	
</div>

<jsp:include page="happy_user_login.jsp" />

<%--  <%@include file="passenger_frame_register.jsp"%> 
<%@include file="happy_user_login.jsp" %> --%>


</div>

</html>