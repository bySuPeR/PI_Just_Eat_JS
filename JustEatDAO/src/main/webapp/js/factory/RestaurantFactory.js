angular.module ('JustEatDAO')
	.factory ('RestaurantFactory', ['$http', function ($http) {
		var url = 'https://localhost:8443/JustEat/rest/Restaurant/';
		var restaurantInterface = {
			loadAllRestaurantsFactory: function () {
				var extraUrl = url + 'loadAllRestaurants';
				return $http.get (extraUrl)
					.then (function (response) {
						return response.data;
					});
			}
		}
		return restaurantInterface;
	}])