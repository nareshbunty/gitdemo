
<%@page isELIgnored="false"%>
<html ng-app="search_bus.controllers1">
<head>
<%@include file="includers.jsp"%> 

<script type="text/javascript">
var myApp=angular.module('search_bus.controllers1', []);
myApp.controller('search_bus_controller1', ['$scope', '$window','$http',
	function($scope,$window,$http) {
	$window.alert("calling");
	$scope.searchBus = function() {
		$window.alert("calling");
		$window.alert($scope.dateOfJourney);
	$scope.message="hello";
	$http({
	method:"post",	
	url:"/HappyBus/searchBus",
	params:{
		"source":$scope.source,
		"destination":$scope.destination,
		"date_of_journey":$scope.dateOfJourney
	}
	}).then(function(result) {
		
		
		$scope.response=result.data;
		$window.alert($scope.response);
		$scope.data=$scope.response.data
		
		$window.alert($scope.data);
		
	}, function(response) {
		$window.alert("unable to process your request");
	}); 
			}
}



]);
</script>
</head>


<!-- page content -->
<!--  <div class="right_col" role="main"  >
	
		<div class="page-title">
			<div class="title_left">
			
			</div> -->
			
			<div ng-controller="search_bus_controller1">
			<div class="hb-section hbb-margin " >
				<label class="hb-label-marign"><b>Source : </b></label> 
				<div>{{message}}</div>
				<input class="hb-input hbb-border hbb-margin-bottom" type="text"
					ng-model="source" 
					placeholder="Enter Source" required/> 
					<label
					class="hb-label-marign"><b>Destination : </b></label> 
					
					<input
					class="hb-input hbb-border hbb-margin-bottom" type="text"
					ng-model="destination"
					placeholder=" Enter Destination"  required/> 
					
					<label
					class="hb-label-marign"><b>Date Of Journey : </b></label> 
					<datepicker>
					<input
					 type="date" ng-model="dateOfJourney" />
					</datepicker>
				<label
					class="hb-label-marign"><b>Return  Journey : </b></label> <input
					class="hb-input hbb-border" type="text" ng-model="returnJourney"
					required/>
				<br>
				<button
					class=" hb-label-marign hbb-button hbb-large hbb-green hb-section"
					ng-click="searchBus()">Search</button>



			</div>
			
			</div>

					<!-- </div> -->
		<!-- <div class="clearfix"></div>

		<div class="row">
		<div style="font: medium;" align="center">
{{message}}
</div> -->
 	
 	
<!-- /page content -->
<%-- <%@include file="admin_footer_menu.jsp"%> --%>

 </html>
