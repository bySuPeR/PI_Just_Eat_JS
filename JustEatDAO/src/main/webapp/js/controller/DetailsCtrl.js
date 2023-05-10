angular.module('JustEatDAO')
	.controller('DetailsCtrl', ['DetailsFactory','$routeParams',function(DetailsFactory, $routeParams) {
		var detailsViewModel = this;
		detailsViewModel.restaurant = {};
		detailsViewModel.allCategories = [];
		detailsViewModel.belongsToMe = false;
		detailsViewModel.functions = {
			getRestaurant: function() {
				DetailsFactory.getRestaurantFact($routeParams.ID)
					.then(function(response) {
						detailsViewModel.restaurant = response;
					}, function(response) {
						console.log("'getRestaurant' could not be fetched:", response.status);
					})
			},
			loadRestaurantCategories: function () {
				DetailsFactory.loadRestaurantCategoriesFactory ($routeParams.ID)
					.then (function (response) {
						detailsViewModel.allCategories = response;
						if (detailsViewModel.allCategories != null) {
							console.log ("Todo OK");
						}
					},
					function (response) {
						console.log ("'loadRestaurantCategories' could not be fetched: ", response.status);
					})
			},
			loadBelongsToMe: function () {
				DetailsFactory.loadBelongsToMeFactory ($routeParams.ID)
					.then (function (response) {
						detailsViewModel.belongsToMe = response;
					},
					function (response) {
						console.log ("'loadBelongsToMe' could not be fetched: ", response.status);
					})
			}
		}
		detailsViewModel.functions.getRestaurant();
		detailsViewModel.functions.loadRestaurantCategories ();
		detailsViewModel.functions.loadBelongsToMe ();
	}])