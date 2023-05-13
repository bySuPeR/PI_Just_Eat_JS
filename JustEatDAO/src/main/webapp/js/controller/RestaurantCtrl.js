angular.module ('JustEatDAO')
	.controller ('RestaurantCtrl', ['RestaurantFactory', '$location', function (RestaurantFactory, $location) {
		var restaurantViewModel = this;
		restaurantViewModel.allRestaurants = [];
		restaurantViewModel.myRestaurants = [];
		restaurantViewModel.allCategories = [];
		restaurantViewModel.tipoBorrado=0;
		restaurantViewModel.searchPrompt = "";
		restaurantViewModel.restState = "all-restaurants";
		restaurantViewModel.newRestaurant = {};
		restaurantViewModel.newRestaurant.bikeFriendly = 0;
		restaurantViewModel.newRestaurant.available = 0;
		restaurantViewModel.categoryStr = [];
		restaurantViewModel.error = "";
		restaurantViewModel.functions = {
			loadAllCategories: function () {
				RestaurantFactory.loadAllCategoriesFactory ()
					.then (function (response) {
						restaurantViewModel.allCategories = response;
					},
					function (response) {
						console.log ("'loadAllCategories' could not be fetched: ", response.status);
					})
			},
			searchAmongRestaurants: function () {
				RestaurantFactory.searchAmongRestaurantsFactory (restaurantViewModel.searchPrompt)
					.then (function (response) {
						restaurantViewModel.allRestaurants = response;
					},
					function (response) {
						console.log ("'searchAmongRestaurants' could not be fetched: ", response.status);
					})
			},
			searchByCategory: function (searchCategoryID) {
				console.log ("Searching restaurants by ", searchCategoryID, "...")
				RestaurantFactory.searchByCategoryFactory (searchCategoryID)
					.then (function (response) {
						restaurantViewModel.restState="";
						restaurantViewModel.allRestaurants = response;
					},
					function (response) {
						console.log ("'searchByCategory' could not be fetched: ", response.status);
					})
			},
			searchByState: function () {
				RestaurantFactory.searchByStateFactory (restaurantViewModel.restState)
					.then (function (response) {
						restaurantViewModel.allRestaurants = response;
					},
					function (response) {
						console.log ("'searchByState' could not be fetched: ", response.status);
					})
			},
			loadAllMyRestaurants: function() {
				RestaurantFactory.loadAllMyRestaurantsFactory()
					.then(function(response) {
						restaurantViewModel.myRestaurants = response;
					}, function(response) {
						console.log("'loadAllMyRestaurants' could not be fetched: ", response);
					})
			},
			createNewRestaurant: function () {
				var str = restaurantViewModel.functions.convertToString ();
				if (str != "") {
					RestaurantFactory.createNewRestaurantFactory (restaurantViewModel.newRestaurant, str)
						.then (function (response) {
							$location.path ("/myRestaurants");
						},
						function (response) {
							console.log ("'createNewRestaurant': could not be created: ", response);
						})
				}
				else {
					restaurantViewModel.error = "Hay que añadir alguna categoría.";
				}
			},
			flipBike: function () {
				if (restaurantViewModel.newRestaurant.bikeFriendly == 0) {
					restaurantViewModel.newRestaurant.bikeFriendly = 1;
				}
				else {
					restaurantViewModel.newRestaurant.bikeFriendly = 0;
				}
			},
			flipAvailable: function () {
				if (restaurantViewModel.newRestaurant.available == 0) {
					restaurantViewModel.newRestaurant.available = 1;
				}
				else {
					restaurantViewModel.newRestaurant.available = 0;
				}
			},
			markCategory: function (catID) {
				found = false;
				pos = -1;
				for (let i = 0; i < restaurantViewModel.categoryStr.length; i++) {
					if (restaurantViewModel.categoryStr [i] == catID) {
						found = true;
						pos = i;
					}
				}
				if (found) {
					while (pos < restaurantViewModel.categoryStr.length) {
						restaurantViewModel.categoryStr [pos] = restaurantViewModel.categoryStr [pos + 1];
						pos++;
					}
				}
				else {
					restaurantViewModel.categoryStr [restaurantViewModel.categoryStr.length] = catID;
				}
			},
			convertToString: function () {
				var str = "";
				for (let i = 0; i < restaurantViewModel.categoryStr.length; i++) {
					str = str + "_" + restaurantViewModel.categoryStr [i];
				}
				return str;
			}
		}
		restaurantViewModel.functions.loadAllCategories ();
		restaurantViewModel.functions.loadAllMyRestaurants ();
		restaurantViewModel.functions.searchByState ();
	}])