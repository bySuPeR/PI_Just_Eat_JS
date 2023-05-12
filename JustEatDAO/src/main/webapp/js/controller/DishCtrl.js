angular.module ('JustEatDAO')
	.controller ('DishCtrl', ['DishFactory','DetailsFactory', '$routeParams', function (DishFactory,DetailsFactory, $routeParams) {
		var dishViewModel = this;
		dishViewModel.restaurant= {};
		dishViewModel.dishesList = [];
		dishViewModel.functions = {
			getRestaurant: function () {
				DetailsFactory.getRestaurantFact ($routeParams.ID)
					.then(function(response) {
						dishViewModel.restaurant = response;
						if (dishViewModel.restaurant.bikeFriendly == 1) {
							dishViewModel.isBikeFriendly = true;
						}
						if (dishViewModel.restaurant.available) {
							dishViewModel.isAvailable = true;
						}
						dishViewModel.functions.getDishesbyRestaurant();
					}, function(response) {
						console.log("'getRestaurant' could not be fetched:", response.status);
					})
			},
			
			getDishesbyRestaurant: function() {
				DishFactory.getDishesbyRestaurantFactory(dishViewModel.restaurant)
					.then(function(response) {
						console.log("Getting restaurant's dishes...", response);
						dishViewModel.dishesList = response;
					}, function(response) {
						console.log("'getDishesbyRestaurant' could not be fetched:", response);
					})
			}
			
			
		}
		dishViewModel.functions.getRestaurant();
}])