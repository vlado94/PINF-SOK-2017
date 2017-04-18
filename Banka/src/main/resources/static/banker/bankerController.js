var app = angular.module('banker.controllers', []);
 
app.controller('bankerController', ['$scope','bankerService', '$location',
  	function ($scope, bankerService, $location) {
		function checkRights() {
			bankerService.checkRights().then(
				function (response) {
					if(response.data != "") {
						$scope.banker = response.data;
						$scope.currentExchangeRate = $scope.banker.bank.exchangeRates[$scope.banker.bank.exchangeRates.length - 1];
	
						findAllCodeBookActivities();
					}
					else {
					    $location.path('login');
					    alert("Access denied!");
				    }
				}
			);
		}
		
		checkRights();

		$scope.updateProfile = function () {
			bankerService.updateProfile($scope.banker).then(
				function(response){
					$scope.banker = response.data;
				}, function (response){
					alert("Greska");
				}
			);
		}
		
		function findAllCodeBookActivities() {   
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
		
		$scope.prepareToUpdateCodeBookActivity= function (codeBookActivity) { 
			$scope.codeBookActivityForUpdate = codeBookActivity;
		}
		
		$scope.updateCodeBookActivityy = function() {
			bankerService.updateCodeBookActivity($scope.codeBookActivityForUpdate).then(
				function(){
					location.reload();
				}, 
				function (response){
					alert("Greska");
				}
			);
		};
		
		$scope.deleteCodeBookActivity = function(id) {
			bankerService.deleteCodeBookActivity(id).then(
				function(){
					 location.reload();
				}, 
				function (response){
					alert("Postoji pravno lice koje posjeduje datu djelatnost. Nije moguce trenutno izbrisati.");					}
			);
		};
		
		$scope.findAllCountries = function () {   
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
		
		$scope.prepareToUpdateCountry= function (country) { 
			$scope.countryForUpdate = country;
		}
		
		$scope.updateCountryy = function() {
			bankerService.updateCountry($scope.countryForUpdate).then(
				function(){
					 location.reload();
				}, 
				function (response){
					alert("Greska");					}
			);
		};
		
		$scope.deleteCountry = function(id) {
			bankerService.deleteCountry(id).then(
				function(){
					 location.reload();
				}, 
				function (response){
					alert("Postoji naseljeno mesto koje je vezano za tu drzavu. Nije moguce trenutno izbrisati.");					}
			);
		};
		
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
		
		
		$scope.saveCountryForPopulatedPlace= function () {   
			$scope.previousSelected = $scope.selected;
		}
		
		$scope.annulCountryForPopulatedPlace= function () {   
			$scope.selected = $scope.previousSelected;
		}
		
		$scope.savePopulatedPlace= function () {
			bankerService.findCountryById($scope.selected).then(
				function(response){
					var country = response.data;
					var place  = $scope.populatedPlace;
					place.country = country;
					bankerService.savePopulatedPlace(place).then(
						function(){
							alert("Odgovor");
						}, function (response){
							alert("Greska");
						}
					);
				}, function (response){
					alert("Morate odabrati drzavu!");
				}
			);
		}
		
		
		$scope.deletePopulatedPlace = function(id) {
			bankerService.deletePopulatedPlace(id).then(
				function(){
					 location.reload();
				}, 
				function (response){
					alert("Postoji pravno lice koje posjeduje datu djelatnost. Nije moguce trenutno izbrisati.");					}
			);
		};
		
		$scope.prepareToUpdatePopulatedPlace= function (populatedPlace) { 
			$scope.populatedPlaceForUpdate = populatedPlace;
		}
		
		$scope.updatePopulatedPlacee = function() {
			bankerService.updatePopulatedPlace($scope.populatedPlaceForUpdate).then(
				function(){
					 location.reload();
				}, 
				function (response){
					alert("Greska");					}
			);
		};
		
		$scope.setSelected = function(code) {
	        $scope.selected = code;
	    };		
	}
]);