<html ng-app="feedback_type.controllers">
<%@page import="com.happybus.util.StatusUtil"%>
<%@page isELIgnored="false"%>
<%@include file="admin_side_menu.jsp"%>
<%@include file="admin_top_menu.jsp"%>
<head>
<script type="text/javascript" src="js/angular.js"></script>
<!-- PNotify -->
<link href="admin/vendors/pnotify/dist/pnotify.css" rel="stylesheet">
<link href="admin/vendors/pnotify/dist/pnotify.buttons.css"
	rel="stylesheet">

<link href="admin/build/css/custom.min.css" rel="stylesheet">
<link href="admin/build/css/table.css" rel="stylesheet">

</head>
<!-- page content -->
<div class="right_col" role="main"
	ng-controller="feedback_type_controller">
	<div class="">
          <div style="color: green; text-align: center; font: medium;">{{msg}}</div>
		<div class="page-title">
			<div class="title_left">
				<h3>Feedback</h3>
			</div>
		</div>
		
		<div class="clearfix"></div>
        	
		<div class="row">
			<div style="font: medium;" align="center"></div>
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_title">
						<h2>AllFeedbackTypes</h2>
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

						<table border="0">
							<tr>
								<th>feedbackTypeDecription</th>
								<th>status</th>
								<th>Operations</th>
							</tr>
							<tr ng-repeat="feedbakType in feedbakTypes">
								<td>{{feedbakType.feedbackTypeDecription}}</td>
								<td>{{feedbakType.status}}</td>
								<td><button ng-click="edit(feedbakType.feedbackTypeId,feedbakType.feedbackTypeDecription,feedbakType.status)">EDIT</button>
									<button ng-click="deleteOperation(feedbakType.feedbackTypeId)" >DELETE</button></td>
							</tr>

						</table>
						<div class="ln_solid"></div>
						<div ng-show="updateDivshow">
						   <div class="x_content">

						<form class="form-horizontal form-label-left" >
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="name">FeedbackTypeId
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input id="feedbackTypeId" class="form-control col-md-7 col-xs-12"
										name="feedbackTypeId" ng-model="feedbackTypeId"
									    readonly="readonly" required="required" type="text">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="name">FeedbackTypeDecription
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input id="feedbackTypeDecription" class="form-control col-md-7 col-xs-12"
										name="feedbackTypeDecription" ng-model="feedbackTypeDecription"
										placeholder="Driver" required="required" type="text">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="name">Status
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<select class="form-control col-md-7 col-xs-12" ng-model="status" name="status">
                                       <option ng-repeat="(key,value) in statusValues"  value="{{value}}" class="form-control col-md-7 col-xs-12">{{key}}</option>
                                   </select>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-md-6 col-md-offset-3">

									<button class="btn btn-success" ng-click="update()">Update</button>
								</div>
							</div>
                        <div class="ln_solid"></div>
						</form>
						
						</div>
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
<script type="text/javascript">
	angular.module('feedback_type.controllers', []).controller('feedback_type_controller',['$scope','$window','$http',
							function($scope, $window, $http) {
		                   $scope.statusValues = {
		                  		"Enable":"1", 
		                  		"Disable":"0"
		                  }
		                  $scope.updateDivshow=false;	
		
		                //load operation method
								$scope.loadFeedbackTypes= function() {
									$scope.message = "";
									$http({
										method : "get",
										url : "/HappyBus/getAllFeedbackTypes",
										params : {}
									}).then(function(result) {
														$scope.response = result.data;
														$scope.feedbakTypes = angular.fromJson($scope.response.data);
									},function(response) {
														$window.alert("Unable to process");
									});
								}
							  $scope.loadFeedbackTypes();  
                         //delete opertion method
							  $scope.deleteOperation= function(id) {
										$http({
											method: "get",
											url: "/HappyBus/deleteFeedbackType",
											params:{
												'feedbackTypeId':id
											}
										}).then(function(result) {
											 $scope.response = result.data;
											 $scope.msg=$scope.response.message;
											 $window.alert($scope.response.message);
											 $scope.loadFeedbackTypes();
										},
										function(response) {
											 $window.alert("Request unable to process::");
										}); 
								}	
                         
							  //edite opertion method
							   $scope.edit= function(id,feedbackTypeDecription,status) {
								        $scope.updateDivshow=true;
										$scope.feedbackTypeId=id;
										$scope.feedbackTypeDecription=feedbackTypeDecription;
										$scope.status=status;
										
								}	
                        
							 //update opertion method
							  $scope.update= function() {
										 $http({
											method: "post",
											url: "/HappyBus/updateFeedbackType",
											params:{
												'feedbackTypeId':$scope.feedbackTypeId,
												'feedbackTypeDecription' : $scope.feedbackTypeDecription,
												'status' : $scope.status
											}
										}).then(function(result) {
											 $scope.response = result.data;
											 $scope.msg=$scope.response.message;
											 $scope.loadFeedbackTypes();
											 $scope.updateDivshow=false;
										},
										function(response) {
											 $window.alert("Request unable to process::");
										});  
								}		
                          
                         
	}
	]);
</script>
</html>
