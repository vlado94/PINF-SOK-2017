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
        .state('banker.legalBills', {
          	templateUrl : 'banker/bills/legalBills.html'
        })
         .state('banker.individualBills', {
          	templateUrl : 'banker/bills/individualBills.html'
        })
          .state('banker.detailsAboutIndividual', {
          	templateUrl : 'banker/bills/detailsAboutIndividual.html'
        })
          .state('banker.detailsAboutLegal', {
          	templateUrl : 'banker/bills/detailsAboutLegal.html'
        })
        .state('banker.addLegalBill', {
          	templateUrl : 'banker/bills/addLegalBill.html'
        })
        .state('banker.addIndividualBill', {
          	templateUrl : 'banker/bills/addIndividualBill.html'
        })
         .state('banker.codeBookActivities', {
        	url : '/codeBookActivities',
          	templateUrl : 'banker/codeBookActivities/codeBookActivities.html'
        })
         .state('banker.codeBookActivities.addCodeBookActivity', {
          	templateUrl : 'banker/codeBookActivities/addCodeBookActivity.html'
        })
         .state('banker.codeBookActivities.updateCodeBookActivity', {
          	templateUrl : 'banker/codeBookActivities/updateCodeBookActivity.html'
        })
         .state('banker.countries', {
        	url : '/countries',
          	templateUrl : 'banker/country/countries.html'
        })
          .state('banker.countries.addCountry', {
          	templateUrl : 'banker/country/addCountry.html'
        })
          .state('banker.countries.updateCountry', {
          	templateUrl : 'banker/country/updateCountry.html'
        })
          .state('banker.populatedPlaces', {
        	url : '/populatedPlace',
          	templateUrl : 'banker/populatedPlace/populatedPlaces.html'
        })
          .state('banker.populatedPlaces.addPopulatedPlace', {
          	templateUrl : 'banker/populatedPlace/addPopulatedPlace.html'
        })
           .state('banker.populatedPlaces.updatePopulatedPlace', {
          	templateUrl : 'banker/populatedPlace/updatePopulatedPlace.html'
        })
        .state('banker.exchangeRates', {
        	url : '/exchangeRates',
          	templateUrl : 'banker/exchangeRate/exchangeRates.html'
        })
        .state('banker.exchangeRates.addExchangeRate', {
          	templateUrl : 'banker/exchangeRate/addExchangeRate.html'
        })
        .state('banker.closeBill', {
          	templateUrl : 'banker/bills/closeBill.html'
        })
        .state('banker.depositSlip', {
        	url : '/banker/depositSlip',
          	templateUrl : 'banker/depositSlip/addDepositSlip.html'
        })
        .state('banker.depositSlip.allDepositSlips', {
          	templateUrl : 'banker/depositSlip/depositSlips.html'
        })
});