<!DOCTYPE html>
<html ng-app="forgot.controllers">
<%@include file="left_right.jsp"%>
<head>
<%@include file="includers.jsp"%>
<script type="text/javascript">
	angular
			.module('forgot.controllers', [])
			.controller(
					'forgot_controller',['$scope','$window','$http',	function($scope, $window, $http) {
								$scope.forgotPassword = function() {
									$http({
										method : "post",
										url : "/HappyBus/forgotPassword",
										params : {
											'email' : $scope.email,
										}
									}).then(	function(result) {
														$scope.response = result.data;
														$scope.flag = false;
														if ($scope.response.status == "EXCEPTION"
																|| $scope.response.status == "FAILURE") {
															$scope.message = $scope.response.message;
															$scope.email = "";
														}else if($scope.response.status == "SUCCESS"){
															$scope.message = $scope.response.message;	
															$scope.flag=true;
														$scope.data=$scope.response.data;															
														$scope.mobile=angular.fromJson($scope.data).mobile;
												     	$scope.userId=angular.fromJson($scope.data).userId;
																if($scope.mobile&&$scope.userId){													
													//redirect to forgototpform
													$window.location.href="/HappyBus/forgototpform?userId="+$scope.userId+"&mobile="+$scope.mobile;
																}
																
														}
																},function(response) {
														$window.alert("unable to process your request");
													});
								}
								$scope.flag1 = false;
								$scope.flag2 = true;
								$scope.closeWindow = function() {
									//redirect to home
									$window.location.href = "/HappyBus";
									document.getElementById('forgot_frame_div').style.display = 'none';

								};
								angular.element(document).ready(
												function() {
													document.getElementById('forgot_frame_div').style.display = 'block';

													/* var target = angular.element('login_frame_div');
													 */
												});
					
					} ]);
</script>
</head>
<div id="forgot_frame_div" class="hbb-modal"
	ng-controller="forgot_controller">
	<div class="hbb-modal-content hbb-card-4 hbb-animate-zoom"
		style="max-width: 600px">
		<div class="hbb-center">
			<br>
			<span ng-click="closeWindow()" ng-show="flag2"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <span
				onclick="document.getElementById('forgot_frame_div').style.display='none'"
				ng-show="flag1"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <img src="images/logontitle.png"
				alt="HappyBus logo" style="width: 50%" class=" hbb-margin-top ">
		</div>
		<div style="color:green; font-size: medium;" align="center">
			Please Enter Mobile or Email
			<br>
			{{message}}</div>
		<div class="hb-section hbb-margin ">
			<label class="hb-label-marign"><b>Email/Mobile</b></label> <input
				class="hb-input hbb-border hbb-margin-bottom" type="text"
				ng-model="email" ng-pattern="/^([_a-z0-9]+(\.[_a-z0-9]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,5}))|\d+$/" placeholder="Enter Email/Mobile " name="userName"
				id="userName" required>
			<button
				class=" hb-label-marign hbb-button hbb-large hbb-green hb-section"
				ng-click="forgotPassword()">Submit</button>
		</div>
		</form>
	</div>
</div>
</html>