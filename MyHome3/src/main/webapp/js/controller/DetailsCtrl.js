angular.module('MyHome')
	.controller('DetailsCtrl', ['DetailsFactory', 'UsersFactory', '$routeParams', '$location', '$window', function (DetailsFactory, UsersFactory, $routeParams, $location, $window) { //Siempre se pone el respectivo Factory del Controller, y se pasa el mismo orden de parámetros.
		var DetailsViewModel = this;
		DetailsViewModel.host = {};
		DetailsViewModel.serviceList = [];
		DetailsViewModel.categoryList = [];
		DetailsViewModel.heartLike;
		DetailsViewModel.belongsToMe;
		DetailsViewModel.currentUser = {};
		DetailsViewModel.functions = {
			loadHostDetails : function (HostID) {
				DetailsFactory.loadHostDetailsFactory (HostID)
				.then (function (response) {
                        DetailsViewModel.host = response;
                        DetailsViewModel.serviceList = DetailsViewModel.host.servicios.split ("/");
                },
                function (response) {
                	console.log ("ERROR: Host data could not be fetched", response);
                })
			},
			loadHostCategories : function (HostID) {
				DetailsFactory.loadHostCategoriesFactory (HostID)
				.then (function (response) {
					DetailsViewModel.categoryList = response;
				},
				function (response) {
                	console.log ("ERROR: Host category data could not be fetched", response);
                })
			},
			loadLike : function (HostID) {
				DetailsFactory.loadLikeFactory (HostID)
				.then (function (response) {
                        DetailsViewModel.heartLike = response;
                },
                function (response) {
                	console.log ("ERROR: Like data could not be fetched", response);
                })
			},
			itsMine : function (HostID) {
				DetailsFactory.itsMineFactory (HostID)
				.then (function (response) {
					DetailsViewModel.belongsToMe = response;
				},
				function (response) {
					console.log ("ERROR: Belonging data could not be fetched", response)
				})
			},
			deleteHost : function (HostID) {
				DetailsFactory.deleteHostFactory (HostID)
				.then (function (response) {
					console.log ("SUCCESS: The host was successfully deleted.", response);
					$location.path ("/MyHosts");
				},
				function (response) {
					console.log ("ERROR: The host could not be deleted.", response);
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
            selectServiceCategory: function(serviceCategory, decision) {
				var index;
				if (decision) {
					index = DetailsViewModel.categoryList.indexOf(serviceCategory)
					if (index <= -1) {
						DetailsViewModel.categoryList.push (serviceCategory)
					}
					if (index > -1) {
						DetailsViewModel.categoryList.splice(index, 1);
					}
				}
				else {
					index = DetailsViewModel.serviceList.indexOf(serviceCategory);
					if (index <= -1) {
						DetailsViewModel.serviceList.push (serviceCategory);
					}
					if (index > -1) {
						DetailsViewModel.serviceList.splice (index, 1);
					}
				}
			},
			generateServices : function () {
				var serviceStr = "";
				for (let index = 0; index < DetailsViewModel.serviceList.length; index = index + 1) {
					serviceStr = serviceStr + DetailsViewModel.serviceList[index] + "/";
				}
				serviceStr = serviceStr.substring (0, serviceStr.length - 1);
				DetailsViewModel.host.servicios = serviceStr;
			},
            editHost : function () {
				DetailsViewModel.functions.generateServices ();
				var categoryStr = "";
				for (let index = 0; index < DetailsViewModel.categoryList.length; index = index + 1) {
					categoryStr = categoryStr + DetailsViewModel.categoryList [index] + "_";
				}
				categoryStr = categoryStr.substring(0, categoryStr.length - 1);
				DetailsFactory.editHostFactory (DetailsViewModel.host, categoryStr)
				.then (function (response) {
					console.log ("SUCCESS: The host was correctly updated.", response);
					$location.path ("/Details/" + DetailsViewModel.host.id);
				},
				function (response) {
					if (response.status == 502) {
						DetailsViewModel.functions.mistake ("ERROR: The host must have, at least, one category.");
					}
					console.log ("ERROR: The host could not be updated.", response);
				})
			},
			bookHost : function () {
				DetailsFactory.bookHostFactory (DetailsViewModel.host)
				.then (function (response) {
                	console.log ("SUCCESS: The availability data was successfully updated.", response);
                	$window.location.reload ();
                },
                function (response) {
                	console.log ("ERROR: The availability data could not be updated.", response);
                })
			},
			likeHost : function () {
				DetailsFactory.likeHostFactory (DetailsViewModel.host)
				.then (function (response) {
					console.log ("SUCCESS: The likes were successfully updated.", response);
					if (DetailsViewModel.heartLike == true) {
						DetailsViewModel.heartLike = false;
					}
					else {
						DetailsViewModel.heartLike = true;
					}
					$window.location.reload ();
				},
				function (response) {
					console.log ("ERROR: The like could not be processed.", response);
				})
			},
			loadUserData : function () {
				UsersFactory.loadUserDataFactory ()
				.then (function (response) {
                        DetailsViewModel.currentUser = response;
                },
                function (response) {
                	console.log ("ERROR: User data could not be fetched.", response);
                })
			}
		}
		//Todo esto permite precargar datos.
		DetailsViewModel.functions.loadHostDetails ($routeParams.ID); //Esto es para llamar el alojamiento con el ID correspondiente.
		DetailsViewModel.functions.loadLike ($routeParams.ID);
		DetailsViewModel.functions.itsMine ($routeParams.ID);
		DetailsViewModel.functions.loadHostCategories ($routeParams.ID);
		DetailsViewModel.functions.loadUserData ();
	}])