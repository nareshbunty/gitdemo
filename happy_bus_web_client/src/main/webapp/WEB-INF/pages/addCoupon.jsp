
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="coupon-controller">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- validator -->
<script src="admin/vendors/validator/validator.js"></script>
<script type="text/javascript" src="js/angular.js"></script>
<script type="text/javascript" src="js/angular-datepicker.js"></script>
<link href="css/angular-datepicker.css" rel="stylesheet" type="text/css" ></link>
<%@page isELIgnored="false"%>
<%@include file="admin_side_menu.jsp"%>
<%@include file="admin_top_menu.jsp"%>
<%@include file="includers.jsp"%> 

<script type="text/javascript">



angular.module('coupon-controller', ['720kb.datepicker']).
controller('insert_coupon_controller', ['$scope', '$window','$http',function($scope,$window,$http) {
	$scope.insertcoupon()=function(){
		$window.alert('calling insertcoupon');
		
		$http({
			method:"post",
			url:"/HappyBus/couponDetails",
			params:{
				"couponCode":$scope.cCode,
				 "discountAmount":$scope.cCost,
		         "validFrom":$scope.validfrom,
				"validTo":$scope.validto
			}
		}).then(function(result) {
			$scope.response=result.data;
			if($scope.response.status=="EXCEPTION" || $scope.response.status=="FAILURE"){
				$scope.message=$scope.response.message;
			}
		},
		function(response) {
			$scope.message=response.message;
			
			
		});
		
	}
}]); 

</script>
</head>
<body ng-controller="insert_coupon_controller">
<!-- page content -->
<div class="right_col" role="main">
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
						<h2>{{massage}}</h2>
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
							<span class="section">Enter Coupon Information</span>
							
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="name">Enter Coupon Code<span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input id="name" class="form-control col-md-7 col-xs-12"
										data-validate-length-range="6" data-validate-words="2"
										name="cCode" ng-model="cCode"
										placeholder="e.g CH10001WE" required="required"
										type="text">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="name">Enter Coupon Cost<span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input id="name" class="form-control col-md-7 col-xs-12"
										data-validate-length-range="6" data-validate-words="2"
										name="CCost" ng-model="cCost"
										placeholder="e.g 120" required="required"
										type="text">
								</div>
							</div>
							
							
							
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="validfrom">Valid From<span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
							<datepicker  date-format="yyyy-MM-dd"  >
                         <input type="text" ng-model="validfrom"  class="form-control col-md-7 col-xs-12"/>
                       </datepicker></div></div>
					
					
							  <div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="validfrom">Valid To<span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
							<datepicker  date-format="yyyy-MM-dd">
                            <input type="text" ng-model="validto" class="form-control col-md-7 col-xs-12" />
                            </datepicker></div>	</div>	
							
							
							
							<div class="ln_solid"></div>
							<div class="form-group">
								<div class="col-md-6 col-md-offset-3">
									<button type="submit" class="btn btn-primary">Cancel</button>
									<button id="send" type="submit" class="btn btn-success" ng-click="insertcoupon()">Submit</button>
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
</body>

<%@include file="admin_footer_menu.jsp"%>
</html>

  