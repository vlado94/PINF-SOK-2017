var app = angular.module('banker.controllers', []);
var flagSave = 1;
app.controller('bankerController', ['$scope','bankerService', '$location','$state',
  	function ($scope, bankerService, $location,$state) {
		function checkRights() {
			bankerService.checkRights().then(
				function (response) {
					if(response.data != "") {
						$scope.banker = response.data;
						$scope.currentExchangeRate = $scope.banker.bank.exchangeRates[$scope.banker.bank.exchangeRates.length - 1];
						getBillsForBank();
					}
					else {
					    $location.path('login');
					    alert("Access denied!");
				    }
				}
			);
		}
		checkRights();
		
		function getBillsForBank() {
			bankerService.getBillsForBank().then(
				function (response) {
					var bills = response.data;
					for(var i =0;i < bills.length;i++) { //hehe
						if(bills[i].dailyBalances.length > 0)
							bills[i].client.jmbg = bills[i].dailyBalances[bills[i].dailyBalances.length-1].newState;
					}
					
					$scope.bills = bills;
					
				}
			);
		}
				
		$scope.showDepositSlipsForBill = function(billId) {
			bankerService.getDepositSlipsForBill(billId).then(
				function (response) {
					if(response.data.length == 0) {
						alert("No deposit slips for this bill")
						$scope.depositSlipsForBill = [];
					}
					else {
						$scope.depositSlipsForBill = response.data;				
					}
				}
			);
		}

		$scope.setBillInScope = function(billId) {
			 $scope.billID = billId;
		
		}
		
		//$scope.saveChoosenDates = function() {
			//alert("bill" + $scope.billID);
			//alert($scope.chossenDates.fromDate);
			/*bankerService.exportBalanceFromDateToDateForBill(billId).then(
				function (response) {
					if(response.data.length == 0) {
						alert("No deposit slips for this bill")
					
						//$scope.depositSlipsForBill = [];
					}
					else {
						//$scope.depositSlipsForBill = response.data;	
						alert(response.data.length);
					}
				}
			);*/
		//}
		
		
		
		$scope.exportBalanceFromDateToDateForBill = function() {
			
			if($scope.chossenDates.fromDate == null || $scope.chossenDates.toDate == null){
				alert("You must choose date!");
			}else{
				var billId = $scope.billID;
				var fromDate = $scope.chossenDates.fromDate;
				var toDate = $scope.chossenDates.toDate;
			bankerService.exportBalanceFromDateToDateForBill(billId , fromDate, toDate).then(
				function (response) {
					if(response.data.length == 0) {
						alert("No deposit slips for this bill")
					}
					else {	
						alert("Number of transactions :" + response.data.length);
					}
				},function (response){
					alert("Greska");
				}
			);
				
			}
		}
		
		$scope.makePDF = function () {
			if($scope.chossenDatesForPdf.fromDate == null || $scope.chossenDatesForPdf.toDate == null){
				alert("You must choose date!");
			}else{
				var billId = $scope.billID;
				var fromDate = $scope.chossenDatesForPdf.fromDate;
				var toDate = $scope.chossenDatesForPdf.toDate;
				bankerService.makePdf(billId , fromDate, toDate).then(function(response){
					window.location.href = "/banker/makePDFForClient";
	            },
				function(response){
					alert("NOT FOUND");
				})
			}
		}		
		
		/*
		 * 
		 * $scope.findCertificate = function(){
			findCertificateService.findCertificate($scope.findSerialNumber)
			.then(function(response){
		        var blob = new Blob([response.data], {type: 'application/x-x509-ca-cert'});
		        saveAs(blob,$scope.findSerialNumber+'.cer');
		        $scope.findSerialNumber = "";
			},
			function(response){
				alert("NOT FOUND");
			})
}
		 * 
		 * 
		 * 
		 * 
		 */
		$scope.openDepositSlipForClient = function(depositSlip) {
			$(".modal-backdrop").removeClass("fade");
			$(".modal-backdrop").removeClass("in");
			$(".modal-backdrop").removeClass("modal-backdrop");
			$scope.depositSlip = depositSlip;
			$scope.openDepositSlip(1);
		}

		
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
		
		$scope.searchForCodeBookActivity = function(){
			var codeBookActivity = $scope.codeBookActivity;
			bankerService.searchCodeBookActivity(codeBookActivity).then(
				function(response){
				    $scope.allcodeBookActivities = response.data;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.resetSearchCodeBookActivities = function(){
			$scope.findAllCodeBookActivities();
			$scope.codeBookActivity=null;
		}
	    
		
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
		
		$scope.searchForCountry = function(){
			//alert($scope.country.name+"  "+$scope.country.code)
			bankerService.searchCountry($scope.country).then(
				function(response){
				    $scope.allCountries = response.data;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.resetSearchCountry = function(){
			$scope.findAllCountries();
			$scope.country=null;
		}
		
		
		$scope.findAllIndividualBills = function () {   
			bankerService.findAllIndividualBills().then(
				function(response){
					$scope.allIndividualBills = response.data;
				}, function (response){
					alert("Greska!");
				}
			);	
		}
		
		$scope.findAllLegalBills= function () {   
			bankerService.findAllLegalBills().then(
				function(response){
					$scope.allLegalBills = response.data;
				}, function (response){
					alert("Greska!");
				}
			);
		}
		
		$scope.findClientById= function (clientID) { 
			bankerService.findClientById(clientID).then(
				function(response){
					$scope.client = response.data;
					
					if(response.data.type == "PRAVNO"){
						$scope.selectedNameOfActivity = $scope.client.codeBookActivities.name;
					}
				}, function (response){
					alert("Greska!");
				}
			);
		}
		
		$scope.saveIndividualBill= function () {
			var bill = {};
			bill.accountNumber = generateAccountNumber();
			bill.status = true;
			bill.date = new Date();
			bill.client = $scope.individualPerson;
			bill.client.type = "FIZICKO";
			bankerService.saveBill(bill).then(
				function(response){ 
					$state.go("banker.individualBills", {});
					$scope.allIndividualBills.push(response.data);
				}, function (response){
					alert("Greska kod update banke!");
				}
			);			
		}	
		
		$scope.saveLegalBill= function () {
			$scope.legalPerson.codeBookActivities = $scope.selectedActivityObject;
			var bill = {};
			bill.accountNumber = generateAccountNumber();
			bill.status = true;
			bill.date = new Date();
			bill.client = $scope.legalPerson;
			bill.client.type = "PRAVNO";
			bankerService.saveBill(bill).then(
				function(response){ 
					$state.go("banker.legalBills", {});
					$scope.allLegalBills.push(response.data);
				}, function (response){
					alert("Morate odabrati djelatnost!");
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
			bankerService.findActivityByName($scope.selectedNameOfActivity).then(
				function(response){
					var client  = $scope.client;
					//client.deliveryByMail = $scope.delivery;
					client.codeBookActivities =response.data;
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
			var client  = $scope.client;
					
		    bankerService.updateIndividualClient(client).then(
				function(){
					$state.go("banker.individualBills", {});
				}, function (response){
					alert("Greska!");
				}
			);
		};
		
		$scope.searchForIndividualBill = function(){
			var individualBill = $scope.individualBill;
			bankerService.searchBill(individualBill).then(
					function(response){
						alert(response.data)
					    $scope.allIndividualBills = response.data;
					}, 
					function (response){
						alert("Greska");
					}
				);
		}
		
		$scope.resetSearchIndividualBill = function(){
			$scope.findAllIndividualBills();
			$scope.individualBill=null;
		}
		$scope.searchForLegalBill = function(){
			var legalBill = $scope.legalBill;
			bankerService.searchBill(legalBill).then(
				function(response){
					$scope.allLegalBills = response.data;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.resetSearchLegalBill = function(){
			$scope.findAllLegalBills();
			$scope.legalBill=null;
		}
		
		$scope.setSelectedActivity = function(code) {
	        $scope.selectedActivity = code;
	        markRow(code);
	        
	        bankerService.findActivityById($scope.selectedActivity).then(
				function(response){
					$scope.selectedNameOfActivity = response.data.name; 
					$scope.selectedActivityObject = response.data;
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
	    
		$scope.searchForPopulatedPlace = function(){
			var populatedPlace = $scope.populatedPlace;
			bankerService.searchPopulatedPlace(populatedPlace).then(
				function(response){
				    $scope.allPopulatedPlaces = response.data;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.resetsearchPopulatedPlace = function(){
			$scope.findAllPopulatedPlaces();
			$scope.populatedPlace=null;
		}
		
		$scope.findAllExchangeRates = function() {
			bankerService.findAllExchangeRate().then(
				function(response){
				    $scope.exchangeRates = response.data;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.searchForExchangeRates = function(){
			var exchangeRate = $scope.exchangeRate;
			//alert(exchangeRate.date+" "+exchangeRate.numberOfExchangeRate+" "+exchangeRate.startDate)
			bankerService.searchExchangeRate(exchangeRate).then(
				function(response){
				    $scope.exchangeRates = response.data;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.resetSearchExchangeRates = function(){
			$scope.findAllExchangeRates();
			$scope.exchangeRate=null;
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
						$state.go("banker.home", {});	
					}, function (response){
						alert("Saving error "+response);
					}
				);
	        }else{
	        }
	    };
		
	    
	    //proveriti gde se sta poziva i sa desom refaktorisati,ova i naredna
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
			if(flagSave == 1) {
				bankerService.saveDepositSlip($scope.depositSlip).then(
					function(response){
						alert("Deposit slip successfuly processed.");
						location.reload();
						$state.go("banker.home", {});
						
					}, function (response){
						alert("Error!");
					}
				);
			} else
				alert("That request is forbidden now.")
			}
		
		$scope.exportDepositSlips = function() {
			bankerService.exportDepositSlips().then(
				function(response){
					alert("Export successfuly processed.");
					$scope.allNotProcessedDepositSlips = [];
					/*location.reload();
					$state.go("banker.home", {});
					*/
				}, function (response){
					alert("Error!");
				}
			);
		}
		
		$scope.openDepositSlip = function(val) {
			if(val != undefined && val != 1) {
				if(val == "TRANSFER") {
					$state.go("banker.depositSlip.transer", {});
				}
				else if(val == "PAYMENTOUT"){
					$state.go("banker.depositSlip.paymentOut", {});
				}
				else if(val == "PAYOUT"){
					$state.go("banker.depositSlip.payout", {});
				}
				else if(val == "PAYMENTIN"){
					$state.go("banker.depositSlip.paymentIn", {});
				}			    
			}
			else {
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
			var depositSlip = $scope.depositSlip;
			$scope.depositSlip = {};
			if(val == 1) {
				flagSave = 0;
				$scope.depositSlip = depositSlip;
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
		
		$scope.findNotProcessedDepositSlips = function() {
			bankerService.findAllUnsentInterbankTransfer().then (
				function(response){
					var interbankTransfers = response.data;//lista interbank transfera
					var listOfAllDepositSlips = [];
					for (var i in interbankTransfers) {
						for(var j in interbankTransfers[i].depositSlips){
							if(interbankTransfers[i].depositSlips[j].status == "UNPROCESSED")
							listOfAllDepositSlips.push(interbankTransfers[i].depositSlips[j]);
						}
					}
					$scope.allNotProcessedDepositSlips = listOfAllDepositSlips;
				}, function (response){
					alert("Error!")
				});						
		}
		
		$scope.findAllDepositSlipsForBank = function() {
			bankerService.findAllDepositSlipsForBank().then (
				function(response){
					$scope.allDepositSlipsForBank = response.data;
				}, function (response){
					alert("Error!")
			});						
		}
		
		function markRow(code) {   
	    	 var rows = document.getElementsByTagName('tr');
		        for(var i=0; i<rows.length; i +=1) {
		          rows[i].className = "";
		        }
		     element = document.getElementById(code);
		     element.setAttribute("class", "selectedRow");
		}
		
		$scope.searchForDepositSlip = function(){
			var depositSlip = $scope.depositSlip;
			bankerService.searchDepositSlip(depositSlip).then(
				function(response){
				    $scope.allNotProcessedDepositSlips = response.data;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.resetSearchDepositSlip = function(){
			$scope.findNotProcessedDepositSlips();
			$scope.depositSlip=null;
		}
		
		
		 $scope.uploadFile=function(){
			 file = document.getElementById("tempp").files[0]
			 if(file != null || file != undefined) {
	             bankerService.uploadFileToUrl(file).then(
	            	function(response){
	            		 alert("ok");
					}, 
					function (response){
						alert("Greska");
					}
     			);
			 }
            
		 }
	}
]);
