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
	
	this.saveCodeBookActivity = function(codeBookActivity){
		return $http.post(url+"saveCodeBookActivity",codeBookActivity);
	}
	
	this.updateCodeBookActivity = function(codeBookActivity){
		return $http.put(url+"updateCodeBookActivity/"+codeBookActivity.id,codeBookActivity);
	}
	
	this.findAllCountries = function(){
		return $http.get(url+"findAllCountries");
	}
	this.findCountryById = function(countryId){
		return $http.get(url+"findCountryById/"+countryId);
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
	
	this.saveIndividualPerson = function(individualPerson){
		return $http.post(url+"saveIndividualPerson",individualPerson);
	}
	this.saveLegalPerson = function(legalPerson){
		return $http.post(url+"saveLegalPerson",legalPerson);
	}
	this.findAllPopulatedPlaces = function(){
		return $http.get(url+"findAllPopulatedPlaces");
	}
	this.savePopulatedPlace = function(populatedPlace){
		return $http.post(url+"savePopulatedPlace",populatedPlace);
	}
}]);