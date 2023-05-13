angular.module ('JustEatDAO')
	.controller ('OrderCtrl', ['OrderFactory', 'DetailsFactory', '$routeParams','$location', function (OrderFactory, DetailsFactory, $routeParams,$location) {
		var orderViewModel = this;
		orderViewModel.myOrders= [];
		orderViewModel.dishList= [];
		orderViewModel.tipoBorrado = 0;
		orderViewModel.restName = "";
		orderViewModel.functions = {
			loadAllMyOrders: function() {
				OrderFactory.loadAllMyOrdersFactory()
					.then(function(response) {
						orderViewModel.myOrders = response;
					}, function(response) {
						console.log("'loadAllMyOrders' could not be fetched: ", response);
					})
			},
			checkOrder: function(orderID){
				OrderFactory.checkOrderFactory(orderID)
				.then(function(response) {
						orderViewModel.dishList = response;
						orderViewModel.restName = orderViewModel.functions.loadRestaurant ();
					}, function(response) {
						console.log("'checkOrder' could not be fetched: ", response);
					})
			},
			loadOrder: function(orderID){
				$location.path ('checkOrder/' + orderID);
			},
			loadRestaurant: function () {
				DetailsFactory.getRestaurantFact (orderViewModel.dishList [0].id)
					.then (function (response) {
						orderViewModel.restName = response.name;
					},
					function (response) {
						console.log ("'loadRestaurant': the restaurant could not be fetched: ", response.status);
					})
			}
		}
		orderViewModel.functions.loadAllMyOrders ();
		if ($routeParams.ID != null) {
			orderViewModel.functions.checkOrder ($routeParams.ID);
		}
}])