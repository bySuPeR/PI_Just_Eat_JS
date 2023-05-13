angular.module ('JustEatDAO')
	.factory ('DishFactory', ['$http', function ($http) {
		var url = 'https://localhost:8443/JustEatDAO/rest/manageDishes/';
		var dishInterface = {
			getDishesbyRestaurantFactory: function (restID) {
				var extraUrl = url + restID;
				return $http.get (extraUrl)
					.then (function (response) {
						return response.data;
					})
			},
			deleteCurrentDishFactory: function(dishID) {
				var urlDishDeleted = url + 'deleteDish/' + dishID;
				return $http.delete(urlDishDeleted)
					.then(function(response) {
						return response.status;
					});
			},
			updateDishFactory: function (dish) {
				var extraUrl = url + 'UpdateDish/' + dish.id;
				return $http.put (extraUrl, dish)
					.then (function (response) {
						return response;
					})
			}
		}
	return dishInterface;
}])