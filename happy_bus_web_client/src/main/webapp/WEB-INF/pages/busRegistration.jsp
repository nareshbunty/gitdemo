<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@page import="com.happybus.util.StatusUtil"%>
<%@page import="com.happybus.util.RolesConstants"%>
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
    <!-- iCheck -->
    <link href="admin/vendors/iCheck/skins/flat/green.css" rel="stylesheet">
<!-- Select2 -->
    <link href="admin/vendors/select2/dist/css/select2.min.css" rel="stylesheet">
<script type="text/javascript" src="js/angular.js"></script>
<script type="text/javascript" src="js/checklist-model.js"></script>
<script type="text/javascript">
angular.module('bus_reg.controllers', ["checklist-model"]).controller('bus_register_controller', ['$scope', '$window','$http',
	function($scope,$window,$http) {
	$scope.amenitiesList = [];
	$scope.selectedAmenities = [];
	$scope.compareFn = function(obj1, obj2){
        return obj1.id === obj2.id;
    };

    $scope.checkAll = function() {
        $scope.selectedAmenities.splice(0, $scope.selectedAmenities.length);
        for (var i in $scope.amenitiesList) {
             $scope.selectedAmenities.push($scope.amenitiesList[i]);
        }
    };

    $scope.uncheckAll = function() {
        $scope.selectedAmenities.splice(0, $scope.selectedAmenities.length);
    }
	$scope.collect = function() {
			
			$scope.collectBusType();
			$scope.collectAmenities();
		}
	$scope.collectAmenities = function() {
		$scope.message="";
		$http({
		method:"post",	
		url:"/HappyBus/getAllAmenities",
		}).then(function(result, msg) {
			$scope.responseAmenities=result.data;
			$scope.data=$scope.responseAmenities.data;
			$scope.message=$scope.responseAmenities.message;
			$scope.amenities=angular.fromJson($scope.data);
			$scope.amenitiesList=$scope.amenities;
			
		}, function(response) {
			new PNotify({title: 'Failure',text: result.data.message,type: 'error',styling: 'bootstrap3'});
		});
		
		}
	$scope.getcount = function(){
		return $scope.i;
	}
	$scope.collectBusType = function() {
		$scope.message="";
		$http({
		method:"post",	
		url:"/HappyBus/getAllBusType",
		}).then(function(result) {
			$scope.responseBusType=result.data;
			$scope.data=$scope.responseBusType.data;
			$scope.message=$scope.responseBusType.message;
			$scope.busTypeList=angular.fromJson($scope.data);
			$scope.selectedOp=$scope.busTypeList[0];
			
		}, function(response) {
			new PNotify({title: 'Failure',text: result.data.message,type: 'error',styling: 'bootstrap3'});
		});
		
	}
	$scope.busRegistration = function() {
		$scope.message="";
		$scope.busTypeId=$scope.busType;
		var list=$scope.selectedAmenities;
		var busReg=$scope.busRegNo;
		var busT=$scope.busType;
		$http({
		method:"post",	
		url:"/HappyBus/saveBus",
		data: {
			amenities: list,
			busType: {busTypeId:busT},
			busRegNo:busReg
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

<div ng-app="bus_reg.controllers">

<div class="right_col" role="main" ng-controller="bus_register_controller">

	<div class="" ng-init="collect()">

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
		<div>
		{{selectedAmenities}}
		</div>
		<div class="clearfix"></div>
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_title">
						<h2>Bus Registration</h2>
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
									for="name">Bus Registration Number<span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<input id="name" class="form-control col-md-7 col-xs-12"
										data-validate-length-range="6" data-validate-words="2"
									 	ng-model="busRegNo"
										placeholder="e.g AP 01 CA 9898" required="required"
										type="text">
								</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="BusType">Bus Type <span class="required">*</span>
								</label>
								
									<div class="col-md-6 col-sm-6 col-xs-12">
									<select id="name" class="form-control col-md-7 col-xs-12"
										name="busTypeName" ng-model="busType"
										required="required">
										
										<option ng-repeat="data in busTypeList" value="{{data.busTypeId}}">
											{{data.busTypeName}} {{data.busModel}} {{data.layoutModel}} {{data.layoutType}},Seats:{{data.noOfSeats}},{{data.layoutDesc}}
										</option>
									</select>
									<div class="buttons">
										<a href="addBusType"><button type="button" class="btn btn-info btn-xs">Add Bus Type</button></a>
									</div>
									</div>
							</div>
							<div class="item form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="email">Amenities <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<div class="x_content">
										<div class="">
						                    <ul class="to_do">
						                      <li ng-repeat="data in amenitiesList">
						                        <input type="checkbox" checklist-model="selectedAmenities" checklist-value="data" checklist-comparator=".amenitiesName"> {{data.amenitiesName}}
						                      </li>
						                    </ul>
					                  </div>
                					</div>
                					<div class="buttons">
										<a href="addAmenities"><button type="button" class="btn btn-info btn-xs">Add Amenities</button></a>
										<button type="button" ng-click="checkAll()" class="btn btn-info btn-xs">Check All</button>
										<button type="button" ng-click="uncheckAll()" class="btn btn-info btn-xs">Uncheck All</button>
									</div>
                					<!-- <div style="margin: auto;">
										<lable ng-repeat="data in amenitiesList">
											<div ng-if="$index % 2 ==0">
											</div>
											<input type="checkbox" checklist-model="selectedAmenities" checklist-value="data" checklist-comparator=".amenitiesName"> {{data.amenitiesName}}
										</lable>
									</div>-->
                				</div>
								</div>
							<div>
					
								
								
								
								
								
								
								
								
								
								
							</div>
							<div class="ln_solid"></div>
							<div class="form-group">
								<div class="col-md-6 col-md-offset-3">
									<button type="submit" class="btn btn-primary">Cancel</button>
									<button ng-click="busRegistration()" class="btn btn-success">Submit</button>
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

<%@include file="admin_footer_menu.jsp"%>
<!-- iCheck -->
    
<!-- validator -->
    