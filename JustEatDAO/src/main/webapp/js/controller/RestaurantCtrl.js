angular.module ('JustEatDAO')
	.controller ('RestaurantCtrl', ['RestaurantFactory', function (RestaurantFactory) {
		var restaurantViewModel = this;
		restaurantViewModel.allRestaurants = [];
		restaurantViewModel.functions = {
			loadAllRestaurants: function () {
				RestaurantFactory.loadAllRestaurantsFactory ()
					.then (function (response) {
						restaurantViewModel.allRestaurants = response;
					},
					function (response) {
						console.log ("'allRestaurants' could not be fetched: ", response.status);
					})
			}
		}
		restaurantViewModel.functions.loadAllRestaurants ();
	}])