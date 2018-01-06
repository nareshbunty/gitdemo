<html ng-app="customer_support_get_query.controllers">
<%@page isELIgnored="false"%>
<%@include file="customerSupportSideMenu.jsp"%>
<%@include file="admin_top_menu.jsp"%>
<head>
<script type="text/javascript" src="js/angular.js"></script>

<script type="text/javascript">
	angular.module('customer_support_get_query.controllers', []).controller(
			'customer_support_get_query_controller',
			[ '$scope', '$window', '$http', function($scope, $window, $http) {
		/* 		$scope.getQueries = function() {
						$window.alert("CALLING");
					$http({
						method : "post",
						url : "/HappyBus/getQuery1"
					}).then(function(result) {
						$scope.message = $scope.response.message;										
						$window.alert( $scope.message);
					}, function(response) {
						$window.alert("unable to process your request");

					});

				} */
				$scope.getQueries = function() {
					$http({
						method : "post",
						url : "/HappyBus/getQuery1",
						
						}).then(	function(result) {
							
										
							$scope.response=result.data;
							//$window.alert($scope.response);
							
							$scope.message=$scope.response.message;
							$window.alert($scope.message);
							
							$scope.data1=$scope.response.data;
							$scope.data2=angular.fromJson($scope.data1);
							
							$scope.QueryId=($scope.data2[0]);
							$scope.UserId=($scope.data2[1]);
							$scope.Query=($scope.data2[2]);
							$scope.QueryType=($scope.data2[3]);
							$scope.QueryCreatedDate=($scope.data2[4]);
												},function(response) {
										$window.alert("unable to process your request");
										//$window.alert($scope.response);
									});
				}
				$scope.updateQueryDetails = function() {
					$http({
						method : "post",
						url : "/HappyBus/updateQueryDetails",
						params : {
							'customerUserId' :$scope.UserId,
							'solutionToQuery':$scope.UpdateQueryDetails,
							'queryId':$scope.QueryId
						}
						
						}).then(	function(result) {
							
										
							$scope.response=result.data;
							//$window.alert($scope.response);
							
							$scope.message1=$scope.response.message;
							
							
							$scope.data1=$scope.response.data;
							$scope.data2=angular.fromJson($scope.data1);
						
							$scope.QueryId=($scope.data2[0]);
							$scope.UserId=($scope.data2[1]);
							$scope.Query=($scope.data2[2]);
							$scope.QueryType=($scope.data2[3]);
							$scope.QueryCreatedDate=($scope.data2[4]);
												},function(response) {
										$window.alert("unable to process your request");
										//$window.alert($scope.response);
									});
				}
			}

			]);
</script>
</head>


<!-- page content -->
<div class="right_col" role="main"
	ng-controller="customer_support_get_query_controller">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h3>Customer Support</h3>
			</div>

			<div class="title_right">
				<div
					class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
					<div class="input-group">
						<input type="text" class="form-control"
							placeholder="Search for..."> <span
							class="input-group-btn">
							<button class="btn btn-default" type="button">Go!</button>
						</span>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>

		<div class="row">
			<div style="font: large;color: green;" align="center">{{message}}</div>
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_title">
						<h2> Query Details</h2>
						<ul class="nav navbar-right panel_toolbox">
							<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
							</li>
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown" role="button" aria-expanded="false"></a>
							</li>
						</ul>
						
						<div class="clearfix"></div>
						<br>
						<div style="font: medium;color: green;"  ><b>queryId:      {{QueryId}}</b></div>
						<div  style="font: large;color: green;" align="right"><b>UserId :      {{UserId}}</b><div>
						<div  style="font: large;color: green;" align="left" ><b>Query:      {{Query}}      </b></div><br>
						<div style="font: large;color: green;" align="left"class="hb-label-marign"><b>QueryType:      {{QueryType}}</b></div><br>
						<div style="font: large;color: green;" align="left" class="hb-label-marign"><b>Query Created Date:      {{QueryCreatedDate}}</b></div>
					</div>
					<div class="x_content">

						<form class="form-horizontal form-label-left" novalidate>




							<div class="ln_solid"></div>
							<div class="form-group">
								<div class="col-md-6 col-md-offset-3">

									<button class="btn btn-success" ng-click="getQueries()">Get Query Details</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			
	<!-- Update solution to queries -->		
			<div class="x_title">
						<h2> Update Query Details</h2>
						<ul class="nav navbar-right panel_toolbox">
							<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
							</li>
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown" role="button" aria-expanded="false"></a>
							</li>
						</ul>
						
						<div class="clearfix"></div>
						<br>
						<div class="item form-group">
								
								<div class="col-md-6 col-sm-6 col-xs-12">
									<textarea rows="5" cols="80"
										 ng-model="UpdateQueryDetails"
										placeholder="solution to the query" required="required"
										style="width: 929px; height: 105px;"
										></textarea>
								</div>
							</div>
					</div>
					<div class="x_content">

						<form class="form-horizontal form-label-left" novalidate>




							<div class="ln_solid"></div>
							<div class="form-group">
								<div class="col-md-6 col-md-offset-3">

									<button class="btn btn-success" ng-click="updateQueryDetails()">UPDATE SOLUTION</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /page content -->
<%@include file="admin_footer_menu.jsp"%>
<!-- validator -->
<!-- <script src="admin/vendors/validator/validator.js"></script> -->
</html>
