angular.module ('JustEatDAO')
	.controller ('OrderCtrl', ['OrderFactory', '$routeParams','$location', function (OrderFactory, $routeParams,$location) {
		var orderViewModel = this;
		orderViewModel.myOrders= [];
		orderViewModel.dishList= [];
		orderViewModel.tipoBorrado=0;
		orderViewModel.functions = {
			loadAllMyOrders: function() {
				OrderFactory.loadAllMyOrdersFactory()
					.then(function(response) {
						console.log("Load my orders...", response);
						orderViewModel.myOrders = response;
					}, function(response) {
						console.log("'loadAllMyOrders' could not be fetched: ", response);
					})
			},
			
			checkOrder: function(orderID){
				OrderFactory.checkOrderFactory(orderID)
				.then(function(response) {
						console.log("Check my order...", response);
						orderViewModel.dishList = response;
					}, function(response) {
						console.log("'checkOrder' could not be fetched: ", response);
					})
			},
			
			loadOrder: function(orderID){
				$location.path('checkOrder/' + orderID);
			}
			
		}
			orderViewModel.functions.loadAllMyOrders();
			if($routeParams.ID!=null)
				orderViewModel.functions.checkOrder($routeParams.ID);
		
}])