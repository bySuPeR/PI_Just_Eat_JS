angular.module('JustEatDAO', ['ngRoute'])
.config (function ($routeProvider) {
	$routeProvider
    	.when("/", {
    		controller: "RestaurantCtrl",
    		controllerAs: "RestaurantVM",
    		templateUrl: "Search.html",
    		resolve: {
    			delay: function($q, $timeout) {
    				var delay = $q.defer();
    				$timeout(delay.resolve, 500);
    				return delay.promise;
    			}
    		}
		})
		.when("/restaurantDetails/:ID", {
        	controller: "DetailsCtrl",
            controllerAs: "DetailsVM",
            templateUrl: "Res_details.html",
            resolve: {
            	delay: function($q, $timeout) {
                	var delay = $q.defer();
                    $timeout(delay.resolve,0);
                    return delay.promise;
                }
            }
        })    
        .when("/UpdateUser", {
        	controller: "UserCtrl",
            controllerAs: "UserVM",
            templateUrl: "UpdateUser.html",
            resolve: {
            	delay: function($q, $timeout) {
                	var delay = $q.defer();
                    $timeout(delay.resolve,0);
                    return delay.promise;
                }
            }
        })
        .when ("/UpdateRestaurant/:ID", {
			controller: "DetailsCtrl",
			controllerAs: "DetailsVM",
			templateUrl: "UpdateRestaurant.html",
			resolve: {
				delay: function ($q, $timeout) {
					var delay = $q.defer ();
					$timeout (delay.resolve, 0);
					return delay.promise;
				}
			}
		})
	})