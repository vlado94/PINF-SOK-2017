var services = angular.module('banker.services', ['ngResource']);

services.service('bankerService', ['$http', function($http){

	var url = "/banker/";	
	
	this.checkRights = function(){
		return $http.get(url+"checkRights");
	}
	
	this.update = function(banker){
		return $http.put(url+"/update/"+banker.id,banker);
	}
	
	
	this.getAllCodeBookActivities = function(){
		return $http.get(url+"getAllCodeBookActivities");
	}
	
	this.addActivity = function(codeBookActivities){
		return $http.post(url+"addCodeBookActivity",codeBookActivities);
	}
	
	this.getAllCountries = function(){
		return $http.get(url+"getAllCountries");
	}
	this.addNewCountry = function(country){
		return $http.post(url+"addCountry",country);
	}
	
	
}]);