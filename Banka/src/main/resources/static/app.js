'use strict';

angular.module('routerApp', ['ui.router', 
	'banker.services','banker.controllers',
	'login.services','login.controllers'
	])

.config(function($stateProvider, $urlRouterProvider) {
        
        $urlRouterProvider.otherwise('/login');
        
        $stateProvider
        
        .state('login', {
        	url : '/login',
          	templateUrl : 'login/login.html',
          	controller : 'loginController'
         })
         
         .state('login.logOut', {
        	url : '/logout',
         	resolve: {
        		promiseObj:  function($http,$location){
        			$location.path('login');
                    return $http.get("/user/logOut");
                 }}
         })
         
         .state('loggedIn', {
        	url : '/loggedIn',
        	templateUrl : 'loggedIn.html'
         })
         
         .state('banker', {
        	url : '/banker',
          	templateUrl : 'banker/bankerPartial.html',
            controller : 'bankerController'
         })
        .state('banker.home', {
        	url : '/home',
          	templateUrl : 'banker/bankerHome.html'
        })
        .state('banker.profile', {
        	url : '/profile',
          	templateUrl : 'banker/bankerProfile.html'
        })
        .state('banker.createNewLegalBill', {
        	url : '/createNew',
          	templateUrl : 'banker/createNewLegalBill.html'
        })
         .state('banker.codeBookActivities', {
        	url : '/codeBookActivities',
          	templateUrl : 'banker/codeBookActivities/codeBookActivities.html'
        })
         .state('banker.codeBookActivities.addCodeBookActivity', {
          	templateUrl : 'banker/codeBookActivities/addCodeBookActivity.html'
        })
         .state('banker.countries', {
        	url : '/countries',
          	templateUrl : 'banker/country/countries.html'
        })
          .state('banker.countries.addCountry', {
          	templateUrl : 'banker/country/addCountry.html'
        })
});