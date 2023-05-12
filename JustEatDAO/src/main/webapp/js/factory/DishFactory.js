angular.module ('JustEatDAO')
	.factory ('DishFactory', ['$http', function ($http) {
		var url = 'https://localhost:8443/JustEatDAO/rest/manageDishes/';
		var dishInterface = {
			getDishesbyRestaurantFactory: function (restaurant) {
				var extraUrl = url + restaurant.id;
				return $http.get (extraUrl)
					.then (function (response) {
						return response.data;
					})
			},
			deleteCurrentDishFactory: function(dishID) {
				console.log("Deleting dish...");
				var urlDishDeleted = url + 'deleteDish/' + dishID;
				return $http.delete(urlDishDeleted)
					.then(function(response) {
						return response.status;
					});
			}
		}
	return dishInterface;
}])