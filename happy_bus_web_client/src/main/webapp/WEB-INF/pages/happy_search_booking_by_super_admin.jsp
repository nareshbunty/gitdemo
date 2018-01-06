<!DOCTYPE html>

<html ng-app="booking.controllers">
<%@page isELIgnored="false"%>
<%@include file="admin_side_menu.jsp"%>
<%@include file="admin_top_menu.jsp"%>

<head>
<%@include file="includers.jsp"%>
<script type="text/javascript">
	angular.module('booking.controllers', []).controller('booking_controller',
			['$scope','$http','$window',function($scope, $http, $window) {
	$scope.searchBookingForSuperAdmin = function() {
				
$http(
{
	method : "post",
	url : "/HappyBus/searchBooking1",
	params : {
		'bookingId' : $scope.bookingId,
		'busRegNo' : $scope.busRegNo,
		'startBookingDate' : $scope.startBookingDate,
		'endBookingDate' : $scope.endBookingDate
		}
	})
.then(
	function(result) {
		$scope.response = result.data;
		$scope.data=$scope.response.data;
		$scope.data1=angular.fromJson($scope.data);
		
			
	},
	function(response) {
		$window.alert("unable to process your request from happy_search_booking_by_super_admin");
	});
  }
} ]);
</script>
</head>

<!-- page content -->
<div class="right_col" role="main" ng-controller="booking_controller">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h3>Bookings</h3>
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
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_title">
						<h2>Booking History</h2>
						<ul class="nav navbar-right panel_toolbox">
							<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
							</li>
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown" role="button" aria-expanded="false"></a>
							</li>
						</ul>
						<div class="clearfix"></div>
					</div>
					<div class="x_content">

						<form class="form-horizontal form-label-left" novalidate>

							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="telephone">BookingID </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input type="text" id="bookingId" name="bookingId"
										ng-model="bookingId" placeholder="e.g 1234"
										data-inputmask="'mask' : '1234'"
										class="form-control col-md-7 col-xs-12">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="telephone">BusRegNo </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input type="text" id="busRegNo" name="busRegNo"
										ng-model="busRegNo" placeholder="e.g OD 02 SN 2068"
										data-inputmask="'mask' : '1234'"
										class="form-control col-md-7 col-xs-12">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="telephone">StartBookingDate </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input type="text" id="startBookingDate"
										name="startBookingDate" ng-model="startBookingDate"
										placeholder="e.g 2017-04-21" data-inputmask="'mask' : '1234'"
										class="form-control col-md-7 col-xs-12">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="telephone">EndBookingDate </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input type="text" id="endBookingDate" name="endBookingDate"
										ng-model="endBookingDate" placeholder="e.g 2017-04-25"
										data-inputmask="'mask' : '1234'"
										class="form-control col-md-7 col-xs-12">
								</div>
							</div>


							<div class="ln_solid"></div>
							<div class="form-group">
								<div class="col-md-6 col-md-offset-3">
									&nbsp;&nbsp;&nbsp;
									<button class="btn btn-success" ng-click="searchBookingForSuperAdmin()">Search</button>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<button class="btn btn-primary" type="reset" ng-click="reset()">Cancel</button>
								</div>
							</div>

							<br />

							<div>
								<table border="1" cellpadding="40" cellspacing="5">

									<th>BookingId</th>
									<th>BookingDate</th>
									<th>No of Passengers</th>
									<th>Journey Date</th>
									<th>Boarding Point</th>
									<th>Dropping Point</th>
									<th>BusRegNumber</th>
									<th>UserId</th>
									<tr data-ng-repeat="item in data1">
										<td>{{item.bookingId}}</td>
										<td>{{item.bookingDate}}</td>
										<td>{{item.noOfPassengers}}</td>
										<td>{{item.dateOfJourney}}</td>
										<td>{{item.boardingPoint}}</td>
										<td>{{item.droppingPoint}}</td>
										<td>{{item.bus.busRegNo}}</td>
										<td>{{item.userId}}</td>
									</tr>
								</table>
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
</html>