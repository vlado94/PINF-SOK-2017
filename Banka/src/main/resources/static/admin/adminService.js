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
	
	this.savePopulatedPlaces = function(country){
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
	this.searchPopulatedPlaces = function(populatedPlace){
		return $http.post("/populatedPlaces/search",populatedPlace);
	}
	
	
	
}]);