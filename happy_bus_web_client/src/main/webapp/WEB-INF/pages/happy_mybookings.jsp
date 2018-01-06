<!DOCTYPE html>
<html ng-app="mybookings.controllers">
<head>
<%@include file="includers.jsp"%>
<script type="text/javascript">
	angular
			.module('mybookings.controllers', [])
			.controller(
					'mybookings_controller',
					[
							'$scope',
							'$window',
							'$http',
							function($scope, $window, $http) {
																
								
								angular
										.element(document)	.ready(
												function() {
													document
													.getElementById('mybookings_div').style.display = 'block';
													$http({
															method : "post", 
															url : "/HappyBus/mybookings",
															})
																.then(
																		function(result) {
																			$scope.response=result.data;
																			$scope.flag = false;	
																			if ($scope.response.status == "EXCEPTION" || $scope.response.status == "FAILURE") {
																				$scope.message = $scope.response.message;
																			} else {
																				//$window.alert(angular.fromJson($scope.response.data));
																				$scope.message = $scope.response.message;
																				$scope.mybooking = angular.fromJson($scope.response.data);	
																			}

																		},
																		function(response) {
																			$window
																					.alert("unable to process your request");
																		});
														
																							
												
												});
								$scope.flag1 = false;
								$scope.flag2 = true;
								$scope.closeWindow = function() {
									//redirect to home
									$window.location.href = "/HappyBus/passengerDashboard";
									document
											.getElementById('mybookings_div').style.display = 'none';

								};

							} ]);
</script>
</head>
<div id="mybookings_div" ng-controller="mybookings_controller"
	class="hbb-modal">
	<div class="hbb-modal-content hbb-card-4 hbb-animate-zoom"
		style="max-width: 800px">
		<div class="hbb-center">
			<br> <span ng-click="closeWindow()" ng-show="flag2"
				onclick="document.getElementById('mybookings_div').style.display='none'"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <img src="images/logontitle.png"
				alt="HappyBus logo" style="width: 50%" class=" hbb-margin-top ">
		</div>
		<div style="color:blue; font-size: medium;" align="center">
					{{message}}</div><br>
					<div align="center">			
		<table border="1" cellpadding="5" cellspacing="3" >
			<th>BookingDate</th>
			<th>NoOfPassenger</th>
			<th>FinalFare</th>
			<th>DateOfJourney</th>
			<th>BoardingPoint</th>
			<th>DroppingPoint</th>
			
			<tr data-ng-repeat="booking in mybooking">
				<td>{{booking.bookingDate}}</td>
				<td>{{booking.noOfPassengers}}</td>
				<td>{{booking.finalFare}}</td>
				<td>{{booking.dateOfJourney}}</td>
				<td>{{booking.boardingPoint}}</td>
				<td>{{booking.droppingPoint}}</td>				
			</tr>
		</table>
		<br><br>
		</div>
	</div>
</div>
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
			<%-- <%@include file="searchBuses.jsp"%> --%>
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
