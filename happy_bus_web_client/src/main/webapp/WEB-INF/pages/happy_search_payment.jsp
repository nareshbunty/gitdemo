<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html ng-app="searchpayment-controllers">
<script src="admin/vendors/validator/validator.js"></script>
<script type="text/javascript" src="js/angular.js"></script>
<%@page isELIgnored="false"%>
<%@include file="admin_side_menu.jsp"%>
<%@include file="admin_top_menu.jsp"%>
<%@include file="includers.jsp"%> 
<script type="text/javascript" src="js/angular-datepicker.js"></script>
<script type="text/javascript" src="js/angular-input-date.js"></script>
<link href="css/angular-datepicker.css" rel="stylesheet" type="text/css" ></link>
<script type="text/javascript">


	angular.module('searchpayment-controllers', ['720kb.datepicker']).controller('search_payment_controller',
			['$scope','$http','$window',function($scope, $http, $window) {
	$scope.searchPayment= function() {
		
		$window.alert($scope.disCost);
		$window.alert($scope.validfrom);
		$window.alert($scope.validto);
$http({
	method : "post",
	url : "/HappyBus/searchPaymentDetails",
	params : {
		'paymenttimefrom' : $scope.payment_time_from,
		'paymenttimeto' : $scope.payment_time_to
		
		}
	})
.then(function(result) {
		$scope.response = result.data;
		$scope.paymenttimefrom=angular.fromJson($scope.response.paymenttimefrom);
		$scope.paymenttimeto=angular.fromJson($scope.response.paymenttimeto);
			
	},
	function(response) {
		//$scope.message=response.message;
		$window.alert("Unable to process your request from happy_search_payment");
	});
  }
} ]);
</script>
</head>

<!-- page content -->
<div class="right_col" role="main" ng-controller="search_payment_controller">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h3>Payments</h3>
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
						<h2>{{message}}</h2>
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
									for="telephone">Payment Time From </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input type="text" id="paymentTimeFrom"
										name="paymentTimeFrom" ng-model="paymentTimeFrom"
										placeholder="e.g 2017-03-28" data-inputmask="'mask' : '1234'"
										class="form-control col-md-7 col-xs-12">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="telephone">Payment Time To </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input type="text" id=""paymentTimeFrom" name=""paymentTimeTo"
										ng-model=""paymentTimeTo" placeholder="e.g 2017-03-30"
										data-inputmask="'mask' : '1234'"
										class="form-control col-md-7 col-xs-12">
								</div>
							</div>


							<div class="ln_solid"></div>
							<div class="form-group">
								<div class="col-md-6 col-md-offset-3">
									&nbsp;&nbsp;&nbsp;
									<button class="btn btn-success" ng-click="searchPayment()">Search</button>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<button class="btn btn-primary" type="reset" ng-click="reset()">Cancel</button>
								</div>
							</div>

							<br />

							<div>
								<table border="1" cellpadding="40" cellspacing="5">

									<th>PaymentId</th>
									<th>PaymentTime</th>
									<th>PaymentType</th>
									<th>Bank</th>
									<th>Amount</th>
									
									<tr data-ng-repeat="item in response">
										<td>{{item.paymentId}}</td>
										<td>{{item.paymentTime}}</td>
										<td>{{item.paymentType}}</td>
										<td>{{item.bank}}</td>
										<td>{{item.amount}}</td>
										
									</tr>
								</table>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div align="center" ng-show="but_flg">
		    <center>
        <h2><a href="/download.do">Click here to download Excel file</a></h2>
    </center>

	</div>
	<div align="center" ng-show="but_flg">
		<button
			class=" hb-label-marign hbb-button hbb-large hbb-green hb-section"
			ng-click="pdfdownload('hbdiv')">Pdf</button>
	</div>
	</div>
</div>
<!-- /page content -->
<%@include file="admin_footer_menu.jsp"%>
</html>