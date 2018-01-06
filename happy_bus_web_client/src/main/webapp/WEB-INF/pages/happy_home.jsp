<%@page import="com.happybus.util.RolesConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="happy_bus_app">
<head>
<%@include file="includers.jsp"%> 
<title>Happy Bus</title> 
<script type="text/javascript" src="js/angular-datepicker.js"></script>
<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="js/jquery.redirect.js"></script>

<link href="css/angular-datepicker.css" rel="stylesheet" type="text/css" ></link>

<script type="text/javascript">
angular.module('happy_bus_app', ['720kb.datepicker']).controller('happy_home_controller', ['$scope', '$window','$http',
	function($scope,$window,$http) {

	/* 	This logic is used for registration of sign up */
	$scope.checkUser= function() {
		if($scope.email==null){
		$scope.reg_user_message="Please Enter Email."
		}
		else {			
			if( $scope.mobile==null){
				$scope.reg_user_message="Please Enter Mobile."
				}
			else{				
				$http({
					method:"get",
					url:"/HappyBus/checkUser",
					params:{
						"email":$scope.email,
						"mobile":$scope.mobile
					}
				}).then(function(result) {
					$scope.response=result.data;
					if($scope.response.status=="EXCEPTION" || $scope.response.status=="FAILURE"){
						$scope.reg_user_message=$scope.response.message;
					}
				},
				function(response) {					
					
				}); 
			}
		}		
	}
	
	$scope.registerPassengerDetails = function() {
		if($scope.email==null){
			$scope.reg_user_message="Please Enter Email."
			}
		else if($scope.mobile==null){
			$scope.reg_user_message="Please Enter Mobile."
			}
		else if($scope.password==null){
			$scope.reg_user_message="Please Enter Password."
			} 
		else{
				$scope.reg_user_message="";
				$http({
			method:"post",	
			url:"/HappyBus/userRegistration",
			params:{
				'email':$scope.email,
				'mobile':$scope.mobile,
				'password':$scope.password
			}
			}).then(function(result) {
		
				$scope.response=result.data;
				$scope.flag=false;
				if($scope.response.status=="EXCEPTION" || $scope.response.status=="FAILURE"){
					$scope.reg_user_message=$scope.response.message;
				}else{
						$scope.flag=true;
					$scope.data=$scope.response.data;
					$scope.mobile=angular.fromJson($scope.data).mobile;
		    		 $scope.userId=angular.fromJson($scope.data).userId;
					//redirect to check OTP
				$window.location.href="/HappyBus/otpForm?userId="+$scope.userId+"&mobile="+$scope.mobile;
			}
			
			}, function(response) {
				$window.alert("unable to process your request");
		}); 
	}
}


/* 	logic ends for registration of sign up */
	
/* 	This logic is used for Search the bus */
	$scope.search_bus_div=true;
	$scope.searchBus = function() {
		$http({
			method:"post",
			url:"/HappyBus/searchBus",
			params:{
				"source":$scope.source,
				"destination":$scope.destination,
				"date_of_journey":$scope.dateOfJourney
			}
		}).then(function(result) {
			$scope.response=result.data;			
			if($scope.response.status=="SUCCESS"){
			
				$scope.search_bus_div=false;
				
			$scope.search_message = $scope.response.message;
			$scope.searchbus1= angular.fromJson($scope.response.data);
			$scope.hb_src=$scope.searchbus1[0].source;
			$scope.hb_des=$scope.searchbus1[0].destination;
			$scope.hb_doj=$scope.searchbus1[0].dateOfJourney;
			$scope.routeId=$scope.searchbus1[0].routeId;
			
			}
			},
		function(response) {
			$window.alert("fail");
			
		}); 
		}
	
/* 	logic ends/completed for Search the bus */


/* 	select seats started and happy_boarding_detais */	
	
//$scope.hb_sel_seat_flag=false;

$scope.hb_bor_drp_flag=false;
$scope.totalPrice=0; 
$scope.price;

//This Function Used To Select The Seats From SeatLayOut 1
$scope.selectseats = function() {
	$http({
		method:"post",
		url:"/HappyBus/boardingDetails",
		params:{
			'routeId': $scope.routeId 
				
				/* ,
              "boardingPoints":"ameerpet,srnagar",
			"droppingPoints":"Banglore,KSR,BTM"  */
		}
	}).then(function(result) {
		$scope.response=result.data;
		console.log(result);
		if($scope.response.status=="EXCEPTION" || $scope.response.status=="FAILURE"){
			$scope.message=$scope.response.message;
		}
		else{
			$scope.hb_bor_drp_flag=true;
			$scope.data=$scope.response.data;
			$scope.names1 =angular.fromJson($scope.data).boardingPoints;
			$scope.names2 =angular.fromJson($scope.data).droppingPoints;
			$scope.price=angular.fromJson($scope.data).price;
			$scope.bor_array=$scope.names1.split(',');
			$scope.drp_array=$scope.names2.split(',');
	
		}
	},
	function(response) {
		$window.alert("unable to process your request");	
	}); 
	}
	
//This Function Used To ShowSeat Layout 6

$scope.flagg=false;
$scope.showSeats=function(){
	$scope.showSeatLayoutList = [];
	$scope.selectedSeats = [];
	$http({
		method:"get",
		url:"/HappyBus/seatLayoutDetails",
		params:{
			'routeId':$scope.routeId
		}
	}).then(function(result){
		$scope.response=result.data;
		console.log(result);
			if($scope.response.status=="EXCEPTION" 
						|| $scope.response.status=="FAILURE"){
			$scope.message=$scope.response.message;
		}
		else{    	
			$scope.flagg=true;
			$window.alert($scope.response.data);    	
	
			$scope.noOfSeats=angular.fromJson($scope.response.data).noOfSeats;//40
     
			$scope.seatNoList=angular.fromJson($scope.response.data).seatNoList;//1,2,3,4
     
			$scope.layoutType=angular.fromJson($scope.response.data).layoutType;
     
			$scope.bookedseats=angular.fromJson($scope.response.data).bookedSeatNoList;        
			
    		$scope.lay_array=$scope.layoutType.split('+');        
     
			$scope.rows=0;  //4
			$scope.cols=0;  			

			for (var i=0;i<$scope.lay_array.length;i++)        
			{        	
				$scope.rows+=parseInt($scope.lay_array[i]);         
			}         
     			
			$scope.cols=parseInt(($scope.noOfSeats)/($scope.rows)); //10
			
			$scope.sortedSeatsList=new Array($scope.noOfSeats) ;
			
			//$window.alert($scope.sortedSeatsList);
		
			$scope.k=0;
			for(var i=0;i<$scope.seatNoList.length/$scope.cols;i++)
			{    				
				for(var j=0;j<$scope.seatNoList.length;j++)
				{
					if(j%$scope.rows == (i+0))
						{
						$scope.sortedSeatsList[$scope.k]=$scope.seatNoList[j]; 
						$scope.k++;    						
						}
				}
			}    			
	
			//$scope.gap=new Array($scope.cols);
    
				$scope.seatArray=new Array($scope.rows);//example 4 rows                
     
			for(var i=0; i< $scope.seatArray.length; i++ ) //creating 2D Array        
			{
            	$scope.seatArray[i]=new Array($scope.cols);	
			}
			
			$scope.ind=0;
			$scope.dis=0;
			
			//$window.alert(""+$scope.rows+"-"+$scope.cols+"\n"+$scope.sortedSeatsList);
			
			
			$scope.imgUrl = 'images/ic_availableseat.png'
			for(var i=0; i<$scope.rows; i++) //4
			{
				for(var j=0;j<$scope.cols;j++)//10
				{
					 $scope.seatArray[i][j]={'name' : ''+$scope.sortedSeatsList[$scope.ind], 'src' : ''+$scope.imgUrl, 'disable':false };
				 
				if($scope.sortedSeatsList[$scope.ind]== $scope.bookedseats[$scope.dis].seatNo)
					{
					
						if(($scope.bookedseats[$scope.dis].gender == 'f') || ($scope.bookedseats[$scope.dis].gender == 'F'))
						{
						
	    					$scope.seatArray[i][j]={'name' : ''+$scope.sortedSeatsList[$scope.ind], 'src' : 'images/ic_ladiesseat.png', 'disable':true};

						}else{
	    					$scope.seatArray[i][j]={'name' : ''+$scope.sortedSeatsList[$scope.ind], 'src' : 'images/ic_unavailableseat.png', 'disable':true};

						}  					
					
					$scope.dis++;
					}   			
					
				$scope.ind++;
				}
			}    		
	
			}
			},
				function(response) {
				$window.alert("unable to process your request");	
				});
			}
			
			
//This Function Used To get The UserId Dynamically Layout 6

$scope.checkUserName = function() {
	if($scope.email==null){
	$scope.message="Please Enter Email."
	}
	else {		
		if( $scope.mobile==null){
			$scope.message="Please Enter Mobile."
			}
		else{			
			$http({
				method:"get",
				url:"/HappyBus/checkUser",
				params:{
					"email":$scope.email,
					"mobile":$scope.mobile
				}
			}).then(function(result) {
				$scope.response=result.data;
				if($scope.response.status=="EXCEPTION" ){
					$scope.message=$scope.response.message;
								
				    
				   }
				else if($scope.response.status=="SUCCESS"){
					$window.alert($scope.response.message);
				
				document.getElementById('id02').style.display='block';
			
				
				}else{
					$scope.data=$scope.response.data;	
					$scope.userId=angular.fromJson($scope.data).userId;	
				//$window.alert($scope.userId);
				}
				
			},
			function(response) {			
			}); 
			}
		}		
	}
	
//This Function Used To change The Seats From SeatLayOut 2      			

$scope.changeSeat=function(idrow, idcol){		
	
	 if($scope.seatArray[idrow][idcol].src ==  'images/ic_availableseat.png')
		{			 
		 $scope.seatArray[idrow][idcol].src='images/ic_selectedseat.png';			 
		 $scope.addfeilds(idrow,idcol);
		}
	 else{			 
		 $scope.seatArray[idrow][idcol].src='images/ic_availableseat.png';			 
		 $scope.removefeidls(idrow,idcol);
	 }

}

//This Function Used to save the Booking Detais in Db List 5	
$scope.continuee=function()
{
	$scope.noOfPassengers=$scope.feilds.length;	
	$scope.boardingPoint=$scope.selectedName1;
	$scope.droppingPoint=$scope.selectedName2;	
   $http({
	method:"post",
	url:"/HappyBus/passengerBookingDetails",
	headers: {
        'Content-type': 'application/json'
    }
    ,data:{
   		 	'routeId':$scope.routeId ,
            'userId':$scope.userId,
            'noOfPassengers':$scope.noOfPassengers,
            'boardingPoint':$scope.boardingPoint,
            'droppingPoint':$scope.droppingPoint,
            'finalFare':$scope.totalPrice,
    		'passengerList':$scope.feilds	 
    }
}).then(function(result) {
	$scope.response=result.data;
	if($scope.response.status=="EXCEPTION" || $scope.response.status=="FAILURE"){
		$scope.message=$scope.response.message;
	}
	else
	{
	$scope.data=$scope.response.data;
	$scope.bookingId=angular.fromJson($scope.data).bookingId;
    $scope.noOfPassengers=angular.fromJson($scope.data).noOfPassengers;
    $scope.boardingPoint=angular.fromJson($scope.data).boardingPoint;
    $scope.droppingPoint=angular.fromJson($scope.data).droppingPoint;
    $scope.finalFare=angular.fromJson($scope.data).finalFare;
    $scope.dateOfJourney=angular.fromJson($scope.data).dateOfJourney;
    $scope.routeId=angular.fromJson($scope.data).routeId;
    $scope.userId=angular.fromJson($scope.data).userId;
    
    var routeId=$scope.routeId;
    var bookingId=$scope.bookingId;
    var noOfPassengers=$scope.noOfPassengers;	   
    var boardingPoint=$scope.boardingPoint;
    var droppingPoint=$scope.droppingPoint;
    var finalFare=$scope.finalFare;
    var dateOfJourney=$scope.dateOfJourney;
    var userId=$scope.userId;
  //var passengerList=angular.toJson($scope.feilds);
    $scope.passengerList=angular.toJson($scope.feilds);	   
    $.redirect("/HappyBus/bookingInfo?routeId="+routeId+"&bookingId="+bookingId+"&userId="+userId+"&boardingPoint="+boardingPoint+"&droppingPoint="+droppingPoint+"&dateOfJourney="+dateOfJourney+"&noOfPassengers="+noOfPassengers+"&finalFare="+finalFare+"&passengerList="+ $scope.passengerList);  
	}

},
function(response) {
	$window.alert("unable to process your request");	
}); 
    
}


//This Function Used To add Fields to Passenger List 3  


$scope.feilds=[];
$scope.addfeilds=function(idrow,idcol){		
	 $scope.totalPrice+= $scope.price;
	var new_item=  $scope.feilds.length+1;
	$scope.feilds.push({'seatno':''+$scope.seatArray[idrow][idcol].name});
}
	
//This Function Used To Remove Fields to Passenger List 4 	
$scope.removefeidls=function(idrow,idcol){
	$scope.totalPrice-= $scope.price;	
	var last_item = $scope.feilds.length-1;
    $scope.feilds.splice(last_item);
}


//This function is used to chek coupon code

$scope.checkCouponCode=function(){
	
	$http({
		method:"get",
		url:"/HappyBus/checkCouponCodeDetails",
		params:{
			"CouponCopde":$scopecouponcode		
		}
	}).then(function(result) {
		$scope.response=result.data;		
		  
		 },
	function(response) {			
	}); 
}//end of check coupon
}]);
</script>
</head>

<%
response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility

if(session.getAttribute("userId")!=null 
&& session.getAttribute("userRole")!=null){
	
	if( session.getAttribute("userRole").equals(RolesConstants.ROLE_PASSENGER)){
	response.setStatus(301);
response.setHeader("location","/HappyBus/passengerDashboard");
}else{
	response.setStatus(301);
response.setHeader("location","/HappyBus/adminDashboard");

}}
	

%>
<body  class="hbb-container hbb-card " ng-controller="happy_home_controller">

<!-- header is here -->
<div>
	<img class="hb-ddown" style="float: left;" src="images/logontitle.png" width="500px" height="200px">
<div class="hb-ddown" style="float: right;">

    <br><br><br><br><br><br><br>
    <button onclick="document.getElementById('login_frame_div').style.display='block'" 
    class="hb-margin-btn hbb-btn  hbb-blue  hbb-large ">Sign In</button>
    
    <button onclick="document.getElementById('id02').style.display='block'" 
    class="hb-margin-btn hbb-btn  hbb-blue  hbb-large ">Sign Up</button>
    
   <!--  <a href="userRegistration1" class="hb-margin-btn hbb-btn hbb-blue hbb-large ">SignUp</a> -->
</div>	
</div> 
<!-- header is here -->

<!-- menu  is here -->
<div>
	<div class="hbb-bar hbb-amber hbb-card hbb-left-align hbb-medium">
    <a class="hbb-bar-item hbb-button hbb-hide-medium hbb-hide-large hbb-right hbb-padding-large hbb-hover-white hbb-large hbb-red"
       href="javascript:void(0);" onclick="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
    <a href="/HappyBus" class="hbb-bar-item hbb-button hbb-padding-large hbb-white">Home</a>
    <a href="printTicketForm" class="hbb-bar-item hbb-button hbb-hide-small hbb-padding-large hbb-hover-white">Print Ticket</a>
    <a href="cancelTicketForm" class="hbb-bar-item hbb-button hbb-hide-small hbb-padding-large hbb-hover-white">Cancel Ticket</a>
    <a onclick="document.getElementById('contact_frame_div').style.display='block'" class="hbb-bar-item hbb-button hbb-right hbb-hide-small hbb-padding-large hbb-hover-white ">Contact Us</a>
</div>
</div>
<!-- menu  is here -->
 
 
<!--  main body here -->
<div class="hbb-container hbb-card " >

<!-- body left -->
<div class="hb-nav">
<div  ng-show="search_bus_div">
	<img src="images/home-img.png" width="450px" height="400px">
</div>

</div>
${logoutStatus}
${status}
	
<!-- body right  -->
<div class="hb-article ">
	
<div class="" style="max-width: 500px">
<div class="hb-section hbb-margin "  ng-show="search_bus_div">
<div align="center">
<label class="hb-label-marign"
style="color: blue; font-family: verdana; font-size: 20px"><b>
SEARCH BUSES</b></label><br></div>
<label class="hb-label-marign"><b>Source</b></label>
<input class="hb-input hbb-border hbb-margin-bottom" type="text"
ng-model="source" placeholder="Enter Source" required/><br>
<label class="hb-label-marign"><b>Destination</b></label>
<input class="hb-input hbb-border hbb-margin-bottom" type="text"
					ng-model="destination"
					placeholder=" Enter Destination"  required/> <br>
<label class="hb-label-marign"><b>Date Of Journey</b></label>
<datepicker>
<input class="hb-input hbb-border hbb-margin-bottom"  type="text" ng-model="dateOfJourney" />
</datepicker><br>
<div align="center">
<button
class=" hb-label-marign hbb-button hbb-large hbb-green hb-section"
ng-click="searchBus()">Search</button>
</div>
</div>
	
</div>
	
</div>
<!-- 
<div style="color:blue; font-size: medium;" align="center">
					{{search_message}}</div><br> -->

					
<div  ng-hide="search_bus_div" >	
<br>
<div class="hbb-card" style="max-width: 450px">
<br>
<p>&nbsp;Source: <b>{{hb_src}}</b> &nbsp;</p>
<p>&nbsp;Destination: <b>{{hb_des}}</b>&nbsp;</p>
<p>&nbsp;Date of Journey: <b> {{hb_doj}}</b>&nbsp;</p>
<br>
</div>
<br>
 
 <div class="hbb-container hbb-card "><br>	
<table border="1"  >
		
<thead align="center">
<tr>
<th width="2px">&nbsp;Travels&nbsp; 
			   &nbsp;Name&nbsp;</th>
<th>&nbsp;BusInfo&nbsp;</th>
<th>&nbsp;Via&nbsp;</th>
<th>&nbsp;Price&nbsp;</th>
<th>&nbsp;Depature Time&nbsp;</th>
<th>&nbsp;Arrival Time&nbsp;</th>
<th width="1px">
&nbsp;Total&nbsp;
&nbsp;Seats&nbsp;</th>
<th width="1px">
&nbsp;Available&nbsp;
&nbsp;Seats&nbsp;
</th>
<th>&nbsp;Select Seats&nbsp;</th>
</tr>
</thead>
<tbody>
<tr data-ng-repeat="searchbus in searchbus1">
<td>{{searchbus.travelName}}</td>	
<td>{{searchbus.busTypeName}} +<br>{{searchbus.busModel}}</td>
<td>{{searchbus.via}}</td>
<td>{{searchbus.price}}</td>
<td>{{searchbus.departureTime}}</td>
<td>{{searchbus.arrivalTime}}</td>
<td >{{searchbus.totalNoOfSeats}}</td>
<td>{{searchbus.availableNoOfSeats}}</td>
<td>&nbsp;
	<button 
		class=" hb-margin-btn hbb-btn hbb-blue hbb-small"
						ng-click="selectseats()">Select Seats</button>
&nbsp;</td>	
</tr>
</tbody>
</table><br>	

</div>				
</div>
<br>
<!--  table end here -->

<div id="board_div" align="left" ng-show="hb_bor_drp_flag">
			<label class="hb-label-marign"><b>Boarding Points</b></label>
			 <select ng-model="selectedName1" ng-options="item for item in bor_array" style="width: 100px;">
			</select>
			<label class="hb-label-marign"><b>Dropping Points</b></label> 
			<select
				ng-model="selectedName2" ng-options="item for item in drp_array" style="width: 100px;">
			</select>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
			<button 
				class=" hb-margin-btn hbb-btn hbb-large hbb-lime "
				ng-click="showSeats()">Show Seats</button>
</div><br>
<div class="hbb-container" ng-show="flagg">

			<div class="hb-nav">

				<table border="0" cellspacing="2" cellpadding="0">

					<tbody ng-repeat="row in seatArray" ng-init="child = $index">

						<td ng-repeat="cols in row"><img width="25" height="25"
							ng-src="{{cols.src}}" ng-disabled="{{cols.disable}}"
							ng-click=" cols.disable || changeSeat($parent.$index,$index)">{{cols.name}}
						</td>
					</tbody>
				</table>
				<!-- <td ng-if="child >=1" ng-repeat="item in gap"></td>  -->
				<!-- <td ng-repeat="item in gap" >
{{if row.indexOf() >1 && row <2}}
 &nbsp;     
 {{/if}}
 </td> -->

				<br> <br>
				<table>
					<tr>
					<tr>
						<img width="25" height="25" ng-src="images/ic_availableseat.png">
						<lable>Available Seat</lable>
					</tr>
					<tr>
						<img width="25" height="25" ng-src="images/ic_unavailableseat.png">
						<lable>Booked Seat</lable>
					</tr>
					<tr>
						<img width="25" height="25" ng-src="images/ic_ladiesseat.png">
						<lable>Ladies Seat</lable>
					</tr>
					<tr>
						<img width="25" height="25" ng-src="images/ic_selectedseat.png">
						<lable>Selected Seat</lable>
					</tr>
					</tr>
				</table>
				<br>
				<div align="center" style="color: blue; font-size: medium;">
					<span style="color: blue; font-family: verdana; font-size: 18px">Your
						Total Price is: {{totalPrice}}</span>
				</div>
			</div>
			<div class="hb-article" align="center">
				<label class="hb-label-marign"
					style="color: blue; font-family: verdana; font-size: 20px"><b>Primary
						Passenger Details</b></label><br> <br>

				<div align="center">
					<label class="hb-label-marign"><b>Passenger Name</b></label> <input
						type="text" class="hb-inputtype hbb-border"
						ng-model="primaryPassenger" /><br> <label
						class="hb-label-marign"><b>Mobile</b></label> <input type="text"
						class="hb-inputtype hbb-border" ng-model="mobile"
						ng-blur="checkUserName()" /><br> <label
						class="hb-label-marign"><b>Email</b></label> <input type="text"
						ng-model="email" ng-blur="checkUserName()"
						class="hb-inputtype hbb-border" /><br>
						<label class="hb-label-marign"><b>Enter Coupon Code</b></label> <input
						type="text" class="hb-inputtype hbb-border"
						ng-model="couponcode" ng-blur="checkCouponCode()" /><br>
				</div><br>
				
				<table>
					<thead align="center">
						<tr>
							<th>&nbsp;Name&nbsp;</th>
							<th>&nbsp;Gender&nbsp;</th>
							<th>&nbsp;Age&nbsp;</th>
							<th>&nbsp;SeatNo&nbsp;</th>
						</tr>
					</thead>
					<tbody align="center">
						<tr data-ng-repeat="add in feilds">

							<td align="center"><input type="text" ng-model="add.name"
								style="width: 100px;"></td>
							<td align="center"><select ng-model="add.gender"
								style="width: 60px;">
									<option>Male</option>
									<option>Female</option>
							</select></td>

							<td align="center"><input type="text"   ng-maxlength="2" ng-model="add.age"
								style="width: 60px;"></td>

							<td align="center"><label> {{add.seatno}} </label></td>

						</tr>

					</tbody>
				</table><br><br>
				<button
					class=" hb-margin-btn hbb-btn hbb-large hb-label-marign  hbb-green "
					ng-click="continuee()">Continue</button><br>
			</div><br>
		</div><br>



</div>
<!--  main body end here -->




<!-- login div frame popup -->
<div id="login_frame_div" class="hbb-modal">
	<div class="hbb-modal-content hbb-card-4 hbb-animate-zoom" style="max-width: 600px">

		<div class="hbb-center">
			<br> <span onclick="document.getElementById('login_frame_div').style.display='none'" 
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <!-- <span
				onclick="document.getElementById('login_frame_div').style.display='none'"
				ng-show="flag1"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> --> <img src="images/logontitle.png"
				alt="HappyBus logo" style="width: 50%" class=" hbb-margin-top ">
		</div>
		<div align="center">${status}</div>
		<form class="hbb-container" id="user" action="loginUser" method="post">
			<div class="hb-section hbb-margin ">
				<label class="hb-label-marign"><b>Email </b></label> <input
					class="hb-input hbb-border hbb-margin-bottom" type="text"
					placeholder="Enter Email " name="userName" id="userName" required>
				<label class="hb-label-marign"><b>Password</b></label> <input
					class="hb-input hbb-border" type="password"
					placeholder="Enter Password" name="password" id="password" required>
				<input class="hbb-left hb-label-marign hb-checkbox hbb-margin-top"
					type="checkbox" checked="checked"><br>Remember Me <span
					class="hb-mar-left"><a href="forgotForm">Forgot
						Password.?</a> </span> <br>
				<button
					class=" hb-label-marign hbb-button hbb-large hbb-green hb-section"
					type="submit">Login</button>
			</div>
		</form>
	</div>
</div>
<!-- login div close frame popup -->

<!-- SignUp div frame popup -->

<div id="id02" 
	class="hbb-modal">
<div class="hbb-modal-content hbb-card-4 hbb-animate-zoom" style="max-width: 600px">
<div class="hbb-center">
<br> 
<!-- ng-click="closeWindow()" ng-show="flag2" -->
<span 
onclick="document.getElementById('id02').style.display='none'"
	class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
	title="Close Modal">×</span> 
<img src="images/logontitle.png"
				alt="HappyBus logo" style="width: 50%" class=" hbb-margin-top ">
</div>
<div style="color: red; font-size: medium;" align="center">
			{{reg_user_message}}</div>
<form class="hbb-container">
		<div class="hb-section hbb-margin ">
			<label class="hb-label-marign"><b>Email Id</b></label> 
			 <input class="hb-input hbb-border hbb-margin-bottom" type="text"
					ng-model="email" ng-blur="checkUser()"
					placeholder="Enter Email / Mobile" name="email" required/> 
					<label
					class="hb-label-marign"><b>Mobile Number</b></label> 
					
					<input
					class="hb-input hbb-border hbb-margin-bottom" type="text"
					ng-model="mobile" ng-blur="checkUser()"
					placeholder="Mobile Number" name="mobile" required/> <label
					class="hb-label-marign"><b>Password</b></label> <input
					class="hb-input hbb-border" type="password" ng-model="password"
					placeholder="Enter Password" name="psw" required/>
<br>
<button class=" hb-label-marign hbb-button hbb-large hbb-green hb-section"
					ng-click="registerPassengerDetails()">Register</button>

</div>
</form>
</div>
</div>
<!-- signup div close frame popup -->




<!-- bottom footer -->
	
<div class="hbb-card hb-mar-toponeper">
    <div class="">
        <img src="images/Banner.png" width="100%" height="130px">
    </div>
</div>
<div id="contact_frame_div" class="hbb-modal">
	<div class="hbb-modal-content hbb-card-4 hbb-animate-zoom"
		style="max-width: 600px">
		<div class="hbb-center">
			<br> <span class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> <span
				onclick="document.getElementById('contact_frame_div').style.display='none'"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span>
				 <img src="images/logontitle.png"
				alt="HappyBus logo" style="width: 50%" class=" hbb-margin-top ">
				
<br><br>
<div align="center" style="color:green;font-family:verdana;font-size:14px;">
			<span><b align="center" style="color:blue;font-family:verdana;font-size:15px;">For e-Ticketing/Refund Related Queries</b><br>
			E-Mail :online.support@happybus.com</span><br></div>
			
<div align="center" style="color:green;font-family:verdana;font-size:14px;">
		<span><b align="center" style="color:blue;font-family:verdana;font-size:15px;">For Other Queries</b><br>
		E-Mail : customercare@happybus.com <br>
		Phone No : 040 30102829</span><br>
			<span><b align="center" style="color:blue;font-family:verdana;font-size:15px;">Only For SMS & WhatsUp</b><br>		
				 (+91) 8499900900</span><br>
		</div>	
		<br>			
		</div>
		<br>
	</div>
</div> 
</body>
</html>