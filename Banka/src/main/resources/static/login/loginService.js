var services = angular.module('login.services', ['ngResource']);

var baseUrl = 'http://localhost\\:8080';

services.service('loginService', ['$http', function($http){
	
	this.logIn = function(user){
		return $http.get("/user/logIn",user);
	}
	
	this.logOut = function(){
		return $http.get("/user/logOut");
	}
	
	this.firstLogin = function(id,user){
		return $http.put("/user/"+id,user);
	}
}]);