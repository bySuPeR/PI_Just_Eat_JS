angular.module('JustEatDAO')
	.controller('DetailsCtrl', ['DetailsFactory','$routeParams', 'RestaurantFactory', function (DetailsFactory, $routeParams, RestaurantFactory) {
		var detailsViewModel = this;
		detailsViewModel.restaurant = {};
		detailsViewModel.allCategories = [];
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
						detailsViewModel.allCategories = response;
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
			}
		}
		detailsViewModel.functions.getRestaurant();
		detailsViewModel.functions.loadRestaurantCategories ();
		detailsViewModel.functions.loadBelongsToMe ();
		detailsViewModel.functions.loadAllDishes ();
		detailsViewModel.functions.loadAllCategories ();
	}])