var app = angular.module('banker.controllers', []);
 
app.controller('bankerController', ['$scope','bankerService', '$location',
  	function ($scope, bankerService, $location) {
	
		//provera da li je logovan ponudjac
		function checkRights() {
			bankerService.checkRights().then(
				function (response) {
					if(response.data != "") {
						$scope.banker = response.data;
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