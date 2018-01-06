<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html ng-app="BookingInfo">
<head>
<%@include file="includers.jsp"%>
<script
	src="//ajax.googleapis.com/ajax/libs/angularjs/1.3.8/angular-sanitize.js"></script>
<script type="text/javascript" src="js/checklist-model.js"></script>
<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="js/jquery.redirect.js"></script>

<!-- <script type="text/javascript" src="js/angular-ui-router.js"></script>
 -->
 
<script type="text/javascript">

//This Module From angular to work with Different Functions

var app = angular.module("BookingInfo", ["checklist-model","ngSanitize"]);

app.controller("BookingInfo", ['$scope', '$window','$http',function($scope,$window,$http) {

	$scope.makePay =function()
	{
		$scope.bookId=${booking.bookingId};	
		$scope.finalfare=${booking.finalFare};
		$window.location.href = "/HappyBus/paymentGateway?bookingId="+$scope.bookId+"&finalFare="+$scope.finalfare;
	}
	
	
}]);
</script>
</head>
<body class="hbb-container" ng-controller="BookingInfo">

	<img class="hb-ddown" style="float: left;" src="images/logontitle.png"
		width="500px" height="150px">
<div>
		<%@include file="happy_menu.jsp"%>
	</div>
<div class="hbb-container hbb-card ">
<h2 align="center"> <b>Booking Details</b> </h2>

<table border="1" cellpadding="5" cellspacing="3" align="center" >
<tr>
<td>Booking Id : ${booking.bookingId} </td><td> Date of Journey : ${booking.dateOfJourney}</td>     
<td>No. of Passengers : ${booking.noOfPassengers}</td>
</tr>
<tr>
<td>Boarding Point : ${booking.boardingPoint} </td>
<td>Dropping Point : ${booking.droppingPoint}</td>     
<td>Price : ${booking.finalFare}</td>
</tr>
</table>
<br>
<h3 align="center"> <b> Passenger Details </b></h3>
<table border="1" cellpadding="5" cellspacing="3" align="center">
<thead>
<tr>
<th>Passenger Name</th>
<th>Seat No.</th>
<th>Gender</th>
<th>Age</th>
</tr>
</thead>
<tbody>
<c:forEach items="${passengerList}" var="passenger">
<tr>
    <td> ${passenger.name}</td>
    <td>${passenger.seatno}</td>
      <td>${passenger.gender}</td>
      <td> ${passenger.age} </td>
      </tr>
</c:forEach>


</tbody>
</table>
<br>

<div align="center">
	<button	class=" hb-margin-btn hbb-btn hbb-blue hbb-large"
				ng-click="makePay()">Make Payment</button><br><br>
</div>
</div>
<div class="hbb-card hb-mar-toponeper">    
        <img src="images/Banner.png" width="100%" height="120px">   
</body>
</html>