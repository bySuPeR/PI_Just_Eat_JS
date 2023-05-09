angular.module('JustEatDAO')
	.controller('DetailsCtrl', ['DetailsFactory','$routeParams',function(DetailsFactory, $routeParams) {
		var detailsViewModel = this;
		detailsViewModel.restaurant={};
		detailsViewModel.functions = {
			getRestaurant: function() {
				DetailsFactory.getRestaurantFact($routeParams.ID)
					.then(function(response) {
						detailsViewModel.restaurant = response;
						console.log("Controlador: " + detailsViewModel.restaurant.name);
					}, function(response) {
						console.log("'getRestaurant' could not be fetched:", response);
					})
			}
		}
		detailsViewModel.functions.getRestaurant();
	}])