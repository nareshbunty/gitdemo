<!DOCTYPE html>
<html ng-app="myApp">
<head>
<%@include file="includers.jsp"%>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script> 

<script type="text/javascript">
	var app = angular.module("myApp", []);
	$scope.sel="";
	app.controller('myCtrl', ['$scope', function($scope) {
		 $scope.var1 = "true";
	     $scope.var2 = "true";
	     $scope.var3 = "true";
		 $scope.var4 = "true";
	}]);
	
	
	
</script>
</head>
<div id="seat_layout" ng-controller="myCtrl" class="hbb-modal">

<!--  <div class="hbb-modal-content hbb-card-4 hbb-animate-zoom"
		style="max-width: 600px">

		<div class="hbb-center">
			<br> 
			<span click="document.getElementById('seat_layout').style.display='none'"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> 
				<img src="images/logontitle.png"
				alt="HappyBus logo" style="width: 50%" class=" hbb-margin-top ">
		</div>
		<br><p>Select Your Seat</p>
		
		</div>  -->



	<div class="hbb-modal-content hbb-card-4 hbb-animate-zoom"
		style="max-width: 600px">

		<div class="hbb-center">
			<br> 
			<span
				onclick="document.getElementById('seat_layout').style.display='none'"
				class="hbb-button hbb-xlarge hbb-transparent hbb-display-topright"
				title="Close Modal">×</span> 
				<img src="images/logontitle.png"
				alt="HappyBus logo" style="width: 50%" class=" hbb-margin-top ">
				
				<br><p>Select Your Seat</p>
		
		<br>

	<table  align="center" border="2" cellspacing="1" cellpadding="2" class="">
<tbody>
 <tr>
<td><input type="checkbox" ng-disabled="true" id="1" /><label for="1">1</label></td>												 
<td><input type="checkbox" ng-disabled="var5" id="5" /><label for="5">5</label></td>
<td><input type="checkbox" ng-disabled="var9" id="9" /><label for="9">9</label></td>
<td><input type="checkbox" ng-disabled="var13" id="13" /><label for="13">13</label></td>
<td><input type="checkbox" ng-disabled="var17" id="17" /><label for="17">17</label></td>
<td><input type="checkbox" ng-disabled="var21" id="21" /><label for="21">21</label></td>
<td><input type="checkbox" ng-disabled="var25" id="25" /><label for="25">25</label></td>
<td><input type="checkbox" ng-disabled="var29" id="29" /><label for="29">29</label></td>
<td><input type="checkbox" ng-disabled="var33" id="33" /><label for="33">33</label></td>
<td><input type="checkbox" ng-disabled="var37" id="37" /><label for="37">37</label></td>
</tr>
<tr>
<td><input type="checkbox" ng-disabled="var2" id="2" /><label for="2">2</label></td>												 
<td><input type="checkbox" ng-disabled="var6" id="6" /><label for="6">6</label></td>
<td><input type="checkbox" ng-disabled="var10" id="10" /><label for="10">10</label></td>
<td><input type="checkbox" ng-disabled="var14" id="14" /><label for="14">14</label></td>
<td><input type="checkbox" ng-disabled="var18" id="18" /><label for="18">18</label></td>
<td><input type="checkbox" ng-disabled="var22" id="22" /><label for="22">22</label></td>
<td><input type="checkbox" ng-disabled="var26" id="26" /><label for="26">26</label></td>
<td><input type="checkbox" ng-disabled="var30" id="30" /><label for="30">30</label></td>
<td><input type="checkbox" ng-disabled="var34" id="34" /><label for="34">34</label></td>
<td><input type="checkbox" ng-disabled="var38" id="38" /><label for="38">38</label></td>
</tr>
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td><input type="checkbox" ng-disabled="var3" id="3" /><label for="3">3</label></td>												 
<td><input type="checkbox" ng-disabled="var7" id="7" /><label for="7">7</label></td>
<td><input type="checkbox" ng-disabled="var11" id="11" /><label for="11">11</label></td>
<td><input type="checkbox" ng-disabled="var15" id="15" /><label for="15">15</label></td>
<td><input type="checkbox" ng-disabled="var9" id="19" /><label for="19">19</label></td>
<td><input type="checkbox" ng-disabled="var23" id="23" /><label for="23">23</label></td>
<td><input type="checkbox" ng-disabled="var27" id="27" /><label for="27">27</label></td>
<td><input type="checkbox" ng-disabled="var31" id="31" /><label for="31">31</label></td>
<td><input type="checkbox" ng-disabled="var35" id="35" /><label for="35">35</label></td>
<td><input type="checkbox" ng-disabled="var39" id="39" /><label for="39">39</label></td>
</tr>
<tr>
<td><input type="checkbox" ng-disabled="var4" id="4" /><label for="4">4</label></td>												 
<td><input type="checkbox" ng-disabled="var8" id="8" /><label for="8">8</label></td>
<td><input type="checkbox" ng-disabled="var12" id="12" /><label for="12">12</label></td>
<td><input type="checkbox" ng-disabled="var16" id="16" /><label for="16">16</label></td>
<td><input type="checkbox" ng-disabled="var20" id="20" /><label for="20">20</label></td>
<td><input type="checkbox" ng-disabled="var24" id="24" /><label for="24">24</label></td>
<td><input type="checkbox" ng-disabled="var28" id="28" /><label for="28">28</label></td>
<td><input type="checkbox" ng-disabled="var32" id="32" /><label for="32">32</label></td>
<td><input type="checkbox" ng-disabled="var36" id="36" /><label for="36">36</label></td>
<td><input type="checkbox" ng-disabled="var40" id="40" /><label for="40">40</label></td>
</tr>

</table>
	
<tr>
<td>&nbsp;</td>
</tr>
<div>
Selected Seats: {{sel}}
</div> 

</div>

</div>
		</div> 

</html>

