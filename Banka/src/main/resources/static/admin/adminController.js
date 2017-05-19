var app = angular.module('admin.controllers', []);

var currentCountryIndex = -1;
app.controller('adminController', ['$scope','adminService', '$location','$state',
  	function ($scope, adminService, $location,$state) {
		function checkRights() {
			adminService.checkRights().then(
				function (response) {
					if(response.data != "") {
						$scope.admin = response.data;
					}
				}, 
				function (response){
				    $location.path('login');
					alert("Greska");
				}
			);
		}
		checkRights();

		$scope.updateProfile = function () {
			adminService.updateProfile($scope.admin).then(
				function(response){
					$scope.admin = response.data;
				}, function (response){
					alert("Greska");
				}
			);
		}
	
	
	
	
	
//COUNTRIES
		$scope.findAllCountries = function () {   
			adminService.findAllCountries().then(
				function(response){
					$scope.allCountries = response.data;
				}, function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.saveCountry= function () {
			adminService.saveCountry($scope.country).then(
				function(response){
					$scope.allCountries.push(response.data);
				}, function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.findCountryById= function (countryId,index) { 
			adminService.findCountryById(countryId).then(
				function(response){
					$state.go("admin.countries.updateCountry", {});
					currentCountryIndex = index;
					$scope.countryForUpdate = response.data;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.updateCountryy = function() {
			adminService.updateCountry($scope.countryForUpdate).then(
				function(response){
					$scope.allCountries[currentCountryIndex] = response.data;
				}, 
				function (response){
					alert("Greska");					
				}
			);
		};
		
		$scope.deleteCountry = function(id,index) {
			adminService.deleteCountry(id).then(
				function(){
					$scope.allCountries.splice( index, 1 );
				}, 
				function (response){
					alert("Postoji naseljeno mesto koje je vezano za tu drzavu. Nije moguce trenutno izbrisati.");					}
			);
		};
		
		$scope.searchForCountry = function(){
			adminService.searchCountry($scope.country).then(
				function(response){
				    $scope.allCountries = response.data;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}
//POPULATED PLACES
		$scope.findAllPopulatedPlaces= function () {   
			adminService.findAllPopulatedPlaces().then(
				function(response){
					$scope.allPopulatedPlaces = response.data;
				}, function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.deletePopulatedPlace = function(id,index) {
			adminService.deletePopulatedPlace(id).then(
				function(){
					$scope.allPopulatedPlaces.splice( index, 1 );
				}, 
				function (response){
					alert("Postoji pravno lice koje posjeduje datu djelatnost. Nije moguce trenutno izbrisati.");					}
			);
		};
		
		$scope.findPopulatedPlaceById = function (populatedPlaceId) { 
			adminService.findPopulatedPlaceById(populatedPlaceId).then(
				function(response){
					$state.go("admin.populatedPlaces.updatePopulatedPlace", {});
					$scope.populatedPlaceForUpdate = response.data;
					$scope.selectedNameWhenChanged = response.data.country.name;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.setSelected = function(code) {
	        $scope.selected = code;
	        markRow(code);
	        
	        adminService.findCountryById($scope.selected).then(
				function(response){
					$scope.selectedName = response.data.name;
				}, function (response){
					alert("Morate odabrati drzavu!");
				}
			);
	    };	
	    
	    $scope.setSelectedWhenChanged = function(code) {
	        $scope.selectedForUpdate = code;
	        markRow(code);
	       
	    };
	    
	    function markRow(code) {   
	    	 var rows = document.getElementsByTagName('tr');
		        for(var i=0; i<rows.length; i +=1) {
		          rows[i].className = "";
		        }
		     element = document.getElementById(code);
		     element.setAttribute("class", "selectedRow");
		}
	    
	    $scope.saveChangedCountryForPopulatedPlace= function () {   
			$scope.previousSelectedForUpdate = $scope.selectedForUpdate;
			adminService.findCountryById($scope.selectedForUpdate).then(
				function(response){
					$scope.selectedNameWhenChanged = response.data.name;
				}, function (response){
					alert("Morate odabrati drzavu!");
				}
			 );
		}
		
		
	}
]);
