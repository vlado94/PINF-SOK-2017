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
					$scope.banker = response.data;
				}, function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.findAllCodeBookActivities= function () {   
			bankerService.findAllCodeBookActivities().then(
				function(response){
					$scope.allcodeBookActivities = response.data;
				}, function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.saveCodeBookActivity= function () { 
			bankerService.saveCodeBookActivity($scope.codeBookActivity).then(
				function(){
					alert("Odgovor");
				}, function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.findAllCountries= function () {   
			bankerService.findAllCountries().then(
				function(response){
					$scope.allCountries = response.data;
				}, function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.saveCountry= function () {
			bankerService.saveCountry($scope.country).then(
				function(){
					alert("Odgovor");
				}, function (response){
					alert("Greska");
				}
			);
		}		
		
		$scope.saveIndividualPerson= function () {
			bankerService.saveIndividualPerson($scope.individualPerson).then(
				function(){
					alert("Odgovor");
				}, function (response){
					alert("Greska");
				}
			);
		}	
		
		$scope.saveLegalPerson= function () {
			bankerService.saveLegalPerson($scope.legalPerson).then(
				function(){
					alert("Odgovor");
				}, function (response){
					alert("Greska");
				}
			);
		}	
		
		$scope.findAllPopulatedPlaces= function () {   
			bankerService.findAllPopulatedPlaces().then(
				function(response){
					$scope.allPopulatedPlaces = response.data;
				}, function (response){
					alert("Greska");
				}
			);
		}
		$scope.savePopulatedPlace= function () {
			bankerService.savePopulatedPlace($scope.populatedPlace).then(
				function(){
					alert("Odgovor");
				}, function (response){
					alert("Greska");
				}
			);
		}	
		
		
	}
]);