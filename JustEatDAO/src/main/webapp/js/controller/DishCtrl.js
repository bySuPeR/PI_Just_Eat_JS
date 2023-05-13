angular.module ('JustEatDAO')
	.controller ('DishCtrl', ['DishFactory','DetailsFactory', '$routeParams','$location', function (DishFactory,DetailsFactory, $routeParams,$location) {
		var dishViewModel = this;
		dishViewModel.restID = $routeParams.ID;
		dishViewModel.dishesList = [];
		dishViewModel.tipoBorrado = 0;
		dishViewModel.error = "";
		dishViewModel.newDish = {};
		dishViewModel.functions = {
			getDishesbyRestaurant: function() {
				DishFactory.getDishesbyRestaurantFactory ($routeParams.ID)
					.then (function (response) {
						dishViewModel.dishesList = response;
					}, function(response) {
						console.log("'getDishesbyRestaurant' could not be fetched: ", response.status);
					})
			},
			deleteCurrentDish: function() {
					DishFactory.deleteCurrentDishFactory($routeParams.ID)
						.then(function(response) {
							$location.path ('/myRestaurants');
						}, function(response) {
							console.log("'deleteCurrentDish' could not be fetched: ", response);
						})
			},
			updateDish: function (dishID) {
				dishViewModel.newDish = dishViewModel.functions.findDish (dishID);
				DishFactory.updateDishFactory (dishViewModel.newDish)
					.then (function (response) {
						$location.path ('/myRestaurants');
					},
					function (response) {
						console.log ("'updateDish' could not be updated: ", response.status);
					})
			},
			findDish: function (dishID) {
				var found = false;
				var pos = -1;
				for (let i = 0; (i < dishViewModel.dishesList.length) && (!found); i++) {
					if (dishViewModel.dishesList [i].id == dishID) {
						found = true;
						pos = i;
					}
				}
				return dishViewModel.dishesList [pos];
			}
		}
		dishViewModel.functions.getDishesbyRestaurant ();
}])