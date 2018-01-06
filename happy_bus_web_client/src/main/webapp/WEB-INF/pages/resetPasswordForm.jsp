<!DOCTYPE html>
<html ng-app="resetpassword.controllers">
<%@include file="left_right.jsp"%>
<head>
<%@include file="includers.jsp"%>
<script type="text/javascript">
angular.module('resetpassword.controllers', [])
.controller('resetpassword_controller', ['$scope', '$window','$http',function($scope,$window,$http) {
$scope.resetPassword = function() {
	$http({
	method:"post",	
	url:"/HappyBus/resetPassword",
	params:{
		
		'newpassword':$scope.newpassword,
		'confirmpassword':$scope.confirmpassword,
		'userId':$scope.userId
	}
	}).then(function(result) {
		
		$scope.response=result.data;
		$scope.flag=false;
		if($scope.response.status=="EXCEPTION" || $scope.response.status=="FAILURE"){
			$scope.message=$scope.response.message;
			$scope.newpassword="";
			$scope.confirmpassword="";
		}else{
		$window.alert("Your Password Reset SuccessFully");
		//
		$window.location.href="/HappyBus/";
		}
			
	}, function(response) {
		$window.alert("unable to process your request");
	}); 
}
$scope.flag1 = false;
$scope.flag2 = true;
$scope.closeWindow = function() {
	//redirect to home
	$window.location.href = "/HappyBus";
	document.getElementById('resetpassword_frame_div').style.display = 'none';

};
angular.element(document).ready(
				function() {
					document.getElementById('resetpassword_frame_div').style.display = 'block';

					/* var target = angular.element('login_frame_div');
					 */
				});

}]);
</script>
</head>
<div id="resetpassword_frame_div" class="hbb-modal"
	ng-controller="resetpassword_controller">
	<div class="hbb-modal-content hbb-card-4 hbb-animate-zoom"
		style="max-width: 600px">
		<div class="hbb-center">
			<br>
			<span ng-click="closeWindow()" ng-show="flag2"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <span
				onclick="document.getElementById('resetpassword_frame_div').style.display='none'"
				ng-show="flag1"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <img src="images/logontitle.png"
				alt="HappyBus logo" style="width: 50%" class=" hbb-margin-top ">
		</div>		
		<div style="color:blue; font-size: medium;" align="center">
		Please Enter Below Fields to Reset Your Password
			<br>
			{{message}}</div>
		<div class="hb-section hbb-margin ">
		<input type="hidden" ng-value="userId=${userId}" ng-model="userId"/>
			<label class="hb-label-marign"><b>NewPassword</b></label> <input
				class="hb-input hbb-border hbb-margin-bottom" type="password"
				ng-model="newpassword" required/>
		<label class="hb-label-marign"><b>ConfirmPassword</b></label> <input
				class="hb-input hbb-border hbb-margin-bottom" type="password"
				ng-model="confirmpassword" required/>
			<button
				class=" hb-label-marign hbb-button hbb-large hbb-green hb-section"
				ng-click="resetPassword()">Submit</button>
		</div>
		</form>
	</div>
</div>
</html>