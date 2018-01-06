<html ng-app="bankOperationsModule">
<head>
<script type="text/javascript" src="public/js/angular.js"></script>
 
 <link href="public/css/angular-datepicker.css" rel="stylesheet" type="text/css" />
<script src="public/js/angular-datepicker.js"></script>
 <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.6/angular-cookies.js"></script>
 <!-- <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.6/angular.js"></script>
  --><script type="text/javascript">
 var app=angular.module("bankOperationsModule",
		 ['720kb.datepicker','ngCookies']);
   app.controller("bankOperationsController",
	function($scope,$window,$http,$cookies){
	   $scope.depositWindowFlag=false;
	   $scope.accountFoundFlag=false;
	   $scope.accNumberButtonFlag=true;
	   $scope.chequeeWindowFlag=false;
	   $scope.ddWindowFlag=false;
	   $scope.getAccountBalanceFlag=false;
	   $scope.balanceInfoMessageFlag=false;
	   
	   
	   $scope.showDepositWindow=function(){
	   
	  $http({
	  method:"get",
	  url:"/CBA/deposit",
	  }).then(function(result){
		  $scope.accountNumber="";
		  $scope.depositWindowFlag=true;
		  $scope.accountFoundFlag=false;
		  $scope.accountNumberFlag=true;
		   $scope.accNumberButtonFlag=true;
		   $scope.chequeeWindowFlag=false;
		   $scope.ddWindowFlag=false;
		   $scope.depositAmtFlag=false;
		   $scope.getAccountBalanceFlag=false;
	  },function(result){
		  $window.alert("Unable to process your request!Please Try Again");  
	  });  
	   } 
	   
	  $scope.checkAccountStatus=function(){
		 
		  $http({
			method:"get",
			url:"checkAccountStatus",
			params:{
			"accountNumber":$scope.accountNumber	
			}
		  }).then(function(result){
		$scope.response=angular.fromJson(result.data);
		
		
		$scope.accountFoundFlag=true;
		$scope.amount="";
		$scope.txDesc="";
		$scope.number="";
		$scope.issuedBy="";
		$scope.issuedDate="";
		$scope.issuedBranch="";
		$scope.txMode="cash";
		if($scope.response.status=="SUCCESS"){
			$scope.message=$scope.response.message;	
		$scope.accountStatusInfo=angular.fromJson($scope.response.data);
      
		if($scope.accountStatusInfo.accountStatus=="ACTIVE"){
    	  $scope.depositAmtFlag=true;
    	  $scope.accNumberButtonFlag=false;
      }else{
    	 $scope.message=$scope.accountStatusInfo.accountNumber+" is "+$scope.accountStatusInfo.accountStatus;
      }
		}else{
		$scope.message=$scope.response.message;	
		}
		  },function(result){
			  $window.alert("Unable to process your request!please try again.");
		  }); 
	   }
	  
	  $scope.cash=function(){
		  $scope.txMode="cash";
		  
		  $scope.chequeeWindowFlag=false;
		  $scope.ddWindowFlag=false;
	  }
	  $scope.chequee=function(){
		  $scope.txMode="chequee";
		  $scope.chequeeWindowFlag=true;
		  $scope.ddWindowFlag=false;
	  }
	  $scope.dd=function(){
		  $scope.txMode="dd";
		  $scope.ddWindowFlag=true;
		  $scope.chequeeWindowFlag=false;
	  }
	  $scope.deposit=function(){
		  $window.alert( $cookies.get('XSRF-TOKEN'));
		 
		  $scope.ddORChequeeNumber="";
		  $scope.ddORChequeeIssuedBy="";
		  $scope.ddORChequeeIssuedDate="";
		  $scope.ddORChequeeIssuedBranch="";
	   if($scope.txMode=="chequee" || $scope.txMode=="dd"){
		   $scope.ddORChequeeNumber=$scope.number;
		   $scope.ddORChequeeIssuedBy=$scope.issuedBy;
		   $scope.ddORChequeeIssuedDate=$scope.issuedDate;
		   $scope.ddORChequeeIssuedBranch=$scope.issuedBranch;
	   }
	   $scope.csrf=$cookies.get('XSRF-TOKEN');
	  
	   $http({
		   method:"post",
		   url:"deposit",
		   headers: {
			   'X-CSRF-TOKEN': $scope.csrf
			   }, 
		   params:{
			   "accountNumber":$scope.accountNumber,
			   "txAmount":$scope.amount,
			   "txDesc":$scope.txDesc,
			   "ddORChequeeIssuedBranch":$scope.ddORChequeeIssuedBranch,
			   "txMode":$scope.txMode,
			   "ddORChequeeNumber":$scope.ddORChequeeNumber,
			   "ddORChequeeIssuedBy":$scope.ddORChequeeIssuedBy,
			   "ddORChequeeIssuedDate":$scope.ddORChequeeIssuedDate,
			   "_csrf":$scope.csrf
		   }
	   }).then(function(result){
		$scope.response=angular.fromJson(result.data);
		   if($scope.response.status=="SUCCESS"){
			   $scope.depositAmtFlag=false; 
			   $scope.chequeeWindowFlag=false;
			   $scope.ddWindowFlag=false;
			   $scope.accountNumberFlag=false;
			   $scope.message=$scope.response.message;
		   }else if($scope.response.status=="FAILURE"){
			   $scope.message=$scope.response.message;
		   }
		   else{
			   $window.alert($scope.response.message);
			   $window.location.href = '/CBA/login?error=Session_EXPIRED';
		   }
	   },function(result){
		   $window.alert("FAILURE "+result);
	   });
	  }
	  
	  $scope.showAccountBalanceWindow=function(){
		  $scope.depositWindowFlag=false;
		  $scope.balanceInfoMessageFlag=false;
		  $scope.getAccountBalanceFlag=true;
		  $scope.accountNumber="";
	  }
	  
	  $scope.getAccountBalance=function(){
		  $http({
		 method:"get",
		 url:"/CBA/getAccountBalance",
		 params:{
			 "accountNumber":$scope.accountNumber
		 }
		  }).then(function(result){
			  $scope.response=angular.fromJson(result.data);
			  $scope.balanceInfoMessageFlag=true;
			  if($scope.response.status=="SUCCESS"){
				  $scope.message=$scope.response.message;
				  $scope.totalBalance=$scope.response.dataList[0];
				  $scope.accountStatus=$scope.response.dataList[1];
				  
			   }else if($scope.response.status=="FAILURE"){
				   $scope.message=$scope.response.message;
			   }
			   else{
				   $window.alert($scope.response.message);
				   $window.location.href = '/CBA/login?error=Session_EXPIRED';
			   }
		  },function(result){
			 $window.alert("Unable to Process Your Request !Please Try Again."); 
		  });
		  
		  
	  }
   });
</script>
</head>
<body ng-controller="bankOperationsController">

<div >
<button type="button" ng-click="showDepositWindow()">Deposit</button>
</div>
<div >
<button type="button" ng-click="showAccountBalanceWindow()">BalanceEnquiry</button>
</div>
<div ng-show="depositWindowFlag" align="center">
  <label>Deposit Window</label>
  <table border="1">
  <tr ng-show="accountFoundFlag">
 <td colspan="3" align="center"> {{message}}</td>
  </tr>
  <tr ng-show="accountNumberFlag">
  <td><label>Enter Account Number : </label>
  <input type="text" ng-model="accountNumber"/></td>
  <td ng-show="accNumberButtonFlag"><button ng-click="checkAccountStatus()">Click</button></td>
  </tr>
  <tr ng-show="depositAmtFlag">
  <td><label>Select Transaction Mode : </label>
 <input type="radio" ng-click="cash()" value="cash" ng-model="txMode">CASH</input>
 
 <input type="radio" ng-click="chequee()" value="chequee"  ng-model="txMode">CHEQUEE</input>
<input type="radio" ng-click="dd()"  value="dd"  ng-model="txMode">DD</input></td>
  </tr>
  <tr ng-show="depositAmtFlag">
  <td><label>Enter Deposit Amount : </label>
 <input type="text" ng-model="amount"/></td>
  </tr>
  <tr ng-show="depositAmtFlag">
  <td><label>Enter Transaction Desc : </label>
 <input type="text" ng-model="txDesc"/></td>
  </tr>
  <tr ng-show="chequeeWindowFlag">
  <td>Enter Chequee Number : <input type="text" ng-model="number"/><br/>
<datepicker date-format="dd-MM-yyyy">
 Enter Chequee Issued Date :
	&nbsp;&nbsp; <input ng-model="issuedDate" type="text" /> </datepicker><br/>
  Enter Issued By  : <input type="text" ng-model="issuedBy"/><br/>
  Enter Issued Branch  : <input type="text" ng-model="issuedBranch"/><br/>
  
  </td>
  </tr>
  <tr ng-show="ddWindowFlag">
  <td>Enter DD Number : 
  <input type="text" ng-model="number"/><br/>
  
 <datepicker date-format="dd-MM-yyyy">
 Enter DD Issued Date :
	&nbsp;&nbsp; <input ng-model="issuedDate" type="text" /> </datepicker><br/>
<br/>
 Enter DD By  : 
  <input type="text" ng-model="issuedBy"/><br/>
  Enter Issued Branch  : 
  <input type="text" ng-model="issuedBranch"/><br/>
  </td>
  </tr>
  
  <tr ng-show="depositAmtFlag">
  <td><button ng-click="deposit()">Deposit</button></td>
</tr>
  </table>
</div>
<div align="center">
<table ng-show="getAccountBalanceFlag" border="1">
  <tr><th align="center" colspan="2">View Account Balance</th></tr>
 <tr ng-show="balanceInfoMessageFlag">
 <td colspan="2" align="center"> {{message}}</td>
 </tr>
 <tr ng-show="balanceInfoMessageFlag">
 <td colspan="2" align="center"> Total Balance : {{totalBalance}}</td>
 </tr>
 <tr ng-show="balanceInfoMessageFlag">
 <td colspan="2" align="center"> Account Status : {{accountStatus}}</td>
 </tr>
  <tr>
 
  <td>
  <input type="text" ng-model="accountNumber" placeholder="Enter Account Number"/>
  </td>
   <td>
  <input type="submit" value="GetAccountBalance" ng-click="getAccountBalance()"/>
  </td>
  </tr>
  </table>
</div>
</body>
</html>


