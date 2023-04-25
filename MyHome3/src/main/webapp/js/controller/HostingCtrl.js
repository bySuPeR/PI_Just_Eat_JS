angular.module('MyHome')
	.controller('HostingCtrl', ['HostingFactory', '$location', function(HostingFactory, $location) { //Siempre se pone el respectivo Factory del Controller, y se pasa el mismo orden de parámetros.
		var HostingViewModel = this;
		HostingViewModel.hostList = []; //Como un ArrayList en Java
		HostingViewModel.myHostsList = [];
		HostingViewModel.nameDescription = ""; //Para devolver un String.
		HostingViewModel.hostStatus = "All hosts"; //Para devolver el String recogido.
		HostingViewModel.host = {} //El nuevo host que se va a crear.
		HostingViewModel.selectedServices = [];
		HostingViewModel.selectedCategories = [];
		HostingViewModel.pickedCategory = "";
		HostingViewModel.selectedPrice = -1;
		HostingViewModel.functions = {
			loadHosts: function() {
				HostingFactory.loadHostsFactory()
					.then(function(response) {
						HostingViewModel.hostList = response;
					},
					function(response) {
						console.log("ERROR: Data could not be fetched", response);
					})
			},
			searchNameDescription: function() {
				HostingFactory.searchNameDescriptionFactory(HostingViewModel.nameDescription)
					.then(function(response) {
						HostingViewModel.hostList = response;
					},
						function(response) {
							console.log("ERROR: Data could not be fetched during the search by name, description or location.", response);
						})
			},
			reverseFavouritesOrder: function() {
				HostingFactory.reverseFavouritesOrderFactory()
					.then(function(response) {
						HostingViewModel.hostList = response;
					},
						function(response) {
							console.log("ERROR: Data could not be reversed during the favourite process searching.", response);
						})
			},
			statusShow: function() {
				HostingFactory.statusShowFactory(HostingViewModel.hostStatus)
					.then(function(response) {
						HostingViewModel.hostList = response;
					},
						function(response) {
							console.log("ERROR: Status data could not be fetched.", response);
						})
			},
			loadMyHosts: function() {
				HostingFactory.loadMyHostsFactory()
					.then(function(response) {
						HostingViewModel.myHostsList = response;
					},
						function(response) {
							console.log("ERROR: My hosts data could not be fetched.", response);
						})
			},
			selectServiceCategory: function(serviceCategory, decision) {
				var index;
				if (decision) {
					index = HostingViewModel.selectedCategories.indexOf(serviceCategory);
					if (index <= -1) {
						HostingViewModel.selectedCategories.push(serviceCategory);
					}
					if (index > -1) {
						HostingViewModel.selectedCategories.splice(index, 1);
						console.log (serviceCategory + " se ha eliminado.");
					}
				}
				else {
					index = HostingViewModel.selectedServices.indexOf(serviceCategory);
					if (index <= -1) {
						HostingViewModel.selectedServices.push(serviceCategory);
					}
					if (index > -1) {
						HostingViewModel.selectedServices.splice(index, 1);
					}
				}
			},
			addNewHost: function() {
				var serviceStr = "";
				for (let index = 0; index < HostingViewModel.selectedServices.length; index = index + 1) {
					serviceStr = serviceStr + HostingViewModel.selectedServices[index] + "/";
				}
				serviceStr = serviceStr.substring(0, serviceStr.length - 1);
				HostingViewModel.host.servicios = serviceStr;
				var categoryStr = "";
				for (let index = 0; index < HostingViewModel.selectedCategories.length; index = index + 1) {
					categoryStr = categoryStr + HostingViewModel.selectedCategories [index] + "_";
				}
				categoryStr = categoryStr.substring(0, categoryStr.length - 1);
				HostingFactory.addNewHostFactory (HostingViewModel.host, categoryStr)
				.then (function(response) {
						console.log ("SUCCESS: The host was successfully created.", response);
						$location.path ("/MyHosts");
				},
				function(response) {
					if (response.status == 502) {
						HostingViewModel.functions.mistake ("ERROR: The host must have, at least, one category.");
					}
					console.log("ERROR: The new host could not be created.", response);
				})
			},
			mistake: function (mistake) {
                var userMistake = document.querySelector ("#userMistake");
                if (userMistake === null) {
					var nextElement = document.querySelector ("#error");
					var previous = nextElement.parentNode;
					var userMistake = document.createElement ("p");
                    userMistake.textContent = mistake;
                    userMistake.id = "userMistake";
                    previous.insertBefore (userMistake, nextElement);
                    userMistake.classList.add ("mistake");
                }
                else {
                    userMistake.textContent = mistake;
                }
            },
            filterServices : function () {
				var serviceStr = "";
				for (let index = 0; index < HostingViewModel.selectedServices.length; index = index + 1) {
					serviceStr = serviceStr + HostingViewModel.selectedServices[index] + "_";
				}
				serviceStr = serviceStr.substring(0, serviceStr.length - 1);
				HostingFactory.filterServicesFactory (serviceStr)
					.then (function (response) {
						HostingViewModel.hostList = response;
					},
					function(response) {
						console.log("ERROR: Data could not be fetched during the search by service.", response);
					})
			},
			searchHostByCategory : function () {
				HostingFactory.searchHostByCategoryFactory (HostingViewModel.pickedCategory)
					.then(function(response) {
						HostingViewModel.hostList = response;
					},
					function(response) {
						console.log("ERROR: Data could not be fetched during the search by category.", response);
					})
			},
			searchHostByPrice : function () {
				HostingFactory.searchHostByPriceFactory (HostingViewModel.selectedPrice)
				.then (function (response) {
					HostingViewModel.hostList = response;
				},
				function(response) {
					console.log("ERROR: Data could not be fetched during the search by price.", response);
				})
			}
		}
		//Esto es para cargar cosas cuando se accede a la pantalla.
		HostingViewModel.functions.loadHosts();
		HostingViewModel.functions.loadMyHosts();
	}])