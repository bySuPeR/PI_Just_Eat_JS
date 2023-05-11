angular.module('JustEatDAO')
	.controller('DetailsCtrl', ['DetailsFactory','$routeParams','$location', 'RestaurantFactory', function (DetailsFactory, $routeParams, $location, RestaurantFactory) {
		var detailsViewModel = this;
		detailsViewModel.restaurant = {};
		detailsViewModel.allCategories = [];
		detailsViewModel.restaurantCategories = [];
		detailsViewModel.tipoBorrado=0;
		detailsViewModel.belongsToMe = false;
		detailsViewModel.allDishes = [];
		detailsViewModel.error = "";
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
						detailsViewModel.restaurantCategories = response;
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
			},
			loadAllDishes: function  () {
				DetailsFactory.loadAllDishesFactory ($routeParams.ID)
					.then (function (response) {
						detailsViewModel.allDishes = response;
					},
					function (response) {
						console.log ("'loadAllDishes' could not be fetched: ", response.status);
					})
			},
			updateCurrentRestaurant: function () {
				
			},
			loadAllCategories: function () {
				RestaurantFactory.loadAllCategoriesFactory ()
					.then (function (response) {
						detailsViewModel.allCategories = response;
					},
					function (response) {
						console.log ("'loadAllCategories' could not be fetched: ", response.status);
					})
			},
			
			deleteCurrentRestaurant: function() {
					DetailsFactory.deleteCurrentRestaurantFactory($routeParams.ID)
						.then(function(response) {
							console.log("Eliminando el restaurante...", response);
							$location.path('/');
						}, function(response) {
							console.log("Se ha producido un error al borrar el restaurante", response);
						})
			}
			
			
		}
		detailsViewModel.functions.getRestaurant();
		detailsViewModel.functions.loadRestaurantCategories ();
		detailsViewModel.functions.loadBelongsToMe ();
		detailsViewModel.functions.loadAllDishes ();
		detailsViewModel.functions.loadAllCategories ();
	}])