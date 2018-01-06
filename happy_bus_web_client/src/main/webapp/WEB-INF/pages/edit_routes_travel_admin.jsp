<html data-ng-app="edit_traveladmin_routes.controllers">
<%@page isELIgnored="false"%>
<%@include file="travel_admin_side_menu.jsp"%>
<%@include file="admin_top_menu.jsp"%>
<head>
<script type="text/javascript" src="js/angular.js"></script>

<script type="text/javascript">
	angular.module('edit_traveladmin_routes.controllers', []).controller(
			'edit_traveladmin_routes_controller',
			[ '$scope', '$window', '$http', function($scope, $window, $http) {
				$scope.search_bus_div=true;
				$scope.getRoutes = function() {
					$scope.message = "";
					$window.alert("calling");
					$http({
						method : "post",
						url : "/HappyBus/getRoutes",
						params : {

							'routeId' : $scope.routeId,
							'travelId':$scope.travelId

						}

					}).then(function(result) {

						$scope.flag = true;
						$scope.flag = false;
						$scope.response = result.data;
						$window.alert($scope.response);
						$scope.search_bus_div=false;
						$scope.data = $scope.response.data
						$scope.data1 = angular.fromJson($scope.data);
						$window.alert($scope.data1);
						$scope.message = $scope.response.message;
						$window.alert($scope.message);
					}, function(response) {
						$window.alert("unable to process your request");
					});
				}

				$scope.editRoutes = function() {
					$scope.message = "";
					$window.alert("calling");
					$http({
						method : "post",
						url : "/HappyBus/editRoutes1",
						params : {

							'source' : $scope.source,
							'destination' : $scope.destination,
						    'price' : $scope.price,
							'routeId' : $scope.routeId,
							'travelId':$scope.travelId

						}

					}).then(function(result) {

						$scope.response = result.data;
						$window.alert($scope.response);
						$scope.data = $scope.response.data
						$scope.data1 = angular.fromJson($scope.data);
						$window.alert($scope.data1);
						$scope.message = $scope.response.message;
						$window.alert($scope.message);
					}, function(response) {
						$window.alert("unable to process your request");
					});
				}

			}

			]);
</script>
</head>
<!-- page content -->
<div class="right_col" role="main"
	data-ng-controller="edit_traveladmin_routes_controller">
	<form class="form-horizontal form-label-left" novalidate>
	<div data-ng-show="search_bus_div">

		<div class="item form-group">

			<div class="col-md-6 col-sm-6 col-xs-12">
				<label class="control-label col-md-3 col-sm-3 col-xs-12"
					for="routeId">RouteId<span class="required">*</span>
				</label> <input id="name" class="form-control col-md-7 col-xs-12"
					data-validate-length-range="6" data-validate-words="1"
					name="routeId" data-ng-model="routeId" placeholder="e.g 2052"
					required="required" type="text">
			</div>

			<div class="col-md-6 col-sm-6 col-xs-12">
				<label class="control-label col-md-3 col-sm-3 col-xs-12"
					for="travelId">Travel Id<span class="required">*</span>
				</label> <input id="name" class="form-control col-md-7 col-xs-12"
					data-validate-length-range="6" data-validate-words="1"
					name="travelId" data-ng-model="travelId" placeholder="e.g 1102"
					required="required" type="text">
			</div>


		</div>
		<div class="col-md-6 col-md-offset-3">
			<button class="btn btn-primary">Cancel</button>
			<button class="btn btn-success" data-ng-click="getRoutes()">Submit</button>
		</div>

</div>

		<div class="col-md-6 col-sm-6 col-xs-12">
			<label class="control-label col-md-3 col-sm-3 col-xs-12" for="source">Source<span
				class="required">*</span>
			</label> <input id="name" class="form-control col-md-7 col-xs-12"
				data-validate-length-range="6" data-validate-words="1" name="source"
				data-ng-model="source" placeholder="e.g hyderabad"
				required="required" type="text">
		</div>
		<div class="col-md-6 col-sm-6 col-xs-12">
			<label class="control-label col-md-3 col-sm-3 col-xs-12"
				for="destination">Destination<span class="required">*</span>
			</label> <input id="name" class="form-control col-md-7 col-xs-12"
				data-validate-length-range="6" data-validate-words="1"
				name="destination" data-ng-model="destination"
				placeholder="e.g Orissa" required="required" type="text">
		</div>
		
		<div class="col-md-6 col-sm-6 col-xs-12">
			<label class="control-label col-md-3 col-sm-3 col-xs-12" for="price">Price<span
				class="required">*</span>
			</label> <input id="name" class="form-control col-md-7 col-xs-12"
				data-validate-length-range="6" data-validate-words="1" name="price"
				data-ng-model="price" required="required" type="text">
		</div>
<div class="col-md-6 col-sm-6 col-xs-12">
			<input  id="name" class="form-control col-md-7 col-xs-12"
				data-validate-length-range="6" data-validate-words="1"
				name="travelId" data-ng-model="travelId" placeholder="e.g 7"
				required="required" type="text">
		</div>

		<div class="col-md-6 col-sm-6 col-xs-12">
			 <input type="hidden" id="name" class="form-control col-md-7 col-xs-12"
				data-validate-length-range="6" data-validate-words="1"
				name="routeId" data-ng-model="routeId" required="required"
				type="text">
		</div>
		<br> <br>
	</form>

	<div class="col-md-6 col-md-offset-3">
		<button class="btn btn-primary">Cancel</button>
		<button class="btn btn-success" data-ng-click="editRoutes()">Edit</button>
	</div>
</div>


<!-- /page content -->
<%@include file="admin_footer_menu.jsp"%>
<!-- validator -->
<!-- <script src="admin/vendors/validator/validator.js"></script> -->

</html>