var services = angular.module('banker.services', ['ngResource']);

services.service('bankerService', ['$http', function($http){

	var url = "/banker/";	
	this.checkRights = function(){
		return $http.get(url+"checkRights");
	}
}]);