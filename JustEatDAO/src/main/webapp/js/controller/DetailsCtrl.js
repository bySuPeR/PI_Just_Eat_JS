angular.module('JustEatDAO')
	.controller('DetailsCtrl', ['DetailsFactory', 'UserFactory', '$routeParams','$location', 'RestaurantFactory', function (DetailsFactory, UserFactory, $routeParams, $location, RestaurantFactory) {
		var detailsViewModel = this;
		detailsViewModel.restaurant = {};
		detailsViewModel.allCategories = [];
		detailsViewModel.restaurantReviews = [];
		detailsViewModel.reviewUser = [];
		detailsViewModel.rateReviewUser = {};
		detailsViewModel.restaurantCategories = [];
		detailsViewModel.tipoBorrado;
		detailsViewModel.belongsToMe = false;
		detailsViewModel.allDishes = [];
		detailsViewModel.error = "";
		detailsViewModel.isBikeFriendly = "no-bike-friendly-value";
		detailsViewModel.isAvailable = false;
		detailsViewModel.currentUser = {};
		detailsViewModel.functions = {
			getRestaurant: function() {
				DetailsFactory.getRestaurantFact($routeParams.ID)
					.then(function(response) {
						detailsViewModel.restaurant = response;
						if (detailsViewModel.restaurant.bikeFriendly == 1) {
							detailsViewModel.isBikeFriendly = "yes-bike-friendly-value";
						}
						if (detailsViewModel.restaurant.available) {
							detailsViewModel.isAvailable = true;
						}
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
			markCategory: function (catName) {
				var found = false;
				var pos = -1;
				for (let i = 0; (i < detailsViewModel.restaurantCategories.length) && (!found); i++) {
					if (detailsViewModel.restaurantCategories [i].name === catName) {
						found = true;
						pos = i;
					}
				}
				if (found) {
					DetailsFactory.deleteCategoryFactory (detailsViewModel.restaurantCategories, pos)
						.then (function (response) {
							detailsViewModel.restaurantCategories = response;
						})
				}
				else {
					DetailsFactory.addCategoryFactory (detailsViewModel.restaurantCategories, catName)
						.then (function (response) {
							detailsViewModel.restaurantCategories = response;
						})
				}
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
					detailsViewModel.tipoBorrado=0;
					DetailsFactory.deleteCurrentRestaurantFactory($routeParams.ID)
						.then(function(response) {
							console.log("Eliminando el restaurante...", response);
							$location.path('/');
						}, function(response) {
							console.log("Se ha producido un error al borrar el restaurante", response);
						})
			},
			updateCurrentRestaurant: function () {
				if (detailsViewModel.restaurantCategories.length < 1) {
					detailsViewModel.error = "Se debe marcar al menos una categoría";
				}
				else {
					var rc = detailsViewModel.functions.convertToString ();
					DetailsFactory.updateCurrentRestaurantFactory (detailsViewModel.restaurant, rc)
						.then (function (response) {
							$location.path ("/restaurantDetails/" + detailsViewModel.restaurant.id);
						},
						function (response) {
							console.log ("'updateCurrentRestaurant': el restaurante no se ha actualizado: ", response.status);
						})
				}
			},
			isCategoryMarked: function (catName) {
				var found = false;
				for (let i = 0; (i < detailsViewModel.restaurantCategories.length) && (!found); i++) {
					if (detailsViewModel.restaurantCategories [i].name === catName) {
						found = true;
					}
				}
				return found;
			},
			convertToString: function () {
				var string = "";
				for (let i = 0; i < detailsViewModel.restaurantCategories.length; i++) {
					string = string + "_" + detailsViewModel.restaurantCategories [i].id;
				}
				return string;
			},
			flipBike: function () {
				if (detailsViewModel.isBikeFriendly == "no-bike-friendly-value") {
					detailsViewModel.isBikeFriendly = "yes-bike-friendly-value";
				}
				if (detailsViewModel.isBikeFriendly == "yes-bike-friendly-value") {
					detailsViewModel.isBikeFriendly = "no-bike-friendly-value";
				}
			},
			flipAvailable: function () {
				detailsViewModel.isAvailable = !detailsViewModel.isAvailable;
				if (detailsViewModel.isAvailable) {
					detailsViewModel.restaurant.available = 1;
				}
				else {
					detailsViewModel.restaurant.available = 0;
				}
			},
			rateRestaurant: function () {
				DetailsFactory.rateRestaurantFactory ($routeParams.ID)
					.then (function (response) {
						detailsViewModel.rateReviewUser = response;
					},
					function (response) {
						console.log ("'loadAllReviews' could not be fetched: ", response.status);
					})
			},
			
			deleteCurrentReview: function() {
					detailsViewModel.tipoBorrado=1;
					DetailsFactory.deleteCurrentReviewFactory($routeParams.ID)
						.then(function(response) {
							console.log("Deleting review...", response);
							$location.path('/');
						}, function(response) {
							console.log("'deleteCurrentReview' could not be fetched: ", response);
						})
			},
			loadRestaurantReviews: function () {
				DetailsFactory.loadRestaurantReviews ($routeParams.ID)
					.then (function (response) {
						detailsViewModel.restaurantReviews = response;
						detailsViewModel.functions.getUserList (detailsViewModel.restaurantReviews);
					},
					function (response) {
						console.log ("'loadAllCategories' could not be fetched: ", response.status);
					})
			},
			getUserList: function(reviewList) {
				DetailsFactory.getUserFactory(reviewList)
					.then(function(response) {
						detailsViewModel.reviewUser = response; //Lista de usuarios que han hecho review en el restaurante.
					}, function(response) {
						console.log("'getUser' could not be fetched:", response.status);
					})
			},
			getCurrentUser: function () {
				UserFactory.getCurrentUserFactory ()
					.then (function (response) {
						detailsViewModel.currentUser = response;
					},
					function (response) {
						console.log ("'getCurrentUser' could not be fetched ", response.status);
					})
			}
		}
		if ($location.absUrl ().includes ("/loadReviews/")) {
			detailsViewModel.functions.getRestaurant();
			detailsViewModel.functions.rateRestaurant ();
			detailsViewModel.functions.loadRestaurantReviews ();
			detailsViewModel.functions.getCurrentUser ();
		}
		if ($location.absUrl ().includes ("/restaurantDetails/")) {
			detailsViewModel.functions.getRestaurant();
			detailsViewModel.functions.loadRestaurantCategories ();
			detailsViewModel.functions.loadBelongsToMe ();
			detailsViewModel.functions.loadAllDishes ();
			detailsViewModel.functions.loadAllCategories ();
		}
	}])