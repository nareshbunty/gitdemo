<!DOCTYPE html>
<html ng-app="customer_support_show_query.controllers">
<head>
<%@include file="includers.jsp"%>
<script type="text/javascript">
	angular
			.module('customer_support_show_query.controllers', [])
			.controller(
					'customer_show_controller',
					[
							'$scope',
							'$window',
							'$http',
							function($scope, $window, $http,services) {
										angular.element(document).ready(
												function() {
													document.getElementById('show_query_edit_div').style.display = 'block'
												
													$scope.data1;
													 
													 $http(
															{
																method : "post",
																url : "/HappyBus/showQuery",
																
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
																			
																			
																			
																			//$scope.data1=json.parse($scope.data);
																			   
																		   
																		 
																			//$window.location.href = "/HappyBus/passengerDashboard";
																		}

																	},
																	function(response) {
																		$window
																				.alert("unable to process your request");
																	});
 
													
																									});
								$scope.flag1 = false;
								$scope.flag2 = true;
								$scope.closeWindow = function() {
									//redirect to home
									$window.location.href = "/HappyBus/passengerDashboard";
									document
											.getElementById('show_query_edit_div').style.display = 'none';

								};

							} ]);
</script>
</head>
<div id="show_query_edit_div" ng-controller="customer_show_controller" class="hbb-modal">
	<div class="hbb-modal-content hbb-card-4 hbb-animate-zoom"
		style="max-width: 800px">

		<div class="hbb-center">
			<br> <span ng-click="closeWindow()" ng-show="flag2"
				onclick="document.getElementById('show_query_edit_div').style.display='none'"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <img src="images/logontitle.png"
				alt="HappyBus logo" style="width: 50%" class=" hbb-margin-top ">
		</div>
		<div style="color:blue; font-size: medium;" align="center">
					{{message}}</div><br>
		<div align="center">
		<table border="1" cellpadding="5" cellspacing="3" >
            
			<th>QueryId</th>
			<th>Query</th>
			<th>QuerySolution</th>
			
			<tr data-ng-repeat="item in data1">
				<td>{{item.queryId}}</td>
				<td>{{item.query}}</td>
				<td>{{item.querySolution}}</td>
				
			</tr>
		</table>
		</div>
		<br><br>
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
