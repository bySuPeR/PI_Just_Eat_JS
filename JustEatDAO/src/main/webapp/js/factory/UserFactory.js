angular.module ('JustEatDAO')
	.factory ('UserFactory', ['$http', function ($http) {
		var url = 'https://localhost:8443/JustEat/rest/UpdateUser';
		var userInterface = {
			getUserFactory: function () {
				url = url;
				return $http.get (url)
					.then (function (response) {
						return response.data;
					});
			}
		}
		return userInterface;
	}])