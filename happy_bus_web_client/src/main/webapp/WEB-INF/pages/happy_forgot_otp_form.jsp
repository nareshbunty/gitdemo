<!DOCTYPE html>
<html ng-app="forgototp.controllers">
<%@include file="left_right.jsp"%>
<head>
<%@include file="includers.jsp"%>
<script type="text/javascript">

angular.module('forgototp.controllers', [])
.controller('forgototp_controller', ['$scope', '$window','$http',function($scope,$window,$http) {
	$scope.closeWindow=function(){
		//redirect to home
		$window.location.href="/HappyBus";

	};
	$scope.flag1=false;
	$scope.flag2=true;
	$scope.closeWindow=function(){
		//redirect to home
		$window.location.href="/HappyBus";
		document.getElementById('forgototp_frame_div').style.display='none';
	    
	};
	angular.element(document).ready(function () {
	   document.getElementById('forgototp_frame_div').style.display='block';
	    });

	$scope.checkOTP = function() {
	$http({
	method:"post",	
	url:"/HappyBus/checkOTP",
	params:{
		
		'otp':$scope.otp,
		'userId':$scope.userId
	}
	}).then(function(result) {
		
		$scope.response=result.data;
		$scope.flag=false;
		if($scope.response.status=="EXCEPTION" || $scope.response.status=="FAILURE"){
			$scope.message=$scope.response.message;
			$scope.otp="";
		}else if($scope.response.status == "SUCCESS" ){
		$window.location.href="/HappyBus/resetForm?userId="+$scope.userId;
		}
			
	}, function(response) {
		$window.alert("unable to process your request");
	}); 
}
	$scope.regenrateOTP = function() {
		$window.alert($scope.mobile);
		$window.alert( $scope.userId);
		$http({
			method : "get",
			url : "/HappyBus/regenerateOTP",
			params : {

				'mobile' : $scope.mobile,
				'userId' : $scope.userId
			}
		})
				.then(
						function(result) {

							$scope.response = result.data;
							$scope.flag = false;
							if ($scope.response.status == "EXCEPTION"
									|| $scope.response.status == "FAILURE") {
								$scope.message = $scope.response.message;
								$scope.otp = "";
							} else {
								$scope.message = $scope.response.message;
								
							}

						},
						function(response) {
							$window
									.alert("unable to process your request");
						});
	}
}]);
</script>
</head>
${mobile}
 <div id="forgototp_frame_div" class="hbb-modal"
	ng-controller="forgototp_controller">
	<div class="hbb-modal-content hbb-card-4 hbb-animate-zoom"
		style="max-width: 600px">
		<div class="hbb-center">
			<br> <span ng-click="closeWindow()" ng-show="flag2"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <span
				onclick="document.getElementById('otp_frame_div').style.display='none'"
				ng-show="flag1"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <img src="images/logontitle.png"
				alt="HappyBus logo" style="width: 50%" class=" hbb-margin-top ">
		</div>
			<div class="hb-section hbb-margin ">
<div style="color:blue; " align="center">
Otp Send To your Mobile Please enter Otp To Reset Your Password
<br>
<span style="color:red;font:medium">{{message}}</span>
</div>

			<input type="hidden" ng-value="userId=${userId}" ng-model="userId"/> <br/>
				<label class="hb-label-marign"><b>Mobile</b></label> <input
					class="hb-input hbb-border hbb-margin-bottom" type="text"
					placeholder="Mobile " name="mobile" ng-model="mobile" ng-value="mobile=${mobile}" required/>
				<label class="hb-label-marign"><b>Otp</b></label> <input
					class="hb-input hbb-border" type="text"
					placeholder="Entet otp" name="otp" ng-model="otp" required/>
				<button
					class=" hb-label-marign hbb-button hbb-large hbb-green hb-section" ng-click="checkOTP()">Submit</button>
			<button
				class=" hb-label-marign hbb-button hbb-large hbb-green hb-section"
				ng-click="regenrateOTP()">ResendOtp</button>
			</div>
	</div>
</div>

</html>