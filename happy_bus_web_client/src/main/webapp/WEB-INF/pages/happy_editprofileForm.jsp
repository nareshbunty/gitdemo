<!DOCTYPE html>
<html ng-app="passenger.controllers">
<head>
<%@include file="includers.jsp"%>
<script type="text/javascript" src="js/angular-datepicker.js"></script>
<link href="css/angular-datepicker.css" rel="stylesheet" type="text/css" ></link>
<script type="text/javascript">
	angular
			.module('passenger.controllers', ['720kb.datepicker'])
			.controller(
					'passenger_edit_controller',
					[
							'$scope',
							'$window',
							'$http',
							function($scope, $window, $http) {
																
								$scope.editPassengerDetails = function() {
									$window.alert($scope.dob);
									if($scope.pincode==null){
										$scope.message="Please Enter Pincode.";
									 	if($scope.address==null){
											$scope.message="Please Enter Address.";
										}if($scope.dob==null){
											$scope.message="Please Select Date of Birth .";
										} if($scope.gender==null){
											$scope.message="Please select Gender.";
										}
											
										}
									else{
									$http({
										method : "post",
										url : "/HappyBus/updateProfile",
										params : {
											'userId' : $scope.userId,
											'dob' : $scope.dob,
											'gender' : $scope.gender,
											'address' : $scope.address,
											'pincode' : $scope.pincode
										}
									})
											.then(
													function(result) {

														$scope.response = result.data;
														$scope.flag = false;
														if ($scope.response.status == "EXCEPTION"
																|| $scope.response.status == "FAILURE") {
															$scope.message = $scope.response.message;
														} else {
															$scope.message = $scope.response.message;
															$window
																	.alert("YourProfile Updated SuccessFully");
															$window.location.href = "/HappyBus/passengerDashboard";
														}

													},
													function(response) {
														$window
																.alert("unable to process your request");
													});
									}
								}
								angular
										.element(document)
										.ready(
												function() {
													document
															.getElementById('passenger_edit_div').style.display = 'block';
												});
								$scope.flag1 = false;
								$scope.flag2 = true;
								$scope.closeWindow = function() {
									//redirect to home
									$window.location.href = "/HappyBus/passengerDashboard";
									document
											.getElementById('passenger_edit_div').style.display = 'none';

								};

							} ]);
</script>
</head>
<div id="passenger_edit_div" ng-controller="passenger_edit_controller"
	class="hbb-modal">
	<div class="hbb-modal-content hbb-card-4 hbb-animate-zoom"
		style="max-width: 600px">

		<div class="hbb-center">
			<br> <span ng-click="closeWindow()" ng-show="flag2"
				onclick="document.getElementById('passenger_edit_div').style.display='none'"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <img src="images/logontitle.png"
				alt="HappyBus logo" style="width: 50%" class=" hbb-margin-top ">
		</div>
		<div style="color: red; font-size: medium;" align="center">
			{{message}}</div>
		<form class="hbb-container">
			<div class="hb-section hbb-margin ">
				<input class="hb-input hbb-border hbb-margin-bottom" type="hidden"
					ng-value="userId=${userId}" ng-model="userId" name="mobile"
					required /> 
					<label class="hb-label-marign"><b>Email</b></label><span class="hb-input hbb-border hbb-margin-bottom">${userName}</span>
						<label class="hb-label-marign"><b>Mobile
						Number</b></label> <input class="hb-input hbb-border hbb-margin-bottom"
					type="text" ng-value="mobile=${mobile}" ng-model="mobile" name="mobile" required /> <label
					class="hb-label-marign"><b>Gender</b></label>
				<!-- <input class="hb-input hbb-border hbb-margin-bottom" type="text" 	ng-model="gender"/>  -->
				<input type="radio" ng-model="gender" value="Male" name="gender"
					ng-checked /> Male <input type="radio" ng-model="gender"
					value="Female" name="gender" /> Female<br>
				<br> <label class="hb-label-marign"><b>DateOfBirth</b></label>
				<%-- <input class="hb-input hbb-border hbb-border" type="date" id="from-datepicker" name="dob1" ng-model="dob" ng-value="dob=<%=request.getParameter("dob1")%>" /> --%>
				<datepicker date-format="yyyy-MM-dd">
					<input
					 type="text" class="hb-input hbb-border hbb-margin-bottom" ng-model="dob" />
					</datepicker>	<br>		
			 <!-- <input class="hb-input hbb-border hbb-margin-bottom" type="text"
					ng-model="dob" placeholder="YYYY-MM-DD" /> --> <label
					class="hb-label-marign"><b>Address</b></label> <input
					class="hb-input hbb-border hbb-margin-bottom" type="text"
					ng-model="address" /> <label class="hb-label-marign"><b>PinCode</b></label>
				<input class="hb-input hbb-border hbb-margin-bottom" type="text"
					ng-model="pincode" /> <br>
				<button
					class=" hb-label-marign hbb-button hbb-large hbb-green hb-section"
					ng-click="editPassengerDetails()">Submit</button>
			</div>
		</form>
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
