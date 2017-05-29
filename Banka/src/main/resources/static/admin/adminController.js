var app = angular.module('admin.controllers', []);

var currentCountryIndex = -1;
var currentCodeBookActivityIndex = -1;
var currentPopulatedPlaceIndex = -1;
app.controller('adminController', ['$scope','adminService', '$location','$state',
  	function ($scope, adminService, $location,$state) {
		function checkRights() {
			adminService.checkRights().then(
				function (response) {
					if(response.data != "") {
						$scope.admin = response.data;
						$scope.currentExchangeRate = $scope.admin.bank.exchangeRates[$scope.admin.bank.exchangeRates.length - 1];
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
		
		$scope.resetSearchCountry = function(){
			$scope.findAllCountries();
			$scope.country=null;
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
		
		$scope.findPopulatedPlaceById = function (populatedPlaceId,index) { 
			adminService.findPopulatedPlaceById(populatedPlaceId).then(
				function(response){
					currentPopulatedPlaceIndex = index;
					$state.go("admin.populatedPlaces.updatePopulatedPlace", {});
					$scope.populatedPlaceForUpdate = response.data;		
					$scope.selectedNameWhenChanged = response.data.country.name;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}
		
	    $scope.setSelectedWhenChanged = function(id) {
	        $scope.selectedForUpdate = id;
	        markRow(id);
	       
	    };
	    
	    function markRow(code) {   
	    	 var rows = document.getElementsByTagName('tr');
		        for(var i=0; i<rows.length; i +=1) {
		          rows[i].className = "";
		        }
		     element = document.getElementById(code);
		     element.setAttribute("class", "selectedRow");
		}
	    
	   
		
	    $scope.setSelected = function(code) {
	        $scope.selected = code;
	        markRow(code);
	        
	        adminService.findCountryById($scope.selected).then(
				function(response){
					$scope.selectedNameForAdd = response.data.name;
				}, function (response){
					alert("Morate odabrati drzavu!");
				}
			);
	    };	
	    
	    $scope.saveCountryForPopulatedPlace= function () {   
	    	$scope.selectedName = $scope.selectedNameForAdd
		}
	    
	    $scope.savePopulatedPlace= function () {
	    	adminService.findCountryById($scope.selected).then(
				function(response){
					$scope.populatedPlace.country = response.data;
					adminService.savePopulatedPlace($scope.populatedPlace).then(
						function(response){
							$scope.allPopulatedPlaces.push(response.data);
						}, function (response){
							alert("Greska pri cuvanju naseljenog mjesta");
						}
					);
				}, function (response){
					alert("Morate odabrati drzavu!");
				}
			);
		}
	    
	    $scope.saveChangedCountryForPopulatedPlace= function () {   
	    	adminService.findCountryById($scope.selectedForUpdate).then(
				function(response){
					$scope.selectedNameWhenChanged = response.data.name;
				}, function (response){
					alert("Morate odabrati drzavu!");
				}
			 );
		}
	    
	    $scope.updatePopulatedPlacee = function() {
	    	adminService.findCountryByName($scope.selectedNameWhenChanged).then(
				function(response){
					$scope.populatedPlaceForUpdate.country = response.data;
					adminService.updatePopulatedPlace($scope.populatedPlaceForUpdate).then(
						function(response){
							 $scope.allPopulatedPlaces[currentPopulatedPlaceIndex] = response.data;
						}, function (response){
								alert("Morate odabrati drzavu!");
						}
					);
				}, function (response){
					alert("Morate odabrati drzavu!");
				}
			);
		};
	    
	    $scope.searchPopulatedPlacesState = function () {
			$state.go("admin.populatedPlaces.searchPopulatedPlaces", {});
	    }
	    
	    $scope.searchForPopulatedPlace = function(){
	    	adminService.searchPopulatedPlace($scope.populatedPlace).then(
				function(response){
				    $scope.allPopulatedPlaces = response.data;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}	    
	    $scope.resetSearchPopulatedPlace = function(){
			$scope.findAllPopulatedPlaces();
			$scope.populatedPlace=null;
		}
//CODE BOOK ACTIVITIES
	    $scope.findAllCodeBookActivities = function () { 
	    	adminService.findAllCodeBookActivities().then(
				function(response){
					$scope.allCodeBookActivities = response.data;
				}, function (response){
					alert("Greska");
				}
			);
		}
	    
	    $scope.saveCodeBookActivity= function () { 
	    	adminService.saveCodeBookActivity($scope.codeBookActivity).then(
				function(response){
					$scope.allCodeBookActivities.push(response.data);
				}, function (response){
					alert("Greska");
				}
			);
		}
	    
	    $scope.findCodeBookActivityById= function(codeBookActivityId,index) {
			$state.go("admin.codeBookActivities.updateCodeBookActivity", {});
	    	adminService.findActivityById(codeBookActivityId).then(
				function(response){
					currentCodeBookActivityIndex = index;
					$scope.codeBookActivityForUpdate = response.data;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}
	    
	    $scope.updateCodeBookActivityy = function() {
	    	adminService.updateCodeBookActivity($scope.codeBookActivityForUpdate).then(
				function(response){
					 $scope.allCodeBookActivities[currentCodeBookActivityIndex] = response.data;
				}, 
				function (response){
					alert("Greska");
				}
			);
		};
		
		$scope.deleteCodeBookActivity = function(id,index) {
			adminService.deleteCodeBookActivity(id).then(
				function(){
					$scope.allCodeBookActivities.splice( index, 1 );
				}, 
				function (response){
					alert("Postoji pravno lice koje posjeduje datu djelatnost. Nije moguce trenutno izbrisati.");					}
			);
		
		};
		
		$scope.searchCodeBookActivities = function(){
			adminService.searchCodeBookActivity($scope.codeBookActivity).then(
				function(response){
				    $scope.allCodeBookActivities = response.data;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}
		$scope.resetSearchCodeBookActivities = function(){
			$scope.findAllCodeBookActivities();
			$scope.codeBookActivity=null;
		}
	    
//EXCCHANGE RATE
		
		$scope.findAllExchangeRates = function() {
			adminService.findAllExchangeRate().then(
				function(response){
				    $scope.exchangeRates = response.data;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.exchangeRateDetails = function(exchangeRateId) {
			$state.go("admin.exchangeRates.exchangeRateDetails", {});
			adminService.exchangeRateDetails(exchangeRateId).then(
				function(response){
					$scope.exchangeRateDetail = response.data;
				}, function (response){
					alert("Error!");
				}
			);
		}
		
		$scope.searchForExchangeRates = function(){
			var exchangeRate = $scope.exchangeRate;
			//alert(exchangeRate.date+" "+exchangeRate.numberOfExchangeRate+" "+exchangeRate.startDate)
			adminService.searchExchangeRate(exchangeRate).then(
				function(response){
				    $scope.exchangeRates = response.data;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.resetSearchExchangeRates = function(){
			$scope.findAllExchangeRates();
			$scope.exchangeRate=null;
		}
		$scope.getCurrentExchangeRate = function () {
			$scope.newCurrentExchange = $scope.currentExchangeRate;
		}
		
		$scope.addExchangeRatee = function() {
			exchangeInCurrencies = $scope.newCurrentExchange.exchangeInCurrencies;
			exchangeInCurrencies2 = [];
			for(var i =0;i<exchangeInCurrencies.length;i++) {
				object = exchangeInCurrencies[i];
				exPurchase = "exPurchase"+object.currency.code;
				exMid = "exMid"+object.currency.code;
				exSale = "exSale"+object.currency.code;
				purchase = document.getElementById(exPurchase).value;
				mid = document.getElementById(exMid).value;
				sale = document.getElementById(exSale).value;
				var exchangeInCurrency = {}
				exchangeInCurrency.serialNumber = 999;
				exchangeInCurrency.purchasingRate = purchase;
				exchangeInCurrency.middleRate = mid;
				exchangeInCurrency.saleRate = sale;
				exchangeInCurrency.currency = object.currency;
				exchangeInCurrencies2.push(exchangeInCurrency);
			}
			
			$scope.exchangeRate.exchangeInCurrencies = exchangeInCurrencies2;
			$scope.exchangeRate.date = new Date();
			adminService.saveExchangeRate($scope.exchangeRate).then(
				function(response){
					alert("Successfull added.");

					$scope.exchangeRates.push(response.data);
					$state.go("admin.exchangeRates", {});
					$scope.currentExchangeRate = response.data;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}
	}
]);
