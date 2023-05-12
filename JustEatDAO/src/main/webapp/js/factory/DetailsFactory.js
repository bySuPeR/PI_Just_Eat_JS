angular.module('JustEatDAO')
	.factory("DetailsFactory", ['$http', function($http) {
		var url = 'https://localhost:8443/JustEatDAO/rest/restaurantDetails/';
		var detailsInterface = {
			getRestaurantFact: function(restID) {
				var extraURL = url + restID;
				return $http.get(extraURL)
					.then(function(response) {
						return response.data;
					});
			},
			loadRestaurantCategoriesFactory: function (restID) {
				var extraUrl = url + "loadDetails/" + restID;
				return $http.get (extraUrl)
					.then (function (response) {
						return response.data;
					})
			},
			loadBelongsToMeFactory: function (restID) {
				var extraUrl = url + "loadBelongsToMe/" + restID;
				return $http.get (extraUrl)
					.then (function (response) {
						return response.data;
					})
			},
			loadAllDishesFactory: function (restID) {
				var extraUrl = url + "loadAllDishes/" + restID;
				return $http.get (extraUrl)
					.then (function (response) {
						return response.data;
					})
			},
			deleteCurrentRestaurantFactory: function(restID) {
				console.log("Eliminando mi restaurante...");
				var urlRestDeleted = url + 'deleteRestaurant/' + restID;
				return $http.delete(urlRestDeleted)
					.then(function(response) {
						return response.status;
					});
			},
			createNewRestaurantFactory: function(restaurant) {
				var urlNewRest = url + 'newRestaurant' ;
				return $http.post(urlNewRest, restaurant)
					.then(function(response) {
						return response.data;
					});
			},
			addCategoryFactory: function (restaurantCategories, catName) {
				var extraUrl = url + "addCategory/" + catName;
				return $http.put (extraUrl, restaurantCategories)
					.then (function (response) {
						return response.data;
					})
			},
			deleteCategoryFactory: function (restaurantCategories, pos) {
				var extraUrl = url + "deleteCategory/" + pos;
				return $http.put (extraUrl, restaurantCategories)
					.then (function (response) {
						return response.data;
					})
			},
			updateCurrentRestaurantFactory: function (restaurant, restaurantCategories) {
				var extraUrl = url + "updateCurrentRestaurant/" + restaurantCategories;
				return $http.put (extraUrl, restaurant)
					.then (function (response) {
						return response.status;
					})
			}
		}
		return detailsInterface;
	}])
	




