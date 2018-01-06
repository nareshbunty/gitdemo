<html ng-app="search_routes.controllers">
<%@page isELIgnored="false"%>
<%@include file="admin_side_menu.jsp"%>
<%@include file="admin_top_menu.jsp"%>
<head>
<script type="text/javascript" src="js/angular.js"></script>

<script type="text/javascript">
angular.module('search_routes.controllers', []).controller('search_routes_controller', ['$scope', '$window','$http',
	function($scope,$window,$http) {
	$scope.searchRoutes = function() {
	$scope.message="";
	$window.alert("calling");
	$http({
	method:"post",	
	url:"/HappyBus/searchRoutes1"
	
	}).then(function(result) {
	    $scope.response=result.data;
		$window.alert($scope.response);
		$scope.data=$scope.response.data
		$scope.data1=angular.fromJson($scope.data);
		$window.alert($scope.data);
		$scope.message=$scope.response.message;
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
 <div class="right_col" role="main"  ng-controller="search_routes_controller">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h3>Routes</h3>
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
		<div style="color: blue; font-size: medium;" align="center">
			{{message}}</div>
 	<div>					
		<table align="center" border="1" cellpadding="40" cellspacing="5">
            
			<th>RouteId</th>
			<th>Source</th>
			<th>Destination</th>
			<th>DateOfJourney</th>
			<th>ServiceNo</th>
			<th>TravelId</th>
			<!-- <th>TravelName</th> -->
			
			
			<tr data-ng-repeat="item in data1">
				<td>{{item.routeId}}</td>
				<td>{{item.source}}</td>
				<td>{{item.destination}}</td>
				<td>{{item.dateOfJourney}}</td>
				<td>{{item.serviceNo}}</td>
				<td>{{item.travelId}}</td>
				<!-- <td>{{item.travelName}}</td> -->
			
			</tr>
		</table>
		
	                    </div >
	 
									<button class="btn btn-primary">Cancel</button>
									<button class="btn btn-success" ng-click="searchRoutes()">SearchRoutes</button>
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
