<!DOCTYPE html>
<html ng-app="changepassword.controllers">
<%@page isELIgnored="false" %> 
<head>
<%@include file="includers.jsp"%>
<script type="text/javascript">
angular.module('changepassword.controllers', [])
.controller('changepassword_controller', ['$scope', '$window','$http',function($scope,$window,$http) {
$scope.changePassword = function() {
	if($scope.confirmpassword==null){
		$scope.message="Please Enter ConfirmPassword.";
	 	if($scope.newpassword==null){
			$scope.message="Please Enter NewPassword.";
		}if($scope.currentpassword==null){
			$scope.message="Please Enter CurrentPassword.";
		} 
			
		}
	else{
	$http({
	method:"post",	
	url:"/HappyBus/changePassword",
	params:{
		'currentpassword':$scope.currentpassword,
		'newpassword':$scope.newpassword,
		'confirmpassword':$scope.confirmpassword
		
	}
	}).then(function(result) {
		
		$scope.response=result.data;
		if($scope.response.status=="EXCEPTION" || $scope.response.status=="FAILURE"){
			$scope.message=$scope.response.message;
		}else{
		$window.alert("Your Password Changed SuccessFully");
		$window.location.href="/HappyBus/passengerDashboard";
		}
			
	}, function(response) {
		$window.alert("unable to process your request");
	}); 
	}
}
}]);
</script>
</head>
<div id="changepassword_frame_div" class="hbb-modal"
	ng-controller="changepassword_controller">
	<div class="hbb-modal-content hbb-card-4 hbb-animate-zoom"
		style="max-width: 600px">
		<div class="hbb-center">
			<br>
			<span ng-click="closeWindow()" 
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <span
				onclick="document.getElementById('changepassword_frame_div').style.display='none'"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <img src="images/logontitle.png"
				alt="HappyBus logo" style="width: 50%" class=" hbb-margin-top ">
		</div>		
		<div style="color:green; font-size: medium;" align="center">
		<br>
			{{message}}</div>
		<div class="hb-section hbb-margin ">
<%-- 		<input type="text" ng-model="${userId}"/><br> --%>
			<label class="hb-label-marign"><b>Hello           :</b></label><span class="hbb-border" ng-disabled="true">${userName}</span>
			
			<br><br>
			
	<%-- 	<input type="text" ng-value="userName=${userName}" ng-model="userName" value="${userName}"/>
	 --%>	<label class="hb-label-marign"><b>CurrentPassword</b></label> <input
				class="hb-input hbb-border hbb-margin-bottom" type="password"
				ng-model="currentpassword" required/>
			<label class="hb-label-marign"><b>NewPassword</b></label> <input
				class="hb-input hbb-border hbb-margin-bottom" type="password"
				ng-model="newpassword" required/>
		<label class="hb-label-marign"><b>ConfirmPassword</b></label> <input
				class="hb-input hbb-border hbb-margin-bottom" type="password"
				ng-model="confirmpassword" required/>
			<button
				class=" hb-label-marign hbb-button hbb-large hbb-green hb-section"
				ng-click="changePassword()">Change</button>
		</div>
		</form>
	</div>
</div>
</html>