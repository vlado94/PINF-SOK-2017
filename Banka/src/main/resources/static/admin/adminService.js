var services = angular.module('admin.services', ['ngResource']);

services.service('adminService', ['$http', function($http){
var url = "/admin/";	
	
	this.checkRights = function(){
		return $http.get(url+"checkRights");
	}
	
	this.updateProfile = function(admin){
		return $http.put(url+"updateProfile/"+admin.id,admin);
	}
	
	
//COUNTRY
	this.findAllCountries = function(){
		return $http.get("/country");
	}
	
	this.saveCountry = function(country){
		return $http.post("/country",country);
	}
	
	this.findCountryById = function(countryId){
		return $http.get("/country/"+countryId);
	}
	this.findCountryByName = function(name){
		return $http.get("/country/findByName/"+name);
	}
	
	this.updateCountry = function(country){
		return $http.put("/country/"+country.id,country);
	}
	
	this.deleteCountry = function(id){
		return $http.delete("/country/"+id);
	}
	this.searchCountry = function(country){
		return $http.post("/country/search",country);
	}
	
//POPULATED PLACE
	this.findAllPopulatedPlaces = function(){
		return $http.get("/populatedPlaces");
	}
	
	this.savePopulatedPlace = function(populatedPlace){
		return $http.post("/populatedPlaces",populatedPlace);
	}
	
	this.findPopulatedPlaceById = function(populatedPlaceId){
		return $http.get("/populatedPlaces/"+populatedPlaceId);
	}
	
	this.updatePopulatedPlace = function(populatedPlace){
		return $http.put("/populatedPlaces/"+populatedPlace.id,populatedPlace);
	}
	
	this.deletePopulatedPlace = function(id){
		return $http.delete("/populatedPlaces/"+id);
	}
	this.searchPopulatedPlace = function(populatedPlace){
		return $http.post("/populatedPlaces/search",populatedPlace);
	}
//CODE BOOK ACTIVITY
	this.findAllCodeBookActivities = function(){
		return $http.get("/codeBookActivities");
	}
	this.findActivityById = function(id){
		return $http.get("/codeBookActivities/"+id);
	}
	this.findActivityByName = function(activityName){
		return $http.get("/codeBookActivities/findByName/"+activityName);
	}
	
	this.saveCodeBookActivity = function(codeBookActivity){
		return $http.post("/codeBookActivities",codeBookActivity);
	}
	
	this.updateCodeBookActivity = function(codeBookActivity){
		return $http.put("/codeBookActivities/"+codeBookActivity.id,codeBookActivity);
	}
	this.deleteCodeBookActivity = function(id){
		return $http.delete("/codeBookActivities/"+id);
	}
	this.searchCodeBookActivity = function(codeBookActivity){
		return $http.get("/codeBookActivities/search",codeBookActivity);
	}	
//EXCHANGE RATE
	this.findAllExchangeRate = function(){
		return $http.get("/exchangeRate");
	}
	
	this.saveExchangeRate = function(exchangeRate){
		return $http.post("/exchangeRate",exchangeRate);
	}
	
	this.exchangeRateDetails = function(id){
		return $http.get("/exchangeRate/"+id);
	}
	
	
}]);