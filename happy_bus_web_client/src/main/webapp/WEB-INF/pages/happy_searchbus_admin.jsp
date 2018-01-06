<html ng-app="search_bus_admin_controller">
<%@page isELIgnored="false"%>
<%@include file="admin_side_menu.jsp"%>
<%@include file="admin_top_menu.jsp"%>
<head>
<script type="text/javascript" src="js/angular.js"></script>

<script type="text/javascript">
	angular.module('search_bus_admin_controller', []).controller(
			'search_bus_admin_controller',
			[ '$scope', '$window', '$http', function($scope, $window, $http) {
				$scope.searchBusByAdmin = function() {
					$scope.message = "";
					$window.alert("calling");
					$http({
						method : "post",
						url : "/HappyBus/searchAllBuses",

					}).then(function(result) {

						$scope.response = result.data;

						$scope.bus = angular.fromJson($scope.response.data);

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
	ng-controller="search_bus_admin_controller">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h3>SearchBus</h3>
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
			<div style="font: medium;" align="center">{{message}}</div>
			<div>
				<table border="1" cellpadding="40" cellspacing="5">
					<th>BusRegNo</th>
					<th>BusTypeName</th>
					<th>BusModel</th>
					<th>NoOfSeats</th>
					<th>LayoutType</th>
					<th>TravelName</th>
					<th>TravelEmail</th>
					<th>travelAddress</th>


					<tr data-ng-repeat="item in bus">
						<td>{{item.busRegNo}}</td>
						<td>{{item.busType.busTypeName}}</td>
						<td>{{item.busType.busModel}}</td>
						<td>{{item.busType.noOfSeats}}</td>
						<td>{{item.busType.layoutType}}</td>
						<td>{{item.travelId.travelName}}</td>
						<td>{{item.travelId.travelEmail}}</td>
						<td>{{item.travelId.travelAddress}}</td>

					</tr>
				</table>
				<!-- <div>
		Search By Any field:<input type="text" ng-model="search.destination">
		</div> -->
			</div>

			<button class="btn btn-primary">Cancel</button>
			<button class="btn btn-success" ng-click="searchBusByAdmin()">SearchBusByAdmin</button>
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
