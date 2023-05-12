angular.module ('JustEatDAO')
	.factory ('RestaurantFactory', ['$http', function ($http) {
		var url = 'https://localhost:8443/JustEatDAO/rest/Restaurant/';
		var restaurantInterface = {
			loadAllCategoriesFactory: function () {
				var extraUrl = url + "loadAllCategories";
				return $http.get (extraUrl)
					.then (function (response) {
						return response.data;
					})
			},
			searchAmongRestaurantsFactory: function (searchPrompt) {
				var extraUrl = url + "searchAmongRestaurants/" + searchPrompt;
				return $http.get (extraUrl)
					.then (function (response) {
						return response.data;
					})
			},
			searchByCategoryFactory: function (searchCategoryID) {
				var extraUrl = url + "searchByCategory/" + searchCategoryID;
				return $http.get (extraUrl)
					.then (function (response) {
						return response.data;
					})
			},
			searchByStateFactory: function (restState) {
				var extraUrl = url + "searchByState/" + restState;
				return $http.get (extraUrl)
					.then (function (response) {
						return response.data;
					})
			},
			
			loadAllMyRestaurantsFactory: function() {
				var urlMyRests = url + 'myRestaurants';
				return $http.get(urlMyRests)
					.then(function(response) {
						return response.data;
					});
			},
			createNewRestaurantFactory: function (newRestaurant, str) {
				var extraUrl = url + "createNewRestaurant/" + str;
				return $http.post (extraUrl, newRestaurant)
					.then (function (response) {
						return response.status;
					})
			}
		}
		return restaurantInterface;
	}])