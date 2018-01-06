<html ng-app="BookingApp">
<head>
<%@include file="includers.jsp"%>
<script
	src="//ajax.googleapis.com/ajax/libs/angularjs/1.3.8/angular-sanitize.js"></script>
<script type="text/javascript" src="js/checklist-model.js"></script>
<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="js/jquery.redirect.js"></script>

<!-- <script type="text/javascript" src="js/angular-ui-router.js"></script>
 -->

<script type="text/javascript">

//This Module From angular to work with Different Functions

var app = angular.module("BookingApp", ["checklist-model","ngSanitize"]);

app.controller("BookingCtr", ['$scope', '$window','$http',function($scope,$window,$http) {
	$scope.totalPrice=0; 
	$scope.price;
	$scope.hbflag=false;
//This Function Used To Select The Seats From SeatLayOut 1
$scope.selectseats = function() {
	$http({
		method:"post",
		url:"/HappyBus/boardingDetails",
		params:{
			'routeId':$scope.routeId/* ,
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
			$scope.hbflag=true;
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
	   		 	'routeId':1002 ,
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
	//This Function Used To ShowSeat Layout 6
	
	$scope.flagg=false;
	
	$scope.showSeats=function(){
		$scope.showSeatLayoutList = [];
		$scope.selectedSeats = [];
	
    	$http({
    		method:"get",
    		url:"/HappyBus/seatLayoutDetails",
    		params:{
    			'routeId':1002
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
					if($scope.response.status=="EXCEPTION" || $scope.response.status=="FAILURE"){
						$scope.message=$scope.response.message;
						$scope.data=$scope.response.data;				
					    $scope.userId=angular.fromJson($scope.data).userId;
					   }
					else{
						//redirect to registration page
						$window.location.href="/HappyBus/userRegistration1";
					}
				},
				function(response) {
				
				}); 
			}
		}		
	}
}]);

</script>
</head>
<body class="hbb-container">
	<img class="hb-ddown" style="float: left;" src="images/logontitle.png"
		width="500px" height="150px">
	<div>
		<%@include file="happy_menu.jsp"%>
	</div><br><br>
	<div ng-controller="BookingCtr">
		<div align="center">
			<button 
				class=" hb-margin-btn hbb-btn hbb-blue hbb-large"
				ng-click="selectseats()">Select Seats</button>
		</div><br>
		<div id="board_div" align="left" ng-show="hbflag">
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
		<!-- <div class="hbb-card hb-mar-toponeper">
    <div class="">
        <img src="images/Banner.png" width="100%" height="120px">
    </div> -->
</body>
</html>