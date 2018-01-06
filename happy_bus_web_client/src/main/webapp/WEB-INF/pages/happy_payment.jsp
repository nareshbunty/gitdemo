<html ng-app="myApp">
<head>
<%@include file="includers.jsp"%> 
<script type="text/javascript">
var app = angular.module("myApp", []);

app.controller("payment_controller", ['$scope', '$window','$http',function($scope,$window,$http) {

	
	$scope.message="";
	$scope.payment =function()
	{
			$http({
			method:"get",
			url:"/HappyBus/updateBooking",
			params:{
				'bookingId':$scope.bookingId,
				'cardno':$scope.cardno,
				'nameOnCard':$scope.name,
				'amount':$scope.finalFare
			}
		}).then(function(result) {
			$scope.response=result.data;
			if($scope.response.status=="EXCEPTION" || $scope.response.status=="FAILURE"){
				$scope.message=$scope.response.message;
			}
			else
				{
				$scope.message=$scope.response.message;
				$window.alert($scope.message);
				$window.location.href="/HappyBus";
				}
		},
		function(response) {
			$window.alert("unable to process your request");
			
		}); 
	
	
	
	}
	
	
}]);
</script>
</head>
<div ng-controller="payment_controller">
<img class="hb-ddown" style="float: left;" src="images/logontitle.png"
		width="500px" height="150px">
<div><%@include file="happy_menu.jsp"%></div>
<div style="color: red; font-size: medium;" align="center">
			{{message}}</div> 
			
<div align="center" class="hbb-container">
<form>
<div class="hb-section hbb-margin ">
<label class="hb-label-marign" style="color: blue; font-family: verdana; font-size: 18px"><b>Enter Your Credit /Debit Card Details</b></label><br> 
<input type="hidden" ng-value="bookingId=${bookingId}" ng-model="bookingId" />
<input type="hidden" ng-value="finalFare=${finalFare}" ng-model="finalFare" />
<label class="hb-label-marign"><b>Card No.</b></label> 
<input class="hb-inputtype hbb-border hbb-margin-bottom" type="text" ng-model="cardno"
					placeholder="Enter Card No" required/> 
<label class="hb-label-marign"><b>Expiry Date</b></label> </b></label> 
<input class="hb-inputtype hbb-border hbb-margin-bottom" type="text" ng-model="expdate"
					placeholder="Enter ExpiryDate" required/> 		
<label class="hb-label-marign"><b>CVV</b></label> </b></label> 
<input class="hb-inputtype hbb-border hbb-margin-bottom" type="text" ng-model="cvv"
					placeholder="Enter CVV" required/> 					
<label class="hb-label-marign"><b>Name on Card</b></label> </b></label> 
<input class="hb-inputtype hbb-border hbb-margin-bottom" type="text" ng-model="name"
					placeholder="Enter Name" required/> 										
<input type="checkbox" ng-model="flag"/> I Agree to Terms & Conditions
</div>
</form>
</div>
<div align="center">
	<button	class=" hb-margin-btn hbb-btn hbb-blue hbb-large" ng-disabled="!flag"
				ng-click="payment()">Proceed</button>
</div>

</div>

<div class="hbb-card hb-mar-toponeper">
  <img src="images/Banner.png" width="100%" height="120px">
  </div>
</html>