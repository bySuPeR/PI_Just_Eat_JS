angular.module ('JustEatDAO')
	.factory ('UserFactory', ['$http', function ($http) {
		var url = 'https://localhost:8443/JustEatDAO/rest/UpdateUser/';
		var userInterface = {
			getCurrentUserFactory: function () {
				url = url;
				return $http.get (url)
					.then (function (response) {
						return response.data;
					});
			},
			updateCurrentUserFactory: function(currentUser) {
				console.log("Editando el perfil...");
				var urlCurrentUser = url + currentUser.id;
				return $http.put(urlCurrentUser,currentUser) //tiene dos parametros siempre (la url y el objeto a actualizar)
					.then(function(response) {
						return response.status;
					});
			}
		}
		return userInterface;
	}])