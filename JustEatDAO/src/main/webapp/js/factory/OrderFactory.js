angular.module ('JustEatDAO')
	.factory ('OrderFactory', ['$http', function ($http) {
		var url = 'https://localhost:8443/JustEatDAO/rest/MyOrders/';
		var orderInterface = {
			loadAllMyOrdersFactory: function() {
				return $http.get(url)
					.then(function(response) {
						return response.data;
					});
			},
			checkOrderFactory: function(orderID) {
				var urlOrder = url + 'checkOrder/' + orderID;
				return $http.get(urlOrder)
					.then(function(response) {
						return response.data;
					});
			},
			makeOrderFactory: function (orderedDishes) {
				var extraUrl = url + "makeOrder/" + orderedDishes;
				return $http.post (extraUrl)
					.then (function (response) {
						return response;
					})
			}
		}
	return orderInterface;
}])