var services = angular.module('clientPersonal.services', ['ngResource']);

services.service('clientPersonalService', ['$http', function($http){

	var url = "/clientPersonal/";	
	this.checkRights = function(){
		return $http.get(url + "checkRights");
	}
}]);