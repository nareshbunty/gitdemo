<%@page import="com.happybus.util.StatusUtil"%>
<%@page isELIgnored="false"%>
<%@include file="admin_side_menu.jsp"%>
<%@include file="admin_top_menu.jsp"%>
<head>
<!-- PNotify -->
    <link href="admin/vendors/pnotify/dist/pnotify.css" rel="stylesheet">
    <link href="admin/vendors/pnotify/dist/pnotify.buttons.css" rel="stylesheet">
    <link href="admin/build/css/custom.min.css" rel="stylesheet">
<!-- Switchery -->
    <link href="admin/vendors/switchery/dist/switchery.min.css" rel="stylesheet">
<!-- Select2 -->
    <link href="admin/vendors/select2/dist/css/select2.min.css" rel="stylesheet">
<script type="text/javascript" src="js/angular.js"></script>
<script type="text/javascript">
angular.module('busType_reg.controllers', []).controller('busType_register_controller', ['$scope', '$window','$http',
	function($scope,$window,$http) {
	$scope.busTypeRegistration = function() {
	$scope.message="";
	$http({
	method:"post",	
	url:"/HappyBus/saveBusType",
	params:{
		'busTypeName':$scope.busTypeName,
		'busModel':$scope.busModel,
		'noOfSeats':$scope.noOfSeats,
		'layoutModel':$scope.layoutModel,
		'layoutDesc':$scope.layoutDesc,
		'layoutType':$scope.layoutType
	}
	}).then(function(result, msg) {
		$scope.response=result.data;
		$scope.message=$scope.response.message;
		$scope.status=$scope.response.status;
		if($scope.status == "<%=StatusUtil.HAPPY_STATUS_SUCCESS%>"){
			new PNotify({title: 'Success',text: result.data.message,type: 'success',styling: 'bootstrap3'});	
		}
		else if($scope.status == "<%=StatusUtil.HAPPY_STATUS_EXCEPTION%>"){
			new PNotify({title: 'Failure',text: result.data.message,type: 'notice',styling: 'bootstrap3'});
		}
		else if($scope.status == "<%=StatusUtil.HAPPY_STATUS_FAILURE%>"){
			new PNotify({title: 'Failure',text: result.data.message,type: 'error',styling: 'bootstrap3'});
		}
	}, function(response) {
		$window.alert("unable to process your request");
	}); 
			}
}
	
]);
</script>
</head>
<!-- page content -->
<div ng-app="busType_reg.controllers">
<div class="right_col" role="main" ng-controller="busType_register_controller">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h3>Bus</h3>
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
						<h2>Bus Type Registration</h2>
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
									for="name">Bus Type Name<span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<select id="name" class="form-control col-md-7 col-xs-12"
										name="busTypeName" ng-model="busTypeName"
										required="required">
										<option value="">--Select Bus Type Name--</option>
										<option value="AC">AC</option>
			                            <option value="Non-AC">Non - AC</option>
									</select>
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="name">Bus Model<span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input id="name" class="form-control col-md-7 col-xs-12"
										data-validate-length-range="6" data-validate-words="1"
										name="busModel" ng-model="busModel"
										placeholder="e.g Mercedes Benz  Multi Axel" required="required"
										type="text">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="name">No Of Seates<span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input id="name" class="form-control col-md-7 col-xs-12"
										data-validate-length-range="6" data-validate-words="1"
										name="noOfSeats" ng-model="noOfSeats"
										placeholder="e.g 40" required="required"
										type="text">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="name">Layout Model<span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input id="name" class="form-control col-md-7 col-xs-12"
										data-validate-length-range="6" data-validate-words="1"
										name="layoutModel" ng-model="layoutModel"
										placeholder="e.g Sleeper" required="required"
										type="text">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="name">Layout Description<span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input id="name" class="form-control col-md-7 col-xs-12"
										data-validate-length-range="6" data-validate-words="1"
										name="layoutDesc" ng-model="layoutDesc"
										placeholder="e.g  Push back Luxury seats" required="required"
										type="text">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="name">Layout Type<span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input id="name" class="form-control col-md-7 col-xs-12"
										data-validate-length-range="6" data-validate-words="1"
										name="layoutType" ng-model="layoutType"
										placeholder="e.g 2+2" required="required"
										type="text">
								</div>
							</div>
							<div class="ln_solid"></div>
							<div class="form-group">
								<div class="col-md-6 col-md-offset-3">
									<button type="submit" class="btn btn-primary">Cancel</button>
									<button ng-click="busTypeRegistration()" class="btn btn-success">Submit</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<!-- Switchery -->
    <script src="admin/vendors/switchery/dist/switchery.min.js"></script>
<!-- Select2 -->
    <script src="admin/vendors/select2/dist/js/select2.full.min.js"></script>
<!-- /page content -->

<%@include file="admin_footer_menu.jsp"%>
<!-- validator -->

    