var app = angular.module('banker.controllers', []);
 
app.controller('bankerController', ['$scope','bankerService', '$location',
  	function ($scope, bankerService, $location) {
	
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
		
		
		$scope.update = function () {
			
			bankerService.update($scope.banker).then(
					function(response){
						alert("Ok zavrseno");
					}, function (response){
						alert("Greska");
					}
			);
		}
		
		
		$scope.getAllCodeBookActivities= function () {   
			bankerService.getAllCodeBookActivities().then(
					function(response){
						
						$scope.allcodeBookActivities = response.data;
					}, function (response){
						alert("Greska");
					}
			);
		}
		
		$scope.addNewCodeBookActivity= function () {   
			bankerService.addActivity($scope.codeBookActivity).then(
					function(){
						alert("Odgovor");
					}, function (response){
						alert("Greska");
					}
			);
		}
		

		
		$scope.getAllCountries= function () {   
			bankerService.getAllCountries().then(
					function(response){
						
						$scope.allCountries = response.data;
					}, function (response){
						alert("Greska");
					}
			);
		}
		
		$scope.addNewCountry= function () {
			bankerService.addNewCountry($scope.country).then(
					function(){
						alert("Odgovor");
					}, function (response){
						alert("Greska");
					}
			);
		}
		

		
		
	}
]);