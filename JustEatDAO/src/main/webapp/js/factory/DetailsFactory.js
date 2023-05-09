angular.module('JustEatDAO')
	.factory("DetailsFactory", ['$http', function($http) {
		var url = 'https://localhost:8443/JustEatDAO/rest/restaurantDetails/';
		var detailsInterface = {
			getRestaurantFact: function(restID) {
				var extraURL = url + restID;
				return $http.get(extraURL)
					.then(function(response) {
						console.log("Factory: " + response.data.name);
						return response.data;
					});
			}
		}
		return detailsInterface;
	}])
	




