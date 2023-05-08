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
	})