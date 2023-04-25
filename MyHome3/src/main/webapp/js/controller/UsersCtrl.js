angular.module ('MyHome')
	.controller ('UsersCtrl', ['UsersFactory', '$window', '$location', function (UsersFactory, $window, $location) { //Siempre se pone el respectivo Factory del Controller, y se pasa el mismo orden de parámetros.
		var UsersViewModel = this;
		UsersViewModel.currentUser = {}; //Esto es un objeto entero.
		UsersViewModel.functions = {
			loadUserData : function () {
				UsersFactory.loadUserDataFactory ()
				.then (function (response) {
                        UsersViewModel.currentUser = response;
                },
                function (response) {
                	console.log ("ERROR: User data could not be fetched.", response);
                })
			},
			deleteCurrentUser : function () {
				UsersFactory.deleteCurrentUserFactory ()
				.then (function (response) {
                        console.log ("SUCCESS: Current user was successfully deteled.", response);
                        $window.location.href='/MyHome3/LogoutServlet.do';
                },
                function (response) {
                	console.log ("ERROR: Current user could not be deleted.", response);
                })
			},
			updateCurrentUser : function () {
				UsersFactory.updateCurrentUserFactory (UsersViewModel.currentUser)
				.then (function (response) {
					console.log ("SUCCESS: Current user was successfully updated.", response);
					$location.path ("/");
				},
				function (response) {
					if (response.status == 502) {
						UsersViewModel.functions.mistake ("The username already exists.");
					}
					if (response.status == 400) {
						UsersViewModel.functions.mistake ("The email already exists.");
					}
					if (response.status == 409) {
						UsersViewModel.functions.mistake ("The password is not valid.");
					}
					console.log ("The current user data could not be updated.");
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
            }
		}
		UsersViewModel.functions.loadUserData ();
	}])