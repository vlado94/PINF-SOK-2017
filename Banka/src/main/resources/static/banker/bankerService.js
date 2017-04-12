var services = angular.module('banker.services', ['ngResource']);

services.service('bankerService', ['$http', function($http){

	var url = "/banker/";	
	this.checkRights = function(){
		return $http.get(url+"checkRights");
	}
	
	this.update = function(banker){
		return $http.post("/banker/update",banker);
	}
	
	
	this.getAllCodeBookActivities = function(){
		return $http.get("/banker/getAllCodeBookActivities");
	}
	
	this.addActivity = function(codeBookActivities){
		return $http.post("/banker/addCodeBookActivity",codeBookActivities);
	}
	
	this.getAllCountries = function(){
		return $http.get("/banker/getAllCountries");
	}
	this.addNewCountry = function(country){
		return $http.post("/banker/addCountry",country);
	}
	
	
}]);