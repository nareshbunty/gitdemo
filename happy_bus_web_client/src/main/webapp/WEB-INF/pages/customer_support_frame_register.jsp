<html ng-app="customer_support_reg.controllers">
<%@page isELIgnored="false"%>
<%@include file="admin_side_menu.jsp"%>
<%@include file="admin_top_menu.jsp"%>
<head>
<script type="text/javascript" src="js/angular.js"></script>

<script type="text/javascript">
angular.module('customer_support_reg.controllers', []).controller('customer_support_register_controller', ['$scope', '$window','$http',
	function($scope,$window,$http) {
	$scope.customerSupportRegistration = function() {
	$scope.message="";
	
	$http({
	method:"post",	
	url:"/HappyBus/addCustomerSupport",
	 params:{
		 'mobile':$scope.mobile,
		 'email':$scope.email
	} 
	}).then(function(result) {
		
		$scope.response=result.data;
		$scope.message=$scope.response.message;
		//$window.alert($scope.response.message);
	}, function(response) {
		$window.alert("unable to process your request");
	}); 
			}
	
}
	
]);
</script>
</head>


<!-- page content -->
<div class="right_col" role="main"  ng-controller="customer_support_register_controller">
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
		<div style="color:blue; font-size: medium;" align="center">
{{message}}
</div>
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_title">
						<h2>Customer Support Registration</h2>
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
									for="email">Email <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input type="email" id="email" name="email" ng-model="email"
									placeholder="e.g travel@travel.co.in"
										required="required" class="form-control col-md-7 col-xs-12">
								</div>
							</div>
							
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="telephone">Mobile <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input type="tel" id="telephone" name="mobile" ng-model="mobile"
										data-validate-length-range="8,20"
										placeholder="e.g +91-99999-99999"
										data-inputmask="'mask' : '(+99) 99999-99999'"
										class="form-control col-md-7 col-xs-12">
								</div>
							</div>
							
						<div class="ln_solid"></div>
							<div class="form-group">
								<div class="col-md-6 col-md-offset-3">
									<button  class="btn btn-primary">Cancel</button>
									<button class="btn btn-success" ng-click="customerSupportRegistration()">Submit</button>
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
