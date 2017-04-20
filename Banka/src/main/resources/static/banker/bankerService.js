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
	this.findAllLegalBills = function(){
		return $http.get(url+"findAllLegalBills");
	}
	this.saveLegalBill = function(legalPerson){
		return $http.post(url+"saveLegalBill",legalPerson);
	}
	this.findAllPopulatedPlaces = function(){
		return $http.get(url+"findAllPopulatedPlaces");
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
}]);