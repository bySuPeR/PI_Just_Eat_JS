angular.module ('JustEatDAO')
	.controller ('DishCtrl', ['DishFactory','DetailsFactory','UserFactory', '$routeParams','$location', function (DishFactory,DetailsFactory,UserFactory, $routeParams,$location) {
		var dishViewModel = this;
		dishViewModel.restID = -1;
		dishViewModel.dishesList = [];
		dishViewModel.tipoBorrado = 0;
		dishViewModel.newDish = {};
		dishViewModel.restaurant={};
		dishViewModel.currentUser={};
		dishViewModel.currentDish = {};
		dishViewModel.functions = {
			getDishesbyRestaurant: function() {
				DishFactory.getDishesbyRestaurantFactory ($routeParams.ID)
					.then (function (response) {
						dishViewModel.dishesList = response;
					}, function(response) {
						console.log("'getDishesbyRestaurant' could not be fetched: ", response.status);
					})
			},
			deleteCurrentDish: function() {
					DishFactory.deleteCurrentDishFactory($routeParams.ID)
						.then(function(response) {
							$location.path ('/myRestaurants');
						}, function(response) {
							console.log("'deleteCurrentDish' could not be fetched: ", response);
						})
			},
			updateDish: function (dishID) {
				dishViewModel.newDish = dishViewModel.functions.findDish (dishID);
				DishFactory.updateDishFactory (dishViewModel.newDish)
					.then (function (response) {
						$location.path ('/myRestaurants');
					},
					function (response) {
						console.log ("'updateDish' could not be updated: ", response.status);
					})
			},
			findDish: function (dishID) {
				var found = false;
				var pos = -1;
				for (let i = 0; (i < dishViewModel.dishesList.length) && (!found); i++) {
					if (dishViewModel.dishesList [i].id == dishID) {
						found = true;
						pos = i;
					}
				}
				return dishViewModel.dishesList [pos];
			},
			createNewDish: function () {
				dishViewModel.newDish.idr = $routeParams.ID;
				DishFactory.createNewDishFactory (dishViewModel.newDish)
					.then (function (response) {
						$location.path ("/myRestaurants")
					},
					function (response) {
						console.log ("'createNewDish' could not be created: ", response.status);
					})
			},
			getCurrentUser: function () {
				UserFactory.getCurrentUserFactory ()
					.then (function (response) {
						dishViewModel.currentUser = response;
					},
					function (response) {
						console.log ("'getCurrentUser' could not be fetched ", response.status);
					})
			},
			getRestaurant: function() {
				DetailsFactory.getRestaurantFact(dishViewModel.restID)
					.then(function(response) {
						dishViewModel.restaurant = response;
						if (dishViewModel.restaurant.bikeFriendly == 1) {
							dishViewModel.isBikeFriendly = "yes-bike-friendly-value";
						}
						if (dishViewModel.restaurant.available) {
							dishViewModel.isAvailable = true;
						}
					}, function(response) {
						console.log("'getRestaurant' could not be fetched:", response.status);
					})
			},
			getDish: function (dishID) {
				DishFactory.getDishFactory (dishID)
					.then (function (response) {
						dishViewModel.currentDish = response;
						dishViewModel.restID = dishViewModel.currentDish.idr;
						dishViewModel.functions.getRestaurant ();
					},
					function (response) {
						console.log ("'getDish' could not be fetched: ", response.status);
					})
			}
		}
		if ($location.absUrl ().includes ("/deleteDish/")) {
			dishViewModel.functions.getDish ($routeParams.ID);
			dishViewModel.functions.getCurrentUser ();
		}
		if ($location.absUrl ().includes ("/manageDishes/") || $location.absUrl ().includes ("/AddDish/")) {
			dishViewModel.functions.getDishesbyRestaurant ();
			dishViewModel.restID = $routeParams.ID;
			dishViewModel.functions.getRestaurant();
			dishViewModel.functions.getCurrentUser();
		}
}])