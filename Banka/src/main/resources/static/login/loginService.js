var services = angular.module('login.services', ['ngResource']);

var baseUrl = 'http://localhost\\:8080';

services.service('loginService', ['$http', function($http){
	
	this.logIn = function(user){
		return $http.post("/start/logIn",user);
	}
	
	this.logOut = function(){
		return $http.get("/start/logOut");
	}
	
	this.firstLogin = function(id,user){
		return $http.put("/start/"+id,user);
	}
}]);