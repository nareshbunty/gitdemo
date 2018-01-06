<!DOCTYPE html>
<html ng-app="passenger.controllers">
<%@include file="left_right.jsp"%>
<head>
<%@include file="includers.jsp"%> 

<script type="text/javascript">
angular.module('passenger.controllers', []).controller('passenger_register_controller', ['$scope', '$window','$http',
	function($scope,$window,$http) {
	$scope.checkUserName = function() {
		if($scope.email==null){
		$scope.message="Please Enter Email."
		}
		else {
			
			if( $scope.mobile==null){
				$scope.message="Please Enter Mobile."
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
						$scope.message=$scope.response.message;
					}
				},
				function(response) {
					
					
				}); 
			}
		}
		
	}
	$scope.registerPassengerDetails = function() {
		if($scope.email==null){
			$scope.message="Please Enter Email."
			}
		else if($scope.mobile==null){
			$scope.message="Please Enter Mobile."
			}
		else if($scope.password==null){
			$scope.message="Please Enter Password."
			} else{
				$scope.message="";
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
			$scope.message=$scope.response.message;
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
	angular.element(document).ready(function () {
	    document.getElementById('id02').style.display='block';
	    
			/* var target = angular.element('login_frame_div');
	   */
	    });
	$scope.flag1=false;
	$scope.flag2=true;
	$scope.closeWindow=function(){
		//redirect to home
		$window.location.href="/HappyBus";
		document.getElementById('id02').style.display='none';
	    
	};


}]);
</script>
</head>
<div id="id02" ng-controller="passenger_register_controller"
	class="hbb-modal">
	<div class="hbb-modal-content hbb-card-4 hbb-animate-zoom"
		style="max-width: 600px">

		<div class="hbb-center">
			<br> 
			<span ng-click="closeWindow()" ng-show="flag2"
				onclick="document.getElementById('id02').style.display='none'"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> 
				<img src="images/logontitle.png"
				alt="HappyBus logo" style="width: 50%" class=" hbb-margin-top ">
		</div>
		<div style="color: red; font-size: medium;" align="center">
			{{message}}</div>
		<form class="hbb-container">
			<div class="hb-section hbb-margin ">
				<label class="hb-label-marign"><b>Email Id</b></label> 
				
				<input class="hb-input hbb-border hbb-margin-bottom" type="text"
					ng-model="email" ng-blur="checkUserName()"
					placeholder="Enter Email / Mobile" name="email" required/> 
					<label
					class="hb-label-marign"><b>Mobile Number</b></label> 
					
					<input
					class="hb-input hbb-border hbb-margin-bottom" type="text"
					ng-model="mobile" ng-blur="checkUserName()"
					placeholder="Mobile Number" name="mobile" required/> <label
					class="hb-label-marign"><b>Password</b></label> <input
					class="hb-input hbb-border" type="password" ng-model="password"
					placeholder="Enter Password" name="psw" required/>
				<!-- <label class="hb-label-marign"><b>Confirm Password</b></label>
                <input class="hb-input hbb-border" type="password" ng-model="password" placeholder="Confirm Password" name="psw" required>
 -->
				<!-- 
                <input class="hbb-left hb-label-marign hb-checkbox hbb-margin-top" type="checkbox"
                       checked="checked"><br>Remember Me
                <span class="hb-mar-left"><a href="#">Forgot Password.?</a> </span>
 -->
				<br>
				<button
					class=" hb-label-marign hbb-button hbb-large hbb-green hb-section"
					ng-click="registerPassengerDetails()">Register</button>

			</div>
		</form>
	</div>
</div>

</html>
