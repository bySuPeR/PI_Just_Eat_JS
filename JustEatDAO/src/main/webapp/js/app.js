angular.module('JustEatDAO', ['ngRoute'])
.config (function ($routeProvider) {
	$routeProvider
    	.when("/", {
    		controller: "RestaurantCtrl",
    		controllerAs: "RestaurantVM",
    		templateUrl: "Search.html"
		})
		.when("/restaurantDetails/:ID", {
        	controller: "DetailsCtrl",
            controllerAs: "DetailsVM",
            templateUrl: "Res_details.html"
        })    
        .when("/UpdateUser", {
        	controller: "UserCtrl",
            controllerAs: "UserVM",
            templateUrl: "UpdateUser.html"
        })
        .when ("/UpdateRestaurant/:ID", {
			controller: "DetailsCtrl",
			controllerAs: "DetailsVM",
			templateUrl: "UpdateRestaurant.html"
		})
		.when("/deleteUser", {
			controller: "UserCtrl",
			controllerAs: "UserVM",
			templateUrl: "ConfirmDelete.html"
		})
		.when("/deleteRestaurant/:ID", {
         	controller: "DetailsCtrl",
            controllerAs: "DetailsVM",
            templateUrl: "ConfirmDelete.html"
        }) 
        .when("/myRestaurants", {
        	controller: "RestaurantCtrl",
            controllerAs: "RestaurantVM",
            templateUrl: "Restaurants.html"
        })
        .when("/AddRestaurant", {
        	controller: "RestaurantCtrl",
            controllerAs: "RestaurantVM",
            templateUrl: "AddRestaurant.html"
        })
        .when("/manageDishes/:ID", {
        	controller: "DishCtrl",
            controllerAs: "DishVM",
            templateUrl: "UpdateDishes.html"
        })
        .when("/deleteDish/:ID", {
        	controller: "DishCtrl",
            controllerAs: "DishVM",
            templateUrl: "ConfirmDelete.html"
        })
        .when("/MyOrders", {
        	controller: "OrderCtrl",
            controllerAs: "OrderVM",
            templateUrl: "MyOrders.html"
        })
        .when("/checkOrder/:ID", {
        	controller: "OrderCtrl",
            controllerAs: "OrderVM",
            templateUrl: "CheckOrder.html"
        })
        .when ("/AddDish/:ID", {
			controller: "DishCtrl",
			controllerAs: "DishVM",
			templateUrl: "AddDish.html"
		})
    })