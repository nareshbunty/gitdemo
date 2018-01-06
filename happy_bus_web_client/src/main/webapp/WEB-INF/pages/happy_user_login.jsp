<%@page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="login.controllers">
<head>
<%@include file="includers.jsp"%>

<script type="text/javascript">
angular.module('login.controllers', [])
.controller('login_controller', ['$scope', '$window','$http',function($scope,$window,$http) {
	$scope.flag1=false;
	$scope.flag2=true;
	$scope.closeWindow=function(){
		//redirect to home
		$window.location.href="/HappyBus";
		document.getElementById('login_frame_div').style.display='none';
	    
	};
	angular.element(document).ready(function () {
    document.getElementById('login_frame_div').style.display='block';
    
		/* var target = angular.element('login_frame_div');
   */
    });

}]);
</script>
</head>


<div id="login_frame_div" class="hbb-modal"
	ng-controller="login_controller">
	<div class="hbb-modal-content hbb-card-4 hbb-animate-zoom"
		style="max-width: 600px">

		<div class="hbb-center">
			<br> <span ng-click="closeWindow()" ng-show="flag2"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <span
				onclick="document.getElementById('login_frame_div').style.display='none'"
				ng-show="flag1"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <img src="images/logontitle.png"
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
</html> 