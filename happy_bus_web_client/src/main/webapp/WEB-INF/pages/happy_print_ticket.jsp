<%@page isELIgnored="false"%>
<html ng-app="printticket.controllers">
<head>
<%@include file="includers.jsp"%>
<script type="text/javascript" src="js/angular-datepicker.js"></script>
<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="js/jquery.redirect.js"></script>
<link href="css/angular-datepicker.css" rel="stylesheet" type="text/css" ></link>
<script type="text/javascript">
	angular
			.module('printticket.controllers', [])
			.controller(
					'printticket_controller',
					[
							'$scope',
							'$window',
							'$http',
							function($scope, $window, $http) {

								$scope.omm = false;
								$scope.but_flg = false;
								$scope.hb_img = false;

								$scope.print = function(printSectionId) {

									$scope.hb_img = true;

									var innerContents = document
											.getElementById(printSectionId).innerHTML;
									var popupWinindow = window
											.open(
													'',
													'_blank',
													'width=600,height=700,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
									popupWinindow.document.open();
									popupWinindow.document
											.write('<html><head><link rel="stylesheet" type="text/css" href="style.css" /></head><body onload="window.print()">'
													+ innerContents + '</html>');
									popupWinindow.document.close();

									$scope.hb_img = false;
									//window.print();
								}

								
								
								$scope.printTicket = function() {
									if ($scope.bookingid == null) {
										$scope.message = "Please Enter BookingId.";
										if ($scope.mobile == null) {
											$scope.message = "Please Enter Mobile.";
										}

									} else {
										$http({
											method : "post",
											url : "/HappyBus/printTicket",
											params : {
												'mobile' : $scope.mobile,
												'bookingid' : $scope.bookingid,
											}
										})
												.then(
														function(result) {
															$scope.response = result.data;
															if ($scope.response.status == "EXCEPTION"
																	|| $scope.response.status == "FAILURE") {
																$scope.message = $scope.response.message;
															} else {
																$scope.message = $scope.response.message;

																//	$window.alert($scope.response.dataList);
																//$window.alert($scope.response.dataList[0]);

																//$window.alert($scope.response.dataList[0].email);

																//$scope.userDetails=$scope.response.dataList[0];

																$scope.userDetails = angular
																		.fromJson($scope.response.dataList[0]);
																$scope.boardingDetails = angular
																		.fromJson($scope.response.dataList[1]);

																$scope.omm = true;

																$scope.but_flg = true;

																//$window.alert($scope.boardingDetails);
																//$window.alert($scope.boardingDetails.bookingId);

																//$window.alert($scope.boardingDetails.passengerList);
																$scope.passengerDetails=$scope.boardingDetails.passengerList;
															}

														},
														function(response) {
															$window
																	.alert("unable to process your request");
														});
									}
								}//printticketclose
					
								
								/* 	This logic is used for registration of sign up */
								$scope.checkUser= function() {
									if($scope.email==null){
									$scope.reg_user_message="Please Enter Email."
									}
									else {			
										if( $scope.mobile==null){
											$scope.reg_user_message="Please Enter Mobile."
											}
										else{				
											$http({
												method:"get",
												url:"/HappyBus/checkUser",
												params:{
													"email":$scope.email,
													"mobile":$scope.mobile
												}
											}).then(function(result) {
												$scope.response=result.data;
												if($scope.response.status=="EXCEPTION" || $scope.response.status=="FAILURE"){
													$scope.reg_user_message=$scope.response.message;
												}
											},
											function(response) {					
												
											}); 
										}
									}		
								}
								
								$scope.registerPassengerDetails = function() {
									$window.alert("calling");
									if($scope.email==null){
										$scope.reg_user_message="Please Enter Email."
										}
									else if($scope.mobile==null){
										$scope.reg_user_message="Please Enter Mobile."
										}
									else if($scope.password==null){
										$scope.reg_user_message="Please Enter Password."
										} 
									else{
											$scope.reg_user_message="";
											$http({
										method:"post",	
										url:"/HappyBus/userRegistration",
										params:{
											'email':$scope.email,
											'mobile':$scope.mobile,
											'password':$scope.password
										}
										}).then(function(result) {
									
											$scope.response=result.data;
											$scope.flag=false;
											if($scope.response.status=="EXCEPTION" || $scope.response.status=="FAILURE"){
												$scope.reg_user_message=$scope.response.message;
											}else{
													$scope.flag=true;
												$scope.data=$scope.response.data;
												$scope.mobile=angular.fromJson($scope.data).mobile;
									    		 $scope.userId=angular.fromJson($scope.data).userId;
												//redirect to check OTP
											$window.location.href="/HappyBus/otpForm?userId="+$scope.userId+"&mobile="+$scope.mobile;
										}
										
										}, function(response) {
											$window.alert("unable to process your request");
									}); 
								}
							}


							/* 	logic ends for registration of sign up */
								
	} ]);
</script>
</head>
<div ng-controller="printticket_controller"  class="hbb-container hbb-card ">


<!-- 
	<img class="hb-ddown" style="float: left;" src="images/logontitle.png"
		width="500px" height="150px"> -->
		
<!-- header is here -->
<div>
	<img class="hb-ddown" style="float: left;" src="images/logontitle.png" width="500px" height="200px">
<div class="hb-ddown" style="float: right;">

    <br><br><br><br><br><br><br>
    <button onclick="document.getElementById('login_frame_div').style.display='block'" 
    class="hb-margin-btn hbb-btn  hbb-blue  hbb-large ">Sign In</button>
    
    <button onclick="document.getElementById('id02').style.display='block'" 
    class="hb-margin-btn hbb-btn  hbb-blue  hbb-large ">Sign Up</button>
    
   <!--  <a href="userRegistration1" class="hb-margin-btn hbb-btn hbb-blue hbb-large ">SignUp</a> -->
</div>	
</div> 
<!-- header is here -->	
<div><%@include file="happy_menu.jsp"%></div>


	
	<div class="hbb-container hbb-card ">
	<div style="color:blue; font-size:20px;" align="center">
		{{message}}</div>

	<div align="center" class="hbb-container" ng-hide="omm">
		<form>
			<div class="hb-section hbb-margin ">
				<label class="hb-label-marign"><b>Mobile Number</b></label> <input
					class="hb-inputtype hbb-border" type="text" ng-model="mobile"
					required /> <label class="hb-label-marign"><b>Booking
						Id</b></label> <input class="hb-inputtype hbb-border" type="text"
					ng-model="bookingid" required />
			</div>
		</form>
	<div align="center">
		<button
			class=" hb-label-marign hbb-button hbb-large hbb-green hb-section"
			ng-click="printTicket()">Show Details</button>
	</div>
	</div>
	<div id="hbdiv">
		<div align="center" ng-show="hb_img">
			<img align="center" class="hb-ddown" style="float: left;"
				src="images/logontitle.png" width="550px" height="180px">
				<br><br><br><br><br><br><br><br><br><br><br><br><br>
		</div>	
		<div align="center" ng-show="omm">
			<br>
			<div align="center">	
			<br>
			<h2 align="center" ><b>Your Ticket Details</b></h2>			
				<table border="1" cellpadding="3" cellspacing="3" align="center">
					<thead>
						<tr>
							<th>Email Id</th>
							<th>Mobile</th>
							<th>Gender</th>
							<th>Address</th>
							<th>Pin Code</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>{{userDetails.email}}</td>
							<td>{{userDetails.mobile}}</td>
							<td>{{userDetails.gender}}</td>
							<td>{{userDetails.address}}</td>
							<td>{{userDetails.pincode}}</td>
						</tr>
					</tbody>
				</table>

				<h3 align="center">
					<b>Booking Details </b>
				</h3>
				<table border="1" cellpadding="5" cellspacing="3" align="center">
					<thead>
						<tr>
							<th>Booking Id</th>
							<th>Booking Date</th>
							<th>No. of Passengers</th>
							<th>Total Fare</th>
							<th>Date of Journey</th>
							<th>Boarding Point</th>
							<th>Dropping Point</th>

						</tr>
					</thead>
					<tbody>
						<tr>
							<td>{{boardingDetails.bookingId}}</td>
							<td>{{boardingDetails.bookingDate}}</td>
							<td>{{boardingDetails.noOfPassengers}}</td>
							<td>{{boardingDetails.finalFare}}</td>
							<td>{{boardingDetails.dateOfJourney}}</td>
							<td>{{boardingDetails.boardingPoint}}</td>
							<td>{{boardingDetails.droppingPoint}}</td>

						</tr>
					</tbody>
				</table>


				<h3 align="center">
					<b>List of Passenger Details </b>
				</h3>
				<table border="1" cellpadding="5" cellspacing="3" align="center">
					<thead>
						<tr>
							<th>Seat No.</th>
							<th>Passenger Name</th>
							<th>Gender</th>
							<th>Age</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="p in passengerDetails">
							<td>{{p.seatno}}</td>
							<td>{{p.name}}</td>
							<td>{{p.gender}}</td>
							<td>{{p.age}}</td>


						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<div align="center" ng-show="but_flg">
		<button
			class=" hb-label-marign hbb-button hbb-large hbb-green hb-section"
			ng-click="print('hbdiv')">Print Ticket</button>
	</div>
	
	</div>
	
	
	
<!-- login div frame popup -->
<div id="login_frame_div" class="hbb-modal">
	<div class="hbb-modal-content hbb-card-4 hbb-animate-zoom" style="max-width: 600px">

		<div class="hbb-center">
			<br> <span onclick="document.getElementById('login_frame_div').style.display='none'" 
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <!-- <span
				onclick="document.getElementById('login_frame_div').style.display='none'"
				ng-show="flag1"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> --> <img src="images/logontitle.png"
				alt="HappyBus logo" style="width: 50%" class=" hbb-margin-top ">
		</div>
		<div align="center">${status}</div>
		<form class="hbb-container" id="user" action="loginUser" method="post">
			<div class="hb-section hbb-margin ">
				<label class="hb-label-marign"><b>Email </b></label> <input
					class="hb-input hbb-border hbb-margin-bottom" type="text"
					placeholder="Enter Email " name="userName" id="userName" required>
				<label class="hb-label-marign"><b>Password</b></label> <input
					class="hb-input hbb-border" type="password"
					placeholder="Enter Password" name="password" id="password" required>
				<input class="hbb-left hb-label-marign hb-checkbox hbb-margin-top"
					type="checkbox" checked="checked"><br>Remember Me <span
					class="hb-mar-left"><a href="forgotForm">Forgot
						Password.?</a> </span> <br>
				<button
					class=" hb-label-marign hbb-button hbb-large hbb-green hb-section"
					type="submit">Login</button>
			</div>
		</form>
	</div>
</div>
<!-- login div close frame popup -->

<!-- SignUp div frame popup -->

<div id="id02" 
	class="hbb-modal" >
<div class="hbb-modal-content hbb-card-4 hbb-animate-zoom" style="max-width: 600px">
<div class="hbb-center">
<br> 
<!-- ng-click="closeWindow()" ng-show="flag2" -->
<span 
onclick="document.getElementById('id02').style.display='none'"
	class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
	title="Close Modal">×</span> 
<img src="images/logontitle.png"
				alt="HappyBus logo" style="width: 50%" class=" hbb-margin-top ">
</div>
<div style="color: red; font-size: medium;" align="center">
			{{reg_user_message}}</div>
<form class="hbb-container">
		<div class="hb-section hbb-margin ">
			<label class="hb-label-marign"><b>Email Id</b></label> 
			 <input class="hb-input hbb-border hbb-margin-bottom" type="text"
					ng-model="email" ng-blur="checkUser()"
					placeholder="Enter Email / Mobile" name="email" required/> 
					<label
					class="hb-label-marign"><b>Mobile Number</b></label> 
					
					<input
					class="hb-input hbb-border hbb-margin-bottom" type="text"
					ng-model="mobile" ng-blur="checkUser()"
					placeholder="Mobile Number" name="mobile" required/> <label
					class="hb-label-marign"><b>Password</b></label> <input
					class="hb-input hbb-border" type="password" ng-model="password"
					placeholder="Enter Password" name="psw" required/>
<br>
<button class=" hb-label-marign hbb-button hbb-large hbb-green hb-section"
					ng-click="registerPassengerDetails()">Register</button>

</div>
</form>
</div>
</div>
<!-- signup div close frame popup -->

<div class="hbb-card hb-mar-toponeper">
    <div class="">
        <img src="images/Banner.png" width="100%" height="130px">
    </div>
</div>
</div>







</html>