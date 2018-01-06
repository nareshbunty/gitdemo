<!DOCTYPE html>
<html ng-app="coupons.controller">
<head>
<%@page isELIgnored="false"%>
<%@include file="admin_side_menu.jsp"%>
<%@include file="admin_top_menu.jsp"%>
<%@include file="includers.jsp"%> 
<!-- validator -->
    <script src="admin/vendors/validator/validator.js"></script>
    
    <script type="text/javascript">
    
   angular.module('coupons.controller', []).controller('search_coupon_controller', ['$scope', '$window','$http',
    function($scope,$window,$http) {
	   $window.alert("calling");
	   $scope.searchCoupon=function(){
		   $window.alert("calling1");
		   
		   $http({
				method:"get",
				url:"/HappyBus/searchCoupons"
				
			}).then(function(result) {
				$scope.response=result.data;
				if( $scope.response.status=="SUCCESS"){
					$scope.message=$scope.response.message;
					$window.alert("calling");
					$window.alert($scope.message);
					$scope.coupons=angular.fromJson($scope.response.data);
				}
			},
			function(response) {
				
				$window.alert("fail");
				
				
			});
	   }//end of searchcoupon function
	   
     }]);
    
    </script>
    </head>
<!-- page content -->
<body>
<div class="right_col" role="main" ng-controller="search_coupon_controller">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h3>Coupons</h3>
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
						<h2></h2>
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
							<span class="section">Search Coupon Information</span>
							<div class="ln_solid"></div>
							<div class="form-group">
								<div class="col-md-6 col-md-offset-3">
								<button id="send" type="submit" class="btn btn-success" ng-click="searchCoupon()">Search</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

<table class="table table-striped">
<tr>
<th>CouponId</th><th>CouponCode</th><th>CouponCost</th><th>ValidFrom</th><th>ValidTo</th><th>CouponStatus</th><th>CreatedBy</th><th>CreatedDate</th>
</tr>
<tr data-ng-repeat="values in coupons">

<th scope="row">{{values.couponId}}</th>
<td>{{values.couponCode}}</td>
<td>{{values.discountAmount}}</td>
<td>{{values.validFrom}}</td>
<td>{{values.validTo}}</td>
<td>{{values.couponStatus}}</td>
<td>{{values.createdBy}}</td>
<td>{{values.createdDate}}</td>
</tr>
</table>
</div>
<!-- /page content -->
</body>

<%@include file="admin_footer_menu.jsp"%>

    </html>