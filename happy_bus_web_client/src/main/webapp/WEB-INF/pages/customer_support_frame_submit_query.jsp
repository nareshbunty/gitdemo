<!DOCTYPE html>
<%@page import="com.happybus.beans.IssueType"%>
<html ng-app="submit_query.controllers">
<head>
<%@include file="includers.jsp"%>
<script type="text/javascript">
	angular
			.module('submit_query.controllers', [])
			.controller(
					'submit_query_controller',
					[
							'$scope',
							'$window',
							'$http',
							function($scope, $window, $http) {
								$scope.submitQuery = function() {
									
										$http(
												{
													method : "post",
													url : "/HappyBus/submitQuery",
													params : {
														'issueType' : $scope.selectedItem,
														'description' : $scope.description
													}
												}).then(
														function(result) {

															$scope.response = result.data;
															$scope.flag = false;

															if ($scope.response.status == "EXCEPTION"
																	|| $scope.response.status == "FAILURE") {
																$scope.message = $scope.response.message;
															} else {
																$scope.message = $scope.response.message;
																
																/*  $window
																		.alert("Your query submited sucessfully") */;
																$window.location.href = "/HappyBus/passengerDashboard"; 
															}

														},
														function(response) {
															$window
																	.alert("unable to process your request");
														});
									
								}
								angular
										.element(document)
										.ready(
												function() {
													document
															.getElementById('submit_query_div').style.display = 'block';
													
													
														$scope.data1;
														var issueTypearr=[];
														
														 $http(
																{
																	method : "post",
																	url : "/HappyBus/getIssueType",
																	
																})
																.then(
																		function(result) {

																			$scope.response = result.data;
																		
																			$scope.flag = false;
																			if ($scope.response.status == "EXCEPTION"
																					|| $scope.response.status == "FAILURE") {
																				$scope.message = $scope.response.message;
																			} else {
																				$scope.data= $scope.response.data;
																				$scope.data1=angular.fromJson($scope.data);
																				
																			}

																		},
																		function(response) {
																			$window
																					.alert("unable to process your request");
																		});
	 
														
														document.getElementById('submit_query_div').style.display = 'block';
													$scope.data1=$parse(issueType);
													
												});
								$scope.flag1 = false;
								$scope.flag2 = true;
								$scope.closeWindow = function() {
									//redirect to home
									$window.location.href = "/HappyBus/passengerDashboard";
									document.getElementById('submit_query_div').style.display = 'none';

								};

							} ]);
</script>
</head>
<div id="submit_query_div" data-ng-controller="submit_query_controller"
	class="hbb-modal">
	<div class="hbb-modal-content hbb-card-4 hbb-animate-zoom"
		style="max-width: 600px">

		<div class="hbb-center">
			<br> <span data-ng-click="closeWindow()" data-ng-show="flag2"
				onclick="document.getElementById('submit_query_div').style.display='none'"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <img src="images/logontitle.png"
				alt="HappyBus logo" style="width: 50%" class=" hbb-margin-top ">
		</div>
		
		<div style="color:blue; font-size: medium;" align="center">
					{{message}}</div><br>
			<div class="hb-section hbb-margin ">
			select issue type:
			<!-- <p>selected item is : {{selectedItem}}</p> -->
					<select data-ng-model="selectedItem">
         <option  data-ng-repeat="item in data1"  value="{{item.issueTypeId}}"> {{item.issueTypeName}}</option>
                   </select> 

			</div>

     	<label class="hb-label-marign"><b>Description</b></label>
			<textarea rows="5" cols="50"
				class="hb-input hbb-border hbb-margin-bottom" data-ng-model="description"></textarea>
			<button
				class=" hb-label-marign hbb-button hbb-large hbb-green hb-section"
				data-ng-click="submitQuery()">Submit</button>
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
