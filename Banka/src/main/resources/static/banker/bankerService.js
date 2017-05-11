var services = angular.module('banker.services', ['ngResource']);

services.service('bankerService', ['$http', function($http){

	var url = "/banker/";	
	
	this.checkRights = function(){
		return $http.get(url+"checkRights");
	}
	
	this.updateProfile = function(banker){
		return $http.put(url+"updateProfile/"+banker.id,banker);
	}
	
	this.findAllCodeBookActivities = function(){
		return $http.get(url+"findAllCodeBookActivities");
	}
	this.findActivityById = function(activityId){
		return $http.get(url+"findActivityById/"+activityId);
	}
	this.findActivityByName = function(activityName){
		return $http.get(url+"findActivityByName/"+activityName);
	}
	
	this.saveCodeBookActivity = function(codeBookActivity){
		return $http.post(url+"saveCodeBookActivity",codeBookActivity);
	}
	
	this.updateCodeBookActivity = function(codeBookActivity){
		return $http.put(url+"updateCodeBookActivity/"+codeBookActivity.id,codeBookActivity);
	}
	this.deleteCodeBookActivity = function(id){
		return $http.delete(url+"deleteCodeBookActivity/"+id);
	}
	
	this.findAllCountries = function(){
		return $http.get(url+"findAllCountries");
	}
	this.findCountryById = function(countryId){
		return $http.get(url+"findCountryById/"+countryId);
	}
	this.findCountryByName = function(countryName){
		return $http.get(url+"findCountryByName/"+countryName);
	}
	this.updateCountry = function(country){
		return $http.put(url+"updateCountry/"+country.id,country);
	}
	this.saveCountry = function(country){
		return $http.post(url+"saveCountry",country);
	}
	this.deleteCountry = function(id){
		return $http.delete(url+"deleteCountry/"+id);
	}
	this.findAllIndividualBills = function(){
		return $http.get(url+"findAllIndividualBills");
	}
	this.saveIndividualBill = function(individualPerson){
		return $http.post(url+"saveIndividualBill",individualPerson);
	}
	
	this.saveIndividualBill = function(individualPerson){
		return $http.post(url+"saveIndividualBill",individualPerson);
	}
	
	this.updateIndividualClient = function(individualClient){
		return $http.put(url+"updateIndividualClient/"+individualClient.id,individualClient);
	}
	
	this.findClientById = function(clientId){
		return $http.get(url+"findClientById/"+clientId);
	}
	
	this.updateLegalClient = function(legalClient){
		return $http.put(url+"updateLegalClient/"+legalClient.id, legalClient);
	}
	this.saveLegalBill = function(legalPerson){
		return $http.post(url+"saveLegalBill",legalPerson);
	}
	this.findAllPopulatedPlaces = function(){
		return $http.get(url+"findAllPopulatedPlaces");
	}
	
	this.findPopulatedPlaceById = function(populatedPlaceId){
		return $http.get(url+"findPopulatedPlaceById/"+populatedPlaceId);
	}
	
	this.savePopulatedPlace = function(populatedPlace){
		return $http.post(url+"savePopulatedPlace",populatedPlace);
	}
	this.deletePopulatedPlace = function(id){
		return $http.delete(url+"deletePopulatedPlace/"+id);
	}
	this.updatePopulatedPlace = function(populatedPlace){
		return $http.put(url+"updatePopulatedPlace/"+populatedPlace.id,populatedPlace);
	}
	this.saveBill = function(bill){
		return $http.post(url+"saveBill",bill);
	}
	this.updateBank = function(bank){
		return $http.put(url+"updateBank/"+bank.id,bank);
	}
	this.closeBill = function(closingBill){
		return $http.post(url+"closeBill",closingBill);
	}
	
	this.saveDepositSlip = function(depositSlip){
		return $http.post(url+"saveDepositSlip",depositSlip);
	}
	
}]);