<!DOCTYPE html>
<html ng-app="discount.controller">
<head>
<%@page isELIgnored="false"%>
<%@include file="admin_side_menu.jsp"%>
<%@include file="admin_top_menu.jsp"%>
<%@include file="includers.jsp"%> 
<!-- validator -->
    <script src="admin/vendors/validator/validator.js"></script>
    
    <script type="text/javascript">
    
   angular.module('discount.controller', []).controller('search_discount_controller', ['$scope', '$window','$http',
    function($scope,$window,$http) {
	  // $scope.discounts=[];
	   $scope.searchdiscount=function(){
		   
		   $http({
				method:"get",
				url:"/HappyBus/searchDiscount"
				
			}).then(function(result) {
				$scope.response=result.data;
				if( $scope.response.status=="SUCCESS"){
					$scope.message=$scope.response.message;
					$window.alert("calling");
					
					$scope.discounts=angular.fromJson($scope.response.data);
					$window.alert($scope.discounts1);
					$window.alert($scope.message);		
				}
			},
			function(response) {
				$window.alert(fail);
				
				
			});
	   }//end of searchcoupon function
	   
     }]);
    
    </script>
    </head>
<!-- page content -->
<body>
<div class="right_col" role="main" ng-controller="search_discount_controller">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h3>Discounts</h3>
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
							<span class="section">Search Discounts Information</span>
							<div class="ln_solid"></div>
							<div class="form-group">
								<div class="col-md-6 col-md-offset-3">
								<button id="send" type="submit" class="btn btn-success" ng-click="searchdiscount()">Search</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	 <!--<div>{{discounts}}</div>-->
	<table class="table table-striped">
<thead>
<tr> 
<th>DiscountId</th><th>DiscountAmount</th><th>ValidFrom</th><th>ValidTo</th><th>DiscountStatus</th><th>CreatedBy</th><th>CreatedDate</th>
</tr>
<thead>
<tbody>
<tr data-ng-repeat="discount in discounts">

<th>{{discount.discountId}}</th>
<td>{{discount.discountAmount}}</td>
<td>{{discount.validFrom}}</td>
<td>{{discount.validTo}}</td>
<td>{{discount.discountStatus}}</td>
<td>{{discount.createdBy}}</td>
<td>{{discount.createdDate}}</td>
</tr>
</tbody>
</table>
</div>

<!-- /page content -->
</body>

<%@include file="admin_footer_menu.jsp"%>

    </html>