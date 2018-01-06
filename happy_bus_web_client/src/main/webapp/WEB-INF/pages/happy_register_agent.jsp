<html ng-app="agent_reg.controllers">
<%@page isELIgnored="false"%>
<%@include file="admin_side_menu.jsp"%>
<%@include file="admin_top_menu.jsp"%>
<head>
<script type="text/javascript" src="js/angular.js"></script>

<script type="text/javascript">
angular.module('agent_reg.controllers', []).controller('agent_register_controller', ['$scope', '$window','$http',
	function($scope,$window,$http) {
	$scope.agentRegistration = function() {
	$scope.message="";
	
	$http({
	method:"post",	
	url:"/HappyBus/addAgent1",
	 params:{
		'agentName':$scope.agentName,
	 	'email':$scope.email,
		'phoneNumber1':$scope.phoneNumber1,
		'phoneNumber2':$scope.phoneNumber2,
		'ownerId':$scope.ownerId,
		'mobile':$scope.mobile,
		'agentAddress':$scope.address,
		'bankAccountNo':$scope.bankAccNum,
		'bankName':$scope.bankName,
		'branchName':$scope.branchName,
		'ifscCode':$scope.ifsc 
	} 
	}).then(function(result) {
		
		$scope.response=result.data;
		$scope.message=$scope.response.message;
		$window.alert($scope.response.message);
	}, function(response) {
		$window.alert("unable to process your request");
	}); 
			}
}
	
]);
</script>
</head>


<!-- page content -->
<div class="right_col" role="main"  ng-controller="agent_register_controller">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h3>agent</h3>
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
						<h2>Agent Registration</h2>
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
									for="name">Name <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input id="name" class="form-control col-md-7 col-xs-12"
										data-validate-length-range="6" data-validate-words="1"
										name="agentName" ng-model="agentName"
										placeholder="e.g Jon Doe" required="required"
										type="text">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="email">Email <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input type="email" id="email" name="email" ng-model="email"
									placeholder="e.g agent@agent.co.in"
										required="required" class="form-control col-md-7 col-xs-12">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="telephone">Telephone <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input type="tel" id="telephone" name="phoneNumber1" ng-model="phoneNumber1"
										 data-validate-length-range="5,20"
										placeholder="e.g 040-123-4567"
										data-inputmask="'mask' : '(999) 999-9999'"
										class="form-control col-md-7 col-xs-12">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="telephone">Telephone <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input type="tel" id="telephone" name="phoneNumber2" ng-model="phoneNumber2"
										data-validate-length-range="8,20"
										placeholder="e.g 040-1234567"
										data-inputmask="'mask' : '(999) 999-9999'"
										class="form-control col-md-7 col-xs-12">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="telephone">OwnerID <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input type="ownerId" id="ownerId" name="ownerId" ng-model="ownerId"
										
										placeholder="e.g 1234"
										data-inputmask="'mask' : '1234'"
										class="form-control col-md-7 col-xs-12">
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
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="textarea">Address <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<textarea id="textarea" required="required" name="address" ng-model="address"
										class="form-control col-md-7 col-xs-12"></textarea>
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="name">Bank Account No. <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input id="name" class="form-control col-md-7 col-xs-12"
										data-validate-length-range="1,20" data-validate-words="1"
										name="bankAccNum" ng-model="bankAccNum"
										placeholder="e.g 883873782323" required="required"
										type="text">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="name">Bank Name <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input id="name" class="form-control col-md-7 col-xs-12"
										data-validate-length-range="6" data-validate-words="1"
										name="bankName" ng-model="bankName"
										placeholder="e.g ICICI Bank" required="required"
										type="text">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="name">Branch Name <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input id="name" class="form-control col-md-7 col-xs-12"
										data-validate-length-range="6" data-validate-words="1"
										name="branchName" ng-model="branchName"
										placeholder="e.g ICICI Banjara Hills" required="required"
										type="text">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="name">IFSC Code <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input id="name" class="form-control col-md-7 col-xs-12"
										data-validate-length-range="6" data-validate-words="1"
										name="ifsc" ng-model="ifsc"
										placeholder="e.g ICICI9233BAN" required="required"
										type="text">
								</div>
							</div>
							<div class="ln_solid"></div>
							<div class="form-group">
								<div class="col-md-6 col-md-offset-3">
									<button  class="btn btn-primary">Cancel</button>
									<button class="btn btn-success" ng-click="agentRegistration()">Submit</button>
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
