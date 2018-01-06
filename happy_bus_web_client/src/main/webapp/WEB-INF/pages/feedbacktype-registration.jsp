<html ng-app="feedback_reg.controllers">
<%@page isELIgnored="false"%>
<%@include file="admin_side_menu.jsp"%>
<%@include file="admin_top_menu.jsp"%>
<head>
<script type="text/javascript" src="js/angular.js"></script>

<script type="text/javascript">
	angular.module('feedback_reg.controllers', []).controller(
			'feedback_register_controller',
			[ '$scope', '$window', '$http', function($scope, $window, $http) {
				
			    $scope.statusValues = {
			    		"Enable":"1", 
			    		"Disable":"0"
			    }
				
				$scope.addFeedbackType = function() {
					$scope.message = "";
					$http({
						method :"post",
						url : "/HappyBus/addFeedbackType",
						params : {
							'feedbackTypeDecription' : $scope.feedbackTypeDecription,
							'status' : $scope.status
						}
					}).then(function(result) {
						$scope.response=result.data;
						$scope.message=$scope.response.message;
						$scope.feedbackTypeDecription="";
				        
					}, function(response) {
						$window.alert("unable to process your request"+response);
						
					});
				} 
			}
			]);
	
</script>
</head>


<!-- page content -->
<div class="right_col" role="main"
	ng-controller="feedback_register_controller">
	<div class="">
	  <div style="color: green; text-align: center; font: medium;">{{message}}</div>
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
						<h2>FeedbackType Registration</h2>
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

						<form class="form-horizontal form-label-left" >
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
							<div class="ln_solid"></div>
							<div class="form-group">
								<div class="col-md-6 col-md-offset-3">
									<button class="btn btn-primary">Cancel</button>
									<button class="btn btn-success" ng-click="addFeedbackType()">Submit</button>
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
<!-- <script src="admin/vendors/validator/validator.js"></script>   -->
</html>
