<html>
<%@page isELIgnored="false"%>
<head>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="css/feedback.css">

<!-- Bootstrap -->
<link href="admin/vendors/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Font Awesome -->
<link href="admin/vendors/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<!-- NProgress -->
<link href="admin/vendors/nprogress/nprogress.css" rel="stylesheet">
<!-- Custom Theme Style -->
<!--    <link href="admin/build/css/custom.min.css" rel="stylesheet"> -->

<script type="text/javascript" src="js/angular.js"></script>

<script type="text/javascript">
	angular.module('feedback.controllers', []).controller('feedback_controller',['$scope','$window','$http',function($scope, $window, $http) {

								$http
										.get("getAllFeedbackTypeDesc")
										.then(
												function(response) {
													$scope.response = response.data;
													$scope.feedbackTypeDescs = angular
															.fromJson($scope.response.data);
													$window
															.alert($scope.feedbackTypeDescs);
												});

								$scope.getUserId = function() {
									$http({
										method : "get",
										url : "/HappyBus/getUserId",
										params : {
											"email" : $scope.email,
										}
									})
											.then(
													function(result) {
														$scope.message = "";
														$scope.userId = "";
														$scope.response = result.data;
														if ($scope.response.status == "EXCEPTION"
																|| $scope.response.status == "FAILURE") {
															$scope.message = $scope.response.message;
														} else {
															$scope.userId = $scope.response.data;
														}

													}, function(response) {

													});
								}
								$scope.submit = function() {

									$http(
											{
												method : "post",
												url : "/HappyBus/saveFeedback",
												params : {
													"bookingId" : $scope.bookingId,
													"feedbackDescription" : $scope.feedbackDescription,
													"feedbackTypeId" : $scope.feedbackTypeId,
													"rating" : $scope.rating,
													"userId" : $scope.userId
												}
											})
											.then(
													function(result) {
														$scope.response = result.data;

														if ($scope.response.status == "EXCEPTION"
																|| $scope.response.status == "FAILURE") {
															$scope.feedMessage = $scope.response.message;
														} else {
															$scope.feedMessage = $scope.response.message;
														}

													},
													function(response) {
														$window
																.alert("Unable to process");
													});
								}
							} ]);
</script>
</head>

<body>
	<div>
		<%@include file="happy_menu.jsp"%>
	</div>

	<div ng-app="feedback.controllers" ng-controller="feedback_controller">
		<div style="color: graytext;">{{feedMessage}}</div>
		<div class="bgcolor">
			<h2>Feedback</h2>

			<form name="feedbackForm" class="form-horizontal form-label-left">

				<div class="item form-group">
					<label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Email</label>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<input type="email" name="email" ng-model="email"  required
							ng-blur="getUserId()" class="form-control col-md-7 col-xs-12" />
						<span style="color: red">{{message }}</span>
					</div>
				</div>
				<div class="item form-group">
					<label class="control-label col-md-3 col-sm-3 col-xs-12">TicketNumber</label>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<input type="text" name="bookingId" ng-model="bookingId"  required
							class="form-control col-md-7 col-xs-12">
					</div>
				</div>
				<div class="item form-group">
					<label class="control-label col-md-3 col-sm-3 col-xs-12">FeedbackType</label>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<select ng-model="feedbackTypeId" name="feedbackTypeId"  required
							class="form-control col-md-7 col-xs-12">
							<option ng-repeat="value in feedbackTypeDescs"
								class="form-control col-md-7 col-xs-12"
								value="{{value.feedback_type_id}}">{{value.feedback_type_desc}}</option>
						</select>
					</div>
				</div>
				<div class="item form-group">
					<label class="control-label col-md-3 col-sm-3 col-xs-12">FeedbackDescription</label>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<textarea rows="10" cols="30"  required
							class="form-control col-md-7 col-xs-12"
							ng-model="feedbackDescription" name="feedbackDescription"></textarea>
					</div>
				</div>
				<div class="item form-group">
					<label class="control-label col-md-3 col-sm-3 col-xs-12">YourRating</label>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<input class="star star-5" id="star-5" type="radio" name="rating"
							ng-model="rating" value="5" /> <label class="star star-5"
							for="star-5"></label> <input class="star star-4" id="star-4"
							type="radio" name="rating" ng-model="rating" value="4" /> <label
							class="star star-4" for="star-4"></label> <input
							class="star star-3" id="star-3" type="radio" name="rating"
							ng-model="rating" value="3" /> <label class="star star-3"
							for="star-3"></label> <input class="star star-2" id="star-2"
							type="radio" name="rating" ng-model="rating" value="2" /> <label
							class="star star-2" for="star-2"></label> <input
							class="star star-1" id="star-1" type="radio" name="rating"
							ng-model="rating" value="1" /> <label class="star star-1"
							for="star-1"></label>
					</div>
				</div>
				<div class="form-group">
					<button class="btn btn-success" ng-click="submit()" >Submit</button>
				</div>
			</form>
		</div>
	</div>
</body>

<!-- /page content -->
<!-- validator -->
<script src="admin/vendors/validator/validator.js"></script>
</html>
