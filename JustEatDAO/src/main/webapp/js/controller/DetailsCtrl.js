angular.module('JustEatDAO')
	.controller('DetailsCtrl', ['DetailsFactory', 'UserFactory', '$routeParams','$location', '$window', 'RestaurantFactory', function (DetailsFactory, UserFactory, $routeParams, $location, $window, RestaurantFactory) {
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
		detailsViewModel.currentReview = {};
		detailsViewModel.alreadyReviewed = false;
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
					DetailsFactory.deleteCurrentReviewFactory ($routeParams.ID)
						.then(function(response) {
							$location.path('/loadReviews/' + detailsViewModel.restaurant.id);
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
						console.log("'getUser' could not be fetched: ", response.status);
					})
			},
			
			getLikeRestaurant: function() {
				DetailsFactory.getLikeRestaurantFactory($routeParams.ID)
					.then(function(response) {
						console.log("Getting apartament's like...", response);
						detailsViewModel.like = response;
					}, function(response) {
						console.log("'getLikeRestaurant' could not be fetched: ", response);
					})
			},
			giveLike: function() {
			detailsViewModel.like=1;
				DetailsFactory.giveLikeFactory(detailsViewModel.restaurant)
					.then(function(response) {
						$window.location.reload();
						console.log("Giving like...", response);
					}, function(response) {
						console.log("'giveLike' could not be fetched: ", response);
					})
			},
			removeLike: function() {
				detailsViewModel.like=0;
				DetailsFactory.removeLikeFactory(detailsViewModel.restaurant)
					.then(function(response) {
						$window.location.reload();
						console.log("Removing like...", response);
					}, function(response) {
						console.log("'removeLike' could not be fetched: ", response);
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
			},
			loadCurrentReview: function () {
				DetailsFactory.loadCurrentReviewFactory ($routeParams.ID)
					.then (function (response) {
						detailsViewModel.currentReview = response;
					},
					function (response) {
						console.log ("'loadCurrentReview' could not be fetched", response.status);
					})
			},
			updateCurrentReview: function () {
				DetailsFactory.updateCurrentReviewFactory (detailsViewModel.currentReview, $routeParams.ID)
					.then (function (response) {
						$location.path('/loadReviews/' + detailsViewModel.restaurant.id);
					},
					function (response) {
						console.log ("'updateCurrentReview' could not be updated ", response.status);
					})
			},
			addReview: function () {
				DetailsFactory.addReviewFactory (detailsViewModel.currentReview, $routeParams.ID)
					.then (function (response) {
						$window.location.reload();
						detailsViewModel.alreadyReviewed = true;
					},
					function (response) {
						console.log ("'addReview' could not be added ", response.status);
					})
			},
			isAlreadyReviewed: function () {
				DetailsFactory.isAlreadyReviewedFactory ($routeParams.ID)
					.then (function (response) {
						detailsViewModel.alreadyReviewed = response;
					},
					function (response) {
						console.log ("'isAlreadyReviewed' could not be fetched ", response.status);
					})
			}
		}
		detailsViewModel.functions.getRestaurant();
		if ($location.absUrl ().includes ("/loadReviews/")) {
			detailsViewModel.functions.rateRestaurant ();
			detailsViewModel.functions.getCurrentUser ();
			detailsViewModel.functions.loadRestaurantReviews ();
			detailsViewModel.functions.loadBelongsToMe ();
			detailsViewModel.functions.isAlreadyReviewed ();
		}
		if ($location.absUrl ().includes ("/deleteRestaurant/")) {
			detailsViewModel.tipoBorrado = 0;
		}
		if ($location.absUrl ().includes ("/deleteReview/")) {
			detailsViewModel.tipoBorrado = 1;
		}
		if ($location.absUrl ().includes ("/restaurantDetails/")) {
			detailsViewModel.functions.loadRestaurantCategories ();
			detailsViewModel.functions.loadAllDishes ();
			detailsViewModel.functions.loadAllCategories ();
		}
		if ($location.absUrl ().includes ("/rateRestaurant/")) {
			detailsViewModel.tipoBorrado = 1;
			detailsViewModel.functions.loadCurrentReview ();
		}
		detailsViewModel.functions.loadRestaurantCategories ();
		detailsViewModel.functions.loadBelongsToMe ();
		detailsViewModel.functions.loadAllDishes ();
		detailsViewModel.functions.loadAllCategories ();
		detailsViewModel.functions.rateRestaurant();
		detailsViewModel.functions.loadRestaurantReviews ();
		detailsViewModel.functions.getLikeRestaurant ();
	}])