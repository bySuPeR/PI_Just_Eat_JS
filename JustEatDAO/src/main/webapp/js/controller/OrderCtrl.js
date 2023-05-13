angular.module ('JustEatDAO')
	.controller ('OrderCtrl', ['OrderFactory', 'DetailsFactory', '$routeParams','$location', function (OrderFactory, DetailsFactory, $routeParams,$location) {
		var orderViewModel = this;
		orderViewModel.myOrders= [];
		orderViewModel.dishList = [];
		orderViewModel.tipoBorrado = 0;
		orderViewModel.restName = "";
		orderViewModel.newOrderRestaurant = {};
		orderViewModel.orderedDishList = [];
		orderViewModel.error = "";
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
						orderViewModel.functions.loadRestaurant ();
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
			},
			loadNewOrderRestaurant: function () {
				DetailsFactory.getRestaurantFact ($routeParams.ID)
					.then (function (response) {
						orderViewModel.newOrderRestaurant = response;
					},
					function (response) {
						console.log ("'loadNewOrderRestaurant': the restaurant to be ordered could not be fetched: ", response.status)
					})
			},
			loadAllRestaurantDishes: function () {
				DetailsFactory.loadAllDishesFactory ($routeParams.ID)
					.then (function (response) {
						orderViewModel.dishList = response;
					},
					function (response) {
						console.log ("'loadAllRestaurantDishes': could not be fetched: ", response.status);
					})
			},
			addDishToOrder: function (dishID) {
				var found = false;
				var pos = -1;
				for (let i = 0; i < orderViewModel.orderedDishList.length; i++) {
					if (orderViewModel.orderedDishList [i] == dishID) {
						found = true;
						pos = i;
					}
				}
				if (found) {
					orderViewModel.orderedDishList.splice (pos, 1);
				}
				else {
					orderViewModel.orderedDishList.push (dishID);
				}
				console.log (orderViewModel.orderedDishList);
			},
			makeOrder: function () {
				if (orderViewModel.orderedDishList.length > 0) {
					var str = "";
					for (let i = 0; i < orderViewModel.orderedDishList.length; i++) {
						str = str + "_" + orderViewModel.orderedDishList [i];
					}
					OrderFactory.makeOrderFactory (str)
						.then (function (response) {
							$location.path ("/MyOrders");
						},
						function (response) {
							console.log ("'makeOrder': the was a problem ", response.status);
						})
				}
				else {
					orderViewModel.error = "Se debe escoger, al menos, un plato."
				}
			}
		}
		orderViewModel.functions.loadAllMyOrders ();
		if ($routeParams.ID != null && ($location.absUrl ().includes ("/checkOrder/"))) {
			orderViewModel.functions.checkOrder ($routeParams.ID);
		}
		if ($routeParams.ID != null && ($location.absUrl ().includes ("/AddOrder/"))) {
			orderViewModel.functions.loadNewOrderRestaurant ();
			orderViewModel.functions.loadAllRestaurantDishes ();
		}
}])