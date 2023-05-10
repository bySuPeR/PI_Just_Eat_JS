angular.module ('JustEatDAO')
	.controller ('RestaurantCtrl', ['RestaurantFactory', function (RestaurantFactory) {
		var restaurantViewModel = this;
		restaurantViewModel.allRestaurants = [];
		restaurantViewModel.allCategories = [];
		restaurantViewModel.tipoBorrado=0;
		restaurantViewModel.searchPrompt = "";
		restaurantViewModel.restState = "all-restaurants";
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
			}
		}
		restaurantViewModel.functions.loadAllCategories ();
		restaurantViewModel.functions.searchByState ();
	}])