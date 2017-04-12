'use strict';

angular.module('routerApp', ['ui.router', 
	'banker.services','banker.controllers',
	'clientPersonal.services','clientPersonal.controllers',
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
                    return $http.get("/start/logOut");
                 }}
         })
         
         .state('loggedIn', {
        	url : '/loggedIn',
        	templateUrl : 'loggedIn.html'
         })
         
         .state('loggedIn.clientPersonal', {
        	url : '/clientPersonal',
          	templateUrl : 'clientPersonal/clientPersonalPartial.html',
            controller : 'clientPersonalController'
         })
        .state('loggedIn.clientPersonal.home', {
        	url : '/home',
          	templateUrl : 'clientPersonal/clientPersonalHome.html'
        })
        
        .state('loggedIn.banker', {
        	url : '/banker',
          	templateUrl : 'banker/bankerPartial.html',
            controller : 'bankerController'
         })
        .state('loggedIn.banker.home', {
        	url : '/home',
          	templateUrl : 'banker/bankerHome.html'
        })
        .state('loggedIn.banker.profile', {
        	url : '/profile',
          	templateUrl : 'banker/bankerProfile.html'
        })
        .state('loggedIn.banker.createNewLegalBill', {
        	url : '/createNew',
          	templateUrl : 'banker/createNewLegalBill.html'
        })
         .state('loggedIn.banker.codeBookActivities', {
        	url : '/codeBookActivities',
          	templateUrl : 'banker/codeBookActivities/codeBookActivities.html'
        })
         .state('loggedIn.banker.codeBookActivities.addCodeBookActivity', {
          	templateUrl : 'banker/codeBookActivities/addCodeBookActivity.html'
        })
         .state('loggedIn.banker.countries', {
        	url : '/countries',
          	templateUrl : 'banker/country/countries.html'
        })
          .state('loggedIn.banker.countries.addCountry', {
          	templateUrl : 'banker/country/addCountry.html'
        })
});