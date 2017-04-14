var services = angular.module('banker.services', ['ngResource']);

services.service('bankerService', ['$http', function($http){

	var url = "/banker/";	
	
	this.checkRights = function(){
		return $http.get(url+"checkRights");
	}
	
	this.update = function(banker){
		return $http.put(url+"/update/"+banker.id,banker);
	}
	
	
	this.findAllCodeBookActivities = function(){
		return $http.get(url+"findAllCodeBookActivities");
	}
	
	this.saveCodeBookActivity = function(codeBookActivity){
		return $http.post(url+"saveCodeBookActivity",codeBookActivity);
	}
	
	this.findAllCountries = function(){
		return $http.get(url+"findAllCountries");
	}
	this.saveCountry = function(country){
		return $http.post(url+"saveCountry",country);
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
	this.savePopulatedPlace = function(populatedPlac){
		return $http.post(url+"savePopulatedPlace",populatedPlac);
	}
}]);