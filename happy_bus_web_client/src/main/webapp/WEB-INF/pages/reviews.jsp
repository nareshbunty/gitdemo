<!DOCTYPE html>
<html lang="en">
<head>
  <title>HappyBus Reviews</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="js/angular.js"></script>
 <style type="text/css">
 .rating {
    color: #a9a9a9;
    margin: 0;
    padding: 0;
}
ul.rating {
    display: inline-block;
}
.rating li {
    list-style-type: none;
    display: inline-block;
    padding: 1px;
    text-align: center;
    font-weight: bold;
    cursor: pointer;
}
.rating .filled {
    color: red;
}
 </style>
 <script type="text/javascript">
 var starApp = angular.module('starApp', []);

 starApp.controller('StarCtrl', ['$scope','$window', '$http', function ($scope,$window, $http) {
	 $window.alert("fsdgdgh ");
	 $http.get("getReviews").then(function(response) {
					$scope.response = response.data;
					$scope.reviews = angular.fromJson($scope.response.data);
					
				});
	 
 }]);

 //no change
 starApp.directive('starRating', function () {
     return {
         restrict: 'A',
         template: '<ul class="rating">' +
             '<li ng-repeat="star in stars" ng-class="star">' +
             '\u2605' +
             '</li>' +
             '</ul>',
         scope: {
             ratingValue: '=',
             max: '=',
             onRatingSelected: '&'
         },
         link: function (scope, elem, attrs) {

             var updateStars = function () {
                 scope.stars = [];
                 for (var i = 0; i < scope.max; i++) {
                     scope.stars.push({
                         filled: i < scope.ratingValue
                     });
                 }
             };
             scope.$watch('ratingValue', function (oldVal, newVal) {
                 if (newVal) {
                     updateStars();
                 }
             });
         }
     }
 });
 </script>




</head>

<body ng-app="starApp">
 
<div  ng-controller="StarCtrl"> 
<div class="container">
  <h2>Reviews</h2>
  <p>HappyBus.in is India's largest online bus ticketing platform, trusted by over 6 million Indians.
   With a sale of over 6,00,00,000 tickets via web, mobile, and our bus agents, HappyBus stands at the top of the game in bus ticketing. 
   redBus.in operates on over 70000 routes and is associated with 2300 reputed bus operators. Try the HappyBus experience today.</p>
 <div ng-repeat="review in reviews">  
     <div class="panel-group">
          <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-user"></i>&emsp; {{review.userName}}
                  <div star-rating rating-value="review.rating" max="5"></div>
                </div>
            <div class="panel-body" style="font-style: oblique;">{{review.feedbackType}}</div>
               <div style="padding-left: 5em; font-size:small; ;">
                     {{review.feedbackDesc}}
               </div>
       </div>
   
      </div><!-- pannel end tag -->
      </div>
  </div>
</div>
</body>
</html>
