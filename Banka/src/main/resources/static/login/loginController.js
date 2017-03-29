var app = angular.module('login.controllers', []);
 
var firstLoginId = null;

app.controller('loginController', ['$scope','loginService', '$location',
  	function ($scope, loginService, $location) {
	
		$scope.submitLogin = function () {            
			loginService.logIn($scope.user).then(
				function (response) {
                    $scope.state = undefined;
                    if(response.data === "clientPersonal")
                    	$location.path('loggedIn/clientPersonal/home');
                    else if(response.data === "banker")
                    	$location.path('loggedIn/banker/home');
                    
				},
                function (response) {
                    alert("Ne postoji korisnik sa tim parametrima.");
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