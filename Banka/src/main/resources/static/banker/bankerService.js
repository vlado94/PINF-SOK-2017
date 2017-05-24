var services = angular.module('banker.services', ['ngResource']);

services.service('bankerService', ['$http', function($http){

	var url = "/banker/";	
	
	this.checkRights = function(){
		return $http.get(url+"checkRights");
	}
	
	this.updateProfile = function(banker){
		return $http.put(url+"updateProfile/"+banker.id,banker);
	}

//CODE BOOK ACTIVITIES
	this.findAllCodeBookActivities = function(){
		return $http.get("/codeBookActivities");
	}
	
	this.findActivityById = function(id){
		return $http.get("/codeBookActivities/"+id);
	}
	
	this.findActivityByName = function(activityName){	
		return $http.get("/codeBookActivities/findByName/"+activityName);
	}
	
	this.searchCodeBookActivity = function(codeBookActivity){
		return $http.post("/codeBookActivities/search",codeBookActivity);
	}


//COUNTRIES
	this.findAllCountries = function(){
		return $http.get("/country");
	}
	this.searchCountry = function(country){
		return $http.post("/country/search",country);
	}

//EXCHANGE RATE
	this.exchangeRateDetails = function(id){
		return $http.get("/exchangeRate/"+id);
	}
	this.findAllExchangeRate = function(){
		return $http.get("/exchangeRate");
	}

//POPULATED PLACES
	this.findAllPopulatedPlaces = function(){
		return $http.get("/populatedPlaces");
	}
	
	this.searchPopulatedPlace = function(populatedPlace){
		return $http.post("/populatedPlaces/search",populatedPlace);
	}
	
//INDIVIDUAL BILLS
	this.findAllIndividualBills = function(){
		return $http.get("/bill/findAllIndividualBills");
	}
	
	this.findAllLegalBills = function(){
		return $http.get("/bill/findAllLegalBills");
	}
	
	this.updateIndividualClient = function(individualClient){
		return $http.put("/client/updateIndividualClient/"+individualClient.id,individualClient);
	}
	
	this.findClientById = function(clientId){
		return $http.get("/client/"+clientId);
	}
	
	this.updateLegalClient = function(legalClient){
		return $http.put("/client/updateLegalClient/"+legalClient.id, legalClient);
	}
	
	this.saveLegalBill = function(legalPerson){
		return $http.post("/client/saveLegalBill",legalPerson);
	}


//BILLS	
	this.saveBill = function(bill){
		return $http.post("/bill/",bill);
	}

	this.findBillsForAllBanks = function(id){
		return $http.get("/bill/findBillsForAllBanks/"+id);
	}	
	
	this.closeBill = function(closingBill){
		return $http.post(url+"closeBill",closingBill);
	}

//DEPOSIT SLIPS
	this.saveDepositSlip = function(depositSlip){
		return $http.post("depositSlip/saveDepositSlip",depositSlip);
	}
	
	this.findAllDepositSlips = function(){
		return $http.get("depositSlip/findAllDepositSlips");
	}	
	
}]);