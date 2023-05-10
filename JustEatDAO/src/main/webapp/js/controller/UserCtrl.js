angular.module ('JustEatDAO')
	.controller ('UserCtrl', ['UserFactory', '$location', $window, function (UserFactory, $location, $window) {
		var userViewModel = this;
		userViewModel.currentUser = {};
		userViewModel.error = "";
		userViewModel.functions = {
			getCurrentUser: function () {
				UserFactory.getCurrentUserFactory ()
					.then (function (response) {
						userViewModel.currentUser = response;
					}, function (response) {
						console.log ("Error fetching this user in UserCtrl: ", response)
					})
			},
			updateCurrentUser: function () {
				UserFactory.updateCurrentUserFactory (userViewModel.currentUser)
					.then (function (response) {
						$location.path("/UpdateUser");
				}, function (response) {
					if(response.status==412){
						userViewModel.error="El correo ya existe";
					}
					if(response.status==406){
						userViewModel.error="La contraseña no es válida";
						}
					if(response.status==411){
						userViewModel.error="La contraseña no llega a 8 caracteres";
					}
				})
			},
			
			deleteCurrentUser: function(){
				if(userViewModel.confirm === 'Si'){
					UserFactory.deleteCurrentUserFactory()
				.then ( function (response){
					$window.location.href='/JustEatDAO/LogOutServlet.do';
				}, function (response) {
					console.log ("Error fetching this user in UserCtrl: ", response)
				})
			}
			else if (userViewModel.confirm === 'No'){	
				$location.path('/UpdateUser');
				}	
		}
	}
		userViewModel.functions.getCurrentUser ();
	}])
