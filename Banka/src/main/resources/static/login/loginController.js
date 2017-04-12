var app = angular.module('login.controllers', []);

app.controller('loginController', ['$scope','loginService', '$location',
  	function ($scope, loginService, $location) {
	
		$scope.submitLogin = function () {            
			loginService.logIn($scope.user).then(
				function (response) {
                    $scope.state = undefined;
                    if(response.data !== null)
                    	$location.path('banker/home');                   
				},
                function (response) {
				    alert("Access denied!");
                }
			);
		}
		
		$scope.logOut = function() {
			loginService.logOut().then(
				function (response) {
					$location.path('login/logout');
	            }		
			)
		}		
}]);