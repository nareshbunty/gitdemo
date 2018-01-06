
<%@page import="com.happybus.util.StatusUtil"%>
<%@page isELIgnored="false"%>
<%@include file="admin_side_menu.jsp"%>
<%@include file="admin_top_menu.jsp"%>
<head>
<script type="text/javascript" src="js/angular.js"></script>
<!-- PNotify -->
    <link href="admin/vendors/pnotify/dist/pnotify.css" rel="stylesheet">
    <link href="admin/vendors/pnotify/dist/pnotify.buttons.css" rel="stylesheet">
    
    <link href="admin/build/css/custom.min.css" rel="stylesheet">

<script type="text/javascript">
angular.module('amenities_reg.controllers', []).controller('amenities_register_controller', ['$scope', '$window','$http',
	function($scope,$window,$http) {
	$scope.amenitiesRegistration = function() {
	$scope.message="";
	$http({
	method:"post",	
	url:"/HappyBus/saveAmenities",
	params:{
		'amenitiesName':$scope.amenitiesName
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
		new PNotify({title: 'Failure',text: result.data.message,type: 'error',styling: 'bootstrap3'});
	}); 
			}
	
}
	
]);
</script>
</head>
<!-- page content -->
<div ng-app="amenities_reg.controllers">
<div class="right_col" role="main" ng-controller="amenities_register_controller">
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
						<h2>Amenities Registration</h2>
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
									for="name">Amenities Name<span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input class="form-control col-md-7 col-xs-12"
										data-validate-length-range="6" data-validate-words="1"
										ng-model='amenitiesName'
										placeholder="e.g WiFi / Charging Point" required="required"
										type="text">
								</div>
							</div>
							<div class="ln_solid"></div>
							<div class="form-group">
								<div class="col-md-6 col-md-offset-3">
									<button  class="btn btn-primary">Cancel</button>
									<button ng-click="amenitiesRegistration()" class="btn btn-success">Submit</button>
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
<!-- /page content -->
 

    <!-- Custom Theme Scripts -->
    <script src="admin/build/js/custom.min.js"></script>

<%@include file="admin_footer_menu.jsp"%>
<!-- validator -->
    
   