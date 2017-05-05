var app = angular.module('banker.controllers', []);
 
app.controller('bankerController', ['$scope','bankerService', '$location',
  	function ($scope, bankerService, $location) {
		function checkRights() {
			bankerService.checkRights().then(
				function (response) {
					if(response.data != "") {
						$scope.banker = response.data;
						$scope.currentExchangeRate = $scope.banker.bank.exchangeRates[$scope.banker.bank.exchangeRates.length - 1];
					}
					else {
					    $location.path('login');
					    alert("Access denied!");
				    }
				}
			);
		}
		
		checkRights();

		$scope.updateProfile = function () {
			bankerService.updateProfile($scope.banker).then(
				function(response){
					$scope.banker = response.data;
				}, function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.findAllCodeBookActivities = function () { 
			bankerService.findAllCodeBookActivities().then(
				function(response){
					$scope.allcodeBookActivities = response.data;
				}, function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.saveCodeBookActivity= function () { 
			bankerService.saveCodeBookActivity($scope.codeBookActivity).then(
				function(){
					alert("Odgovor");
				}, function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.prepareToUpdateCodeBookActivity= function (codeBookActivity) { 
			$scope.codeBookActivityForUpdate = codeBookActivity;
		}
		
		$scope.updateCodeBookActivityy = function() {
			bankerService.updateCodeBookActivity($scope.codeBookActivityForUpdate).then(
				function(){
					location.reload();
				}, 
				function (response){
					alert("Greska");
				}
			);
		};
		
		$scope.deleteCodeBookActivity = function(id) {
			bankerService.deleteCodeBookActivity(id).then(
				function(){
					 location.reload();
				}, 
				function (response){
					alert("Postoji pravno lice koje posjeduje datu djelatnost. Nije moguce trenutno izbrisati.");					}
			);
		};
		
		$scope.findAllCountries = function () {   
			bankerService.findAllCountries().then(
				function(response){
					$scope.allCountries = response.data;
				}, function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.saveCountry= function () {
			bankerService.saveCountry($scope.country).then(
				function(){
					location.reload();
				}, function (response){
					alert("Greska");
				}
			);
		}	
		
		$scope.prepareToUpdateCountry= function (country) { 
			$scope.countryForUpdate = country;
		}
		
		$scope.updateCountryy = function() {
			bankerService.updateCountry($scope.countryForUpdate).then(
				function(){
					 location.reload();
				}, 
				function (response){
					alert("Greska");					}
			);
		};
		
		$scope.deleteCountry = function(id) {
			bankerService.deleteCountry(id).then(
				function(){
					 location.reload();
				}, 
				function (response){
					alert("Postoji naseljeno mesto koje je vezano za tu drzavu. Nije moguce trenutno izbrisati.");					}
			);
		};
		
		
		$scope.findAllIndividualBills = function () {   
			var banker = $scope.banker;
			var listOfBills = banker.bank.bills;
			var list = [];
			
			 for(var i=0; i<listOfBills.length; i +=1) {
				 if(listOfBills[i].client.type == "FIZICKO"){
					 list.push(listOfBills[i]);
				 }
		     }
			$scope.allIndividualBills = list;
			
			
		}
		
		
		$scope.getDetails= function (client) { 
			alert("OVvooo")
			$scope.client = client;
			
		}
		
		$scope.initDetails= function (client) { 
			if(client.deliveryByMail == true){
				document.getElementById("deliveryTrue").checked= true;
			}else{
				document.getElementById("deliveryFalse").checked= true;
				
			}
		}
		
		
		$scope.chooseDelivery= function (chosen) {
			if(chosen == true){
				document.getElementById("deliveryFalse").checked= false;
				document.getElementById("deliveryTrue").checked= true;
				$scope.delivery = "true";
			}else{
				document.getElementById("deliveryTrue").checked= false;
				document.getElementById("deliveryFalse").checked= true;
				$scope.delivery = "false";
			}
		}
		
		$scope.saveIndividualBill= function () {
			bankerService.saveIndividualBill($scope.individualPerson).then(
				function(response){
					var client = response.data;
					
					var bill = {};
					
					bill.accountNumber = "656555";
					bill.status = true;
					bill.date = "2016-03-03";
					bill.client = client;
					
					bankerService.saveBill(bill).then(
						function(response){ 
							var banker = $scope.banker;
							banker.bank.bills.push(response.data);
							bankerService.updateBank(banker.bank).then(
								function(){
									alert("odgovor posle update bank");
									location.reload();
								}, function (response){
									alert("Greska kod update banke!");
								}
							);
							
						}, function (response){
							alert("Morate odabrati drzavu!");
						}
					);
				}, function (response){
					alert("Greska");
				}
			);
		}	
		
		
		function generateAccountNumber(){
			
		}
		
		
		$scope.updateLegalClient = function() {
			var deliveryByMail =$scope.delivery ; 
	
			bankerService.findActivityByName($scope.selectedNameOfActivity).then(
				function(response){
					var activity = response.data;
					var client  = $scope.client;
						
					client.deliveryByMail = $scope.delivery;
					client.codeBookActivities =activity;
					bankerService.updateLegalClient(client).then(
						function(){
							alert("odgovor");
							location.reload();
						}, function (response){
							alert("Greska!");
						}
					);
				}, function (response){
					alert("Greska!");
				}
			);
		};
		
		
		
		$scope.updateIndividualClient = function() {
			var deliveryByMail =$scope.delivery ; 
			var client  = $scope.client;
		
			client.deliveryByMail = $scope.delivery;		
		    bankerService.updateIndividualClient(client).then(
				function(){
					location.reload();
				}, function (response){
					alert("Greska!");
				}
			);
		};
		
		$scope.findAllLegalBills= function () {   
			var banker = $scope.banker;
			var listOfBills = banker.bank.bills;
			var list = [];
			
			 for(var i=0; i<listOfBills.length; i +=1) {
				 if(listOfBills[i].client.type == "PRAVNO"){
					 list.push(listOfBills[i]);
				 }
		     }
			
			$scope.allLegalBills = list;
			
		}
		
		$scope.saveLegalBill= function () {
			bankerService.findActivityById($scope.selectedActivity).then(
				function(response){
					var activity = response.data;
					var person  = $scope.legalPerson;
					person.codeBookActivities = activity;
						
					bankerService.saveLegalBill(person).then(
						function(){
							alert("Odgovor");
							location.reload();
						}, function (response){
							alert("Greska");
						}
					);
				}, function (response){
					alert("Morate odabrati drzavu!");
				}
			);
		
		}	
		
		
		$scope.setSelectedActivity = function(code) {
	        $scope.selectedActivity = code;
	        markRow(code);
	        
	        bankerService.findActivityById($scope.selectedActivity).then(
				function(response){
					$scope.selectedNameOfActivity = response.data.name; 
				}, function (response){
					alert("Morate odabrati drzavu!");
				}
			);
	    };
	    
	    
		$scope.saveActivityForLegalBill= function () {   
			$scope.previousSelectedActivity = $scope.selectedActivity;
			
			
			 bankerService.findActivityById($scope.selectedActivity).then(
					function(response){
						$scope.selectedNameOfActivity = response.data.name; 
					}, function (response){
						alert("Morate odabrati drzavu!");
					}
			 );
			
		
		}
		
		$scope.annulActivityForLegalBill= function () {   
			$scope.selectedActivity = $scope.previousSelectedActivity;
			
			bankerService.findActivityById($scope.previousSelectedActivity).then(
					function(response){
						//$scope.selectedName = response.data.name; //dodati
					}, function (response){
						//$scope.selectedName =$scope.previousSelected;  //dodati
					}
				);
		}
		
		$scope.findAllPopulatedPlaces= function () {   
			bankerService.findAllPopulatedPlaces().then(
				function(response){
					$scope.allPopulatedPlaces = response.data;
				}, function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.setSelected = function(code) {
	        $scope.selected = code;
	        markRow(code);
	        
	        bankerService.findCountryById($scope.selected).then(
				function(response){
					$scope.selectedName = response.data.name;
				}, function (response){
					alert("Morate odabrati drzavu!");
				}
			);
	    };	
	    
	    
	    function markRow(code) {   
	    	 var rows = document.getElementsByTagName('tr');
		        for(var i=0; i<rows.length; i +=1) {
		          
		          rows[i].className = "";
		        }
		        
		     element = document.getElementById(code);
		     element.setAttribute("class", "selectedRow");
		}
	    

		$scope.saveCountryForPopulatedPlace= function () {   
			$scope.previousSelected = $scope.selected;
			
			
			 bankerService.findCountryById($scope.selected).then(
					function(response){
						$scope.selectedName = response.data.name;
					}, function (response){
						alert("Morate odabrati drzavu!");
					}
			 );
			
		
		}
		
		$scope.annulCountryForPopulatedPlace= function () {   
			$scope.selected = $scope.previousSelected;
			
			bankerService.findCountryById($scope.previousSelected).then(
				function(response){
					$scope.selectedName = response.data.name;
				}, function (response){
					$scope.selectedName =$scope.previousSelected; 
				}
			);
		}
		
		$scope.savePopulatedPlace= function () {
			bankerService.findCountryById($scope.selected).then(
				function(response){
					var country = response.data;
					var place  = $scope.populatedPlace;
					place.country = country;
					bankerService.savePopulatedPlace(place).then(
						function(){
							location.reload();
						}, function (response){
							alert("Greska");
						}
					);
				}, function (response){
					alert("Morate odabrati drzavu!");
				}
			);
		}
		
		
		$scope.deletePopulatedPlace = function(id) {
			bankerService.deletePopulatedPlace(id).then(
				function(){
					 location.reload();
				}, 
				function (response){
					alert("Postoji pravno lice koje posjeduje datu djelatnost. Nije moguce trenutno izbrisati.");					}
			);
		};
		
		$scope.prepareToUpdatePopulatedPlace= function (populatedPlace) { 
			$scope.populatedPlaceForUpdate = populatedPlace;
			$scope.selectedNameWhenChanged = populatedPlace.country.name;
		}
		
		$scope.updatePopulatedPlacee = function() {
			bankerService.findCountryByName($scope.selectedNameWhenChanged).then(
				function(response){
					var country = response.data;
					var place  = $scope.populatedPlaceForUpdate;
					place.country = country;
					bankerService.updatePopulatedPlace(place).then(
						function(){
							location.reload();
						}, function (response){
								alert("Morate odabrati drzavu!");
						}
					);
				}, function (response){
					alert("Morate odabrati drzavu!");
				}
			);
		};
		
		
		$scope.setSelectedWhenChanged = function(code) {
	        $scope.selectedForUpdate = code;
	        markRow(code);
	       
	    };
	    
	    
		$scope.saveChangedCountryForPopulatedPlace= function () {   
			$scope.previousSelectedForUpdate = $scope.selectedForUpdate;
			
			 bankerService.findCountryById($scope.selectedForUpdate).then(
				function(response){
					$scope.selectedNameWhenChanged = response.data.name;
				}, function (response){
					alert("Morate odabrati drzavu!");
				}
			 );
		}
		
		$scope.annulChangedCountryForPopulatedPlace= function () {   
			$scope.selectedForUpdate = $scope.previousSelectedForUpdate;
			
			bankerService.findCountryById($scope.previousSelectedForUpdate).then(
				function(response){
					$scope.selectedNameWhenChanged = response.data.name;
				}, function (response){
					alert("Morate odabrati drzavu!");
				}
			);
		}
	    
		$scope.getCurrentExchangeRate = function () {
			$scope.newCurrentExchange = $scope.currentExchangeRate;
		}
		$scope.addExchangeRatee = function() {
			
			
			alert(1)
		}

		$scope.closeBill = function (individualBill) {
			$scope.billForClosing = individualBill;
			alert("Bill for closing: "+$scope.billForClosing.client.applicant +" "+$scope.billForClosing.accountNumber);
			
		}
		
		$scope.setSelectedIndividual = function(index,accountNumber) {
	        $scope.selected = index;
	        if (confirm("Sure for bill successor?")) {
	        	var date = new Date();
	        	var bill = $scope.billForClosing;
	        	var closingBill = 
                {
                    "date": date,
                    "billSuccessor": accountNumber,
                    "bill":bill
                };
	            bankerService.saveClosingBill(closingBill).then(
						function(response){
							alert("Bill is closed successfully! ");
							location.reload();
							
						}, function (response){
							alert("Saving error "+response);
						}
					);
	        }else{
	        	
	        }
	    };
		$scope.findAllIndividualBillsExceptClosingOne = function () {   
			var banker = $scope.banker;
			var listOfBills = banker.bank.bills;
			var list = [];
			
			 for(var i=0; i<listOfBills.length; i +=1) {
				 if(listOfBills[i].client.type == "FIZICKO" ){
					 if($scope.billForClosing.accountNumber != listOfBills[i].accountNumber){
						 list.push(listOfBills[i]);
					 }
				 }
		     }
			$scope.allIndividualBills = list;
		}
		
		$scope.findAllLegalBillsExceptClosingOne= function () {   
			var banker = $scope.banker;
			var listOfBills = banker.bank.bills;
			var list = [];
			
			 for(var i=0; i<listOfBills.length; i +=1) {
				 if(listOfBills[i].client.type == "PRAVNO"){
					 if($scope.billForClosing.accountNumber != listOfBills[i].accountNumber){
						 list.push(listOfBills[i]);
					 }
				 }
		     }
			$scope.allLegalBills = list;
		}
		
		$scope.saveDepositSlip = function() {
			depositSlip = $scope.depositSlip;
			alert(1);
			bankerService.saveDepositSlip($scope.depositSlip).then(
				function(response){
					alert("Ok");
				}, function (response){
					alert("Error!");
				}
			);
		}
	}
]);
