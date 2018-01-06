<!DOCTYPE html>

<html ng-app="agent.controllers">
<%@page isELIgnored="false"%>
<%@include file="admin_side_menu.jsp"%>
<%@include file="admin_top_menu.jsp"%>

<head>
<%@include file="includers.jsp" %>
<script type="text/javascript" >

angular.module('agent.controllers',[]).controller('agent_controller',['$scope','$http','$window',function($scope,$http,$window){
 
	$scope.searchAgentDetails=function(){
		$http({	
			method:"get",
			url:"/HappyBus/searchAgent1",
			params:{
			'agentId':$scope.agentId,
			'phoneNumber1':$scope.phoneNumber1
			}
		}).then(function(result){
			$scope.response=result.data;
			$scope.data=$scope.response.data;
			$scope.data1=angular.fromJson($scope.data);

			$scope.agentId=angular.fromJson($scope.data)[0].agentId;
		    $scope.ownerId=angular.fromJson($scope.data)[0].ownerId;
		    $scope.agentName=angular.fromJson($scope.data)[0].agentName;
		   $window.alert($scope.agentId+"  "+$scope.ownerId);
 	}, function(response) {
			$window.alert("unable to process your request");
		}); 
	}
		
}]);
</script>
</head>


<!-- page content -->
<div class="right_col" role="main"  ng-controller="agent_controller">
<table border="1">
                                <th>AgentId</th>
                                <th>AgentName</th>
                                <th>AgentAddress</th>
                                <th>PhoneNumber</th>
                                <th>Status</th>
                                <th>OwnerId</th>
                                <th>CreatedBy</th>
                                <th>CreatedDate</th>
                                <th>UserId</th>
                                <th>UserName</th>
                                <th>UserRole</th>
                                <th>Email</th>
                                <th>Mobile</th>
                                <th>BankAccountNo</th>
                                <th>BankName</th>
                                <th>BranchName</th>
                                <th>Ifs Code</th>
                                
                                <tr ng-repeat="item in data1">
                                <td>{{item.agentId}}</td>
                                <td>{{item.agentName}}</td>
                                <td>{{item.agentAddress}}</td>
                                <td>{{item.phoneNumber1}}</td>
                                <td>{{item.status}}</td>
                                <td>{{item.ownerId}}</td>
                                <td>{{item.createdBy}}</td>
                                <td>{{item.createdDate}}</td>
                                <td>{{item.user.userId}}</td>
                                <td>{{item.user.userName}}</td>
                                <td>{{item.user.userRole}}</td>
                                <td>{{item.user.email}}</td>
                                <td>{{item.user.mobile}}</td>
                                <td>{{item.bankDetails.bankAccountNo}}</td>
                                <td>{{item.bankDetails.bankName}}</td>
                                <td>{{item.bankDetails.branchName}}</td>
                                <td>{{item.bankDetails.ifscCode}}</td>
                                </tr>
                                </table>
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
						<h2>Search Agent</h2>
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
									for="telephone">AgentID 
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input type="agentId" id="agentId" name="agentId" ng-model="agentId"
										
										placeholder="e.g 1234"
										data-inputmask="'mask' : '1234'"
										class="form-control col-md-7 col-xs-12">
								</div>
							</div>
							
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="telephone">Telephone 
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input type="tel" id="telephone" name="phoneNumber1" ng-model="phoneNumber1"
										 data-validate-length-range="5,20"
										placeholder="e.g 040-123-4567"
										data-inputmask="'mask' : '(999) 999-9999'"
										class="form-control col-md-7 col-xs-12">
								</div>
							</div>
							<div class="ln_solid"></div>
							<div class="form-group">
								<div class="col-md-6 col-md-offset-3">
									<button  class="btn btn-primary">Cancel</button>
									<button class="btn btn-success" ng-click="searchAgentDetails()">Submit</button>
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

</html>