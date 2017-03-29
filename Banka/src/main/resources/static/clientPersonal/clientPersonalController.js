var app = angular.module('clientPersonal.controllers', []);
 
app.controller('clientPersonalController', ['$scope','clientPersonalService', '$location',
  	function ($scope, clientPersonalService, $location) {
	
		//provera da li je logovan ponudjac
		function checkRights() {
			clientPersonalService.checkRights().then(
				function (response) {
					if(response.data != "") {
						$scope.clientPersonal = response.data;
					}
					else {
					    $location.path('login');
					    alert("Access denied!");
				    }
				}
			);
		}
		checkRights();
	}
]);