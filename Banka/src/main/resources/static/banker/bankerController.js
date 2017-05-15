var app = angular.module('banker.controllers', []);
 
app.controller('bankerController', ['$scope','bankerService', '$location','$state',
  	function ($scope, bankerService, $location,$state) {
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
				function(response){
					$scope.allcodeBookActivities.push({ 'id':response.data.id ,'code':response.data.code, 'name': response.data.name});
				}, function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.prepareToUpdateCodeBookActivity= function (codeBookActivityId) {
			bankerService.findActivityById(codeBookActivityId).then(
					function(response){
						$scope.codeBookActivityForUpdate = response.data;
					}, 
					function (response){
						alert("Greska");
					}
				);
			
		}
		
		$scope.updateCodeBookActivityy = function() {
			bankerService.updateCodeBookActivity($scope.codeBookActivityForUpdate).then(
				function(response){
					 var index = -1;
			          var listOfActivities = eval( $scope.allcodeBookActivities );
			          for( var i = 0; i < listOfActivities.length; i++ ) {
			                if( listOfActivities[i].id === response.data.id ) {
			                    index = i;
			                    break;
			                 }
			          }
			          if( index === -1 ) {
			               alert( "Something gone wrong" );
			          }
			          $scope.allcodeBookActivities[index] = response.data;
				}, 
				function (response){
					alert("Greska");
				}
			);
		};
		
		$scope.deleteCodeBookActivity = function(id) {
			bankerService.deleteCodeBookActivity(id).then(
				function(){
					var index = -1;
			          var listOfActivities = eval( $scope.allcodeBookActivities );
			          for( var i = 0; i < listOfActivities.length; i++ ) {
			                if( listOfActivities[i].id === id ) {
			                    index = i;
			                    break;
			                 }
			          }
			          if( index === -1 ) {
			               alert( "Something gone wrong" );
			          }
			          $scope.allcodeBookActivities.splice( index, 1 );
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
				function(response){
					$scope.allCountries.push({ 'id':response.data.id ,'code':response.data.code, 'name': response.data.name, 'currency':response.data.currency});
				}, function (response){
					alert("Greska");
				}
			);
		}	
		
		$scope.prepareToUpdateCountry= function (countryId) { 
				bankerService.findCountryById(countryId).then(
						function(response){
							$scope.countryForUpdate = response.data;
						}, 
						function (response){
							alert("Greska");
						}
					);
		}
		
		$scope.updateCountryy = function() {
			bankerService.updateCountry($scope.countryForUpdate).then(
				function(response){
					var index = -1;
			          var listOfCountries = eval( $scope.allCountries );
			          for( var i = 0; i < listOfCountries.length; i++ ) {
			                if( listOfCountries[i].id === response.data.id ) {
			                    index = i;
			                    break;
			                 }
			          }
			          if( index === -1 ) {
			               alert( "Something gone wrong" );
			          }
			          $scope.allCountries[index] = response.data;
				}, 
				function (response){
					alert("Greska");					
				}
			);
		
		};
		
		$scope.deleteCountry = function(id) {
			bankerService.deleteCountry(id).then(
				function(){
					   var index = -1;
				          var listOfCountries = eval( $scope.allCountries );
				          for( var i = 0; i < listOfCountries.length; i++ ) {
				                if( listOfCountries[i].id === id ) {
				                    index = i;
				                    break;
				                 }
				          }
				          if( index === -1 ) {
				               alert( "Something gone wrong" );
				          }
				          $scope.allCountries.splice( index, 1 );
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
		
		$scope.getDetailsAboutLegal= function (clientID) { 
			bankerService.findClientById(clientID).then(
					function(response){
						$scope.client = response.data;
						initDetailsAboutLegal($scope.client);
					}, function (response){
						alert("Greska!");
					}
				);
			
		}
		
		
		
		$scope.getDetailsAboutIndividual= function (clientID) { 
			bankerService.findClientById(clientID).then(
					function(response){
						$scope.client = response.data;
						initDetailsAboutIndividual($scope.client);
					}, function (response){
						alert("Greska!");
					}
				);
			
		}
		
		function initDetailsAboutIndividual(client) {
			
			if(client.deliveryByMail == true){
				document.getElementById("deliveryTrue").checked= true;
			}else{
				document.getElementById("deliveryFalse").checked= true;
			}
		}
		
		
		function initDetailsAboutLegal(client) {
			if(client.deliveryByMail == true){
				document.getElementById("deliveryTrue").checked= true;
			}else{
				document.getElementById("deliveryFalse").checked= true;
			}
			$scope.selectedNameOfActivity = client.codeBookActivities.name;
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
					
					var generatedAccountNumber = generateAccountNumber();
					bill.accountNumber = generatedAccountNumber;
					bill.status = true;
					bill.date = new Date();
					bill.client = client;
					
					bankerService.saveBill(bill).then(
						function(response){ 
							var banker = $scope.banker;
							banker.bank.bills.push(response.data);
							bankerService.updateBank(banker.bank).then(
								function(){
									$state.go("banker.individualBills", {});
								}, function (response){
									alert("Greska kod update banke!");
								}
							);
						}, function (response){
							alert("Ne moze se dodati racun!");
						}
					);
				}, function (response){
					alert("Greska");
				}
			);
		}	
		
		
		function generateAccountNumber(){
			var bankCode= $scope.banker.bank.code;
			var numberOfBill =(Math.floor(1000000000000 + Math.random() * 9000000000000)).toString();
			var concatNumber = bankCode+numberOfBill;
			var controlNumber = (98-((concatNumber * 100)%97)).toString();
			
			if(controlNumber.length == 1){
				controlNumber = "0"+ controlNumber;
			}
			return concatNumber+controlNumber;
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
							$state.go("banker.legalBills", {});
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
					$state.go("banker.individualBills", {});
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
						function(response){var client = response.data;
							var bill = {};
							
							var generatedAccountNumber = generateAccountNumber();
							bill.accountNumber = generatedAccountNumber;
							bill.status = true;
							bill.date = new Date();
							bill.client = client;
							
							bankerService.saveBill(bill).then(
								function(response){ 
									var banker = $scope.banker;
									banker.bank.bills.push(response.data);
									bankerService.updateBank(banker.bank).then(
										function(){
											$state.go("banker.legalBills", {});
										}, function (response){
											alert("Greska kod update banke!");
										}
									);
								}, function (response){
									alert("Ne moze se dodati racun!");
								}
							);
						}, function (response){
							alert("Ne moze se sacuvati pravno lice");
						}
					);
				}, function (response){
					alert("Morate odabrati djelatnost!");
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
						function(response){
							$scope.allPopulatedPlaces.push({ 'id':response.data.id ,'name':response.data.name, 'pttCode': response.data.pttCode, 'country':response.data.country});
						}, function (response){
							alert("Greska pri cuvanju naseljenog mjesta");
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
					 var index = -1;
			          var listOfPlaces = eval( $scope.allPopulatedPlaces );
			          for( var i = 0; i < listOfPlaces.length; i++ ) {
			                if( listOfPlaces[i].id === id ) {
			                    index = i;
			                    break;
			                 }
			          }
			          if( index === -1 ) {
			               alert( "Something gone wrong" );
			          }
			          $scope.allPopulatedPlaces.splice( index, 1 );
				}, 
				function (response){
					alert("Postoji pravno lice koje posjeduje datu djelatnost. Nije moguce trenutno izbrisati.");					}
			);
		};
		
		$scope.prepareToUpdatePopulatedPlace= function (populatedPlaceId) { 
		
			bankerService.findPopulatedPlaceById(populatedPlaceId).then(
					function(response){
						$scope.populatedPlaceForUpdate = response.data;
						$scope.selectedNameWhenChanged = response.data.country.name;
					}, 
					function (response){
						alert("Greska");
					}
				);
		}
		
		$scope.updatePopulatedPlacee = function() {
			bankerService.findCountryByName($scope.selectedNameWhenChanged).then(
				function(response){
					var country = response.data;
					var place  = $scope.populatedPlaceForUpdate;
					place.country = country;
					bankerService.updatePopulatedPlace(place).then(
						function(response){
							 var index = -1;
					          var listOfPlaces = eval( $scope.allPopulatedPlaces );
					          for( var i = 0; i < listOfPlaces.length; i++ ) {
					                if( listOfPlaces[i].id === response.data.id ) {
					                    index = i;
					                    break;
					                 }
					          }
					          if( index === -1 ) {
					               alert( "Something gone wrong" );
					          }
					          $scope.allPopulatedPlaces[index] = response.data;
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
		
		$scope.exchangeRateDetails = function(exchangeRateId) {
			bankerService.exchangeRateDetails(exchangeRateId).then(
					function(response){
						$scope.exchangeRateDetail = response.data;
					}, function (response){
						alert("Error!");
					}
				);
		}

		$scope.closeBill = function (individualBill) {
			$scope.billForClosing = individualBill;
			alert("Bill for closing: "+$scope.billForClosing.client.applicant +" "+$scope.billForClosing.accountNumber);
		}
		

		$scope.finishClosingBill = function() {
	        if (confirm("Sure for bill successor?")) {
	        	var date = new Date();
	        	var bill = $scope.billForClosing;
	        	var closingBill = 
                {
                    "date": date,
                    "billSuccessor": $scope.billSuccessor,
                    "bill": bill
                };
	        	alert(closingBill.billSuccessor+" "+closingBill.date+" "+closingBill.bill.accountNumber);
	            bankerService.closeBill(closingBill).then(
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
		/*$scope.findAllIndividualBillsExceptClosingOne = function () {   
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
		}*/
		$scope.findAllBillsExceptClosingOne= function () {   
			var banker = $scope.banker;
			var listOfBills = banker.bank.bills;
			var list = [];
			
			 for(var i=0; i<listOfBills.length; i +=1) {
				 if(listOfBills[i].status == true){
					 if($scope.billForClosing.accountNumber != listOfBills[i].accountNumber){
						 list.push(listOfBills[i]);
				 }
				 }
		     }
			$scope.allBills = list;
		}
		
		$scope.findBillsForAllBanks = function () {   
			var id = $scope.billForClosing.id;
			alert(id)
			bankerService.findBillsForAllBanks(id).then(
				function(response){
					$scope.billsForAllBanks = response.data;
				}, function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.setSelectedSuccessor = function(accountNumber,code) {
			$scope.billSuccessor=accountNumber;
	        document.getElementById("billSuccessor").value = accountNumber;
	        markRow(code);
	        
	    };	
		$scope.saveDepositSlip = function() {
			bankerService.saveDepositSlip($scope.depositSlip).then(
				function(response){
					alert("Deposit slip successfuly processed.");
					location.reload();
					$state.go("banker.home", {});
					
				}, function (response){
					alert("Error!");
				}
			);
		}

		/*$scope.saveDepositSlipAndCloseBill = function(){
			depositSlip = $scope.depositSlip;
			bankerService.saveDepositSlip($scope.depositSlip).then(
				function(response){
					alert("Ok saving deposit slip");
					var date = new Date();
		        	var bill = $scope.billForClosing;
					var closingBill = 
	                {
	                    "date": date,
	                    "billSuccessor": depositSlip.billOfReceiver,
	                    "bill": bill
	                };
		            bankerService.closeBill(closingBill).then(
							function(response){
								alert("Bill is closed successfully! ");
								location.reload();
								
							}, function (response){
								alert("Saving error "+response);
							}
						);
				}, function (response){
					alert("Error!");
				}
			);
		}*/
		
		$scope.openDepositSlip = function() {
			if($scope.depositSlip.type == "TRANSFER") {
				$state.go("banker.depositSlip.transer", {});
			}
			else if($scope.depositSlip.type == "PAYMENTOUT"){
				$state.go("banker.depositSlip.paymentOut", {});
			}
			else if($scope.depositSlip.type == "PAYOUT"){
				$state.go("banker.depositSlip.payout", {});
			}
			else if($scope.depositSlip.type == "PAYMENTIN"){
				$state.go("banker.depositSlip.paymentIn", {});
			}
		}
		
		$scope.findAllDepositSlipsForAccount = function (accountNumber) {  //koristi se kod all depositSlips
			bankerService.findAllDepositSlips().then (
				function(response){
					var slips = response.data;
					var list = [];
					var object;
					var expense = 0;
					var benefit = 0;
					
					for(var i=0; i<slips.length; i +=1) { //sve uplatnice
						if(slips[i].billOfReceiver == accountNumber){
							object = {date: slips[i].depositSlipDate,
								account: slips[i].billOfDeptor,
								description: slips[i].purposeOfPayment,
								trafficAtExpense: 0,
								trafficToBenefit : slips[i].amount
								}
							benefit += slips[i].amount;
							list.push(object);
						} else if(slips[i].billOfDeptor == accountNumber) {
							object = {date: slips[i].depositSlipDate,
									account: slips[i].billOfReceiver,
									description: slips[i].purposeOfPayment,
									trafficAtExpense: slips[i].amount,
									trafficToBenefit : 0
									}
							expense += slips[i].amount;
							list.push(object);
						}				
					}
					$scope.list = list;
					$scope.state = benefit - expense;
				}, function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.findAllDepositSlips = function() {
			bankerService.findAllDepositSlips().then (
				function(response){
					$scope.allDepositSlips = response.data;
				}, function (response){
					alert("Error!")
				});						
		}
	}
]);
