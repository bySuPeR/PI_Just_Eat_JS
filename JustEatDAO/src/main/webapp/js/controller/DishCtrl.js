angular.module ('JustEatDAO')
	.controller ('DishCtrl', ['DishFactory','DetailsFactory', '$routeParams','$location', function (DishFactory,DetailsFactory, $routeParams,$location) {
		var dishViewModel = this;
		dishViewModel.restaurant = {};
		dishViewModel.dishesList = [];
		dishViewModel.tipoBorrado = 0;
		dishViewModel.functions = {
			getRestaurant: function () {
				DetailsFactory.getRestaurantFact ($routeParams.ID)
					.then (function (response) {
						dishViewModel.restaurant = response;
					},
					function (response) {
						console.log ("'getRestaurant' could not be fetched: ", response.status);
					})
			},
			getDishesbyRestaurant: function() {
				DishFactory.getDishesbyRestaurantFactory(dishViewModel.restaurant.id)
					.then(function(response) {
						dishViewModel.dishesList = response;
					}, function(response) {
						console.log("'getDishesbyRestaurant' could not be fetched: ", response.status);
					})
			},
			deleteCurrentDish: function() {
					DishFactory.deleteCurrentDishFactory($routeParams.ID)
						.then(function(response) {
							$location.path ('/UpdateRestaurant/' + dishViewModel.restaurant.id);
						}, function(response) {
							console.log("'deleteCurrentDish' could not be fetched: ", response);
						})
			}
		}
		dishViewModel.functions.getRestaurant ();
		dishViewModel.functions.getDishesbyRestaurant ();
}])