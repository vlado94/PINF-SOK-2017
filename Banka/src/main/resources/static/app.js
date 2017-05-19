'use strict';

angular.module('routerApp', ['ui.router', 
	'banker.services','banker.controllers',
	'admin.services','admin.controllers',
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
         
         .state('admin', {
        	url : '/admin',
          	templateUrl : 'admin/adminPartial.html',
            controller : 'adminController'
         })
         
         .state('admin.home', {
        	url : '/home',
          	templateUrl : 'admin/adminHome.html'
        })
        
        //COUNTRIES
        .state('admin.countries', {
        	url : '/countries',
          	templateUrl : 'admin/country/countries.html'
        })
		.state('admin.countries.addCountry', {
        	url : '/addNew',
			templateUrl : 'admin/country/addCountry.html'
		})
		.state('admin.countries.updateCountry', {
        	url : '/update',
			templateUrl : 'admin/country/updateCountry.html'
		})
		.state('admin.countries.searchCountry', {
        	url : '/search',
			templateUrl : 'admin/country/searchCountry.html'
		})
		
		//POPULATED PLACES
        .state('admin.populatedPlaces', {
        	url : '/populatedPlaces',
          	templateUrl : 'admin/populatedPlaces/populatedPlaces.html'
        })
		.state('admin.populatedPlaces.addPopulatedPlace', {
        	url : '/addNew',
			templateUrl : 'admin/populatedPlaces/addPopulatedPlace.html'
		})
		.state('admin.populatedPlaces.updatePopulatedPlace', {
        	url : '/update',
			templateUrl : 'admin/populatedPlaces/updatePopulatedPlace.html'
		})
		.state('admin.populatedPlaces.searchPopulatedPlaces', {
        	url : '/search',
			templateUrl : 'admin/populatedPlaces/searchPopulatedPlaces.html'
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
        	url : '/legalBills',
          	templateUrl : 'banker/bills/legalBills.html'
        })
         .state('banker.individualBills', {
        	url : '/individualBills',
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
        .state('banker.individualBills.searchIndividualBill', {
          	templateUrl : 'banker/bills/searchIndividualBill.html'
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
        .state('banker.codeBookActivities.searchCodeBookActivity', {
          	templateUrl : 'banker/codeBookActivities/searchCodeBookActivity.html'
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
        .state('banker.countries.searchCountry', {
          	templateUrl : 'banker/country/searchCountry.html'
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
        .state('banker.populatedPlaces.searchPopulatedPlace', {
          	templateUrl : 'banker/populatedPlace/searchPopulatedPlace.html'
        })
        .state('banker.exchangeRates', {
        	url : '/exchangeRates',
          	templateUrl : 'banker/exchangeRate/exchangeRates.html'
        })
        .state('banker.exchangeRates.addExchangeRate', {
        	url : '/addExchangeRate',
          	templateUrl : 'banker/exchangeRate/addExchangeRate.html'
        })
        .state('banker.exchangeRates.exchangeRateDetails', {
        	url : '/exchangeRateDetails',
          	templateUrl : 'banker/exchangeRate/detailsExchangeRate.html'
        })
        .state('banker.closeBill', {
          	templateUrl : 'banker/bills/closeBill.html'
        })
        .state('banker.depositSlip', {
        	url : '/depositSlip/newSlip',
          	templateUrl : 'banker/depositSlip/addDepositSlip.html'
        })
        .state('banker.depositSlip.paymentOut', {
        	url : '/depositSlip/newSlip/PaymentOut',
          	templateUrl : 'banker/depositSlip/depositSlipForPaymentOut.html'
        })
        
        .state('banker.depositSlip.paymentIn', {
        	url : '/depositSlip/newSlip/PaymentIn',
          	templateUrl : 'banker/depositSlip/depositSlipForPaymentIn.html'
        })
        .state('banker.depositSlip.payout', {
        	url : '/depositSlip/newSlip/Payout',
          	templateUrl : 'banker/depositSlip/depositSlipForPayout.html'
        })
        .state('banker.depositSlip.transer', {
        	url : '/depositSlip/newSlip/Transfer',
          	templateUrl : 'banker/depositSlip/depositSlipForTransfer.html'
        })
        .state('banker.depositSlipsForAccount', {
          	templateUrl : 'banker/depositSlip/depositSlipsForAccount.html'
          		
        }).state('banker.depositSlipsAll', {
        	url : '/depositSlip/allInBank',
          	templateUrl : 'banker/depositSlip/depositSlipsAll.html'
        })
        
});