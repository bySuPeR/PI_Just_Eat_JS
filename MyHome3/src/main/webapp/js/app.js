angular.module('MyHome', ['ngRoute'])
.config(function($routeProvider){
	$routeProvider
    	.when("/", {
    		controller: "HostingCtrl",
    		controllerAs: "HostingVM",
    		templateUrl: "Webpage.html",
    		resolve: {
    			delay: function($q, $timeout) {
    				var delay = $q.defer();
    				$timeout(delay.resolve, 500);
    				return delay.promise;
    			}
    		}
    	})
    	.when ("/Favourites", {
			controller: "HostingCtrl",
			controllerAs: "HostingVM",
			templateUrl: "Favourites.html",
			resolve: {
    			delay: function($q, $timeout) {
    				var delay = $q.defer();
    				$timeout(delay.resolve, 500);
    				return delay.promise;
    			}
    		}
		})
		.when ("/Profile", {
			controller: "UsersCtrl",
			controllerAs: "UsersVM",
			templateUrl: "Profile.html",
			resolve: {
				delay: function ($q, $timeout) {
					var delay = $q.defer ();
					$timeout (delay.resolve, 500);
					return delay.promise;
				}
			}
		})
		.when ("/Status", {
			controller: "HostingCtrl",
			controllerAs: "HostingVM",
			templateUrl: "Status.html",
			resolve: {
				delay: function ($q, $timeout) {
					var delay = $q.defer ();
					$timeout (delay.resolve, 500);
					return delay.promise;
				}
			}
		})
		.when ("/MyHosts", {
			controller: "HostingCtrl",
			controllerAs: "HostingVM",
			templateUrl: "MyHosts.html",
			resolve: {
				delay: function ($q, $timeout) {
					var delay = $q.defer ();
					$timeout (delay.resolve, 500);
					return delay.promise;
				}
			}
		})
		.when ("/AddHost", {
			controller: "HostingCtrl",
			controllerAs: "HostingVM",
			templateUrl: "AddHost.html",
			resolve: {
				delay: function ($q, $timeout) {
					var delay = $q.defer ();
					$timeout (delay.resolve, 500);
					return delay.promise;
				}
			}
		})
		.when ("/EditHost/:ID", {
			controller: "DetailsCtrl",
			controllerAs: "DetailsVM",
			templateUrl: "EditHost.html",
			resolve: {
				delay: function ($q, $timeout) {
					var delay = $q.defer ();
					$timeout (delay.resolve, 500);
					return delay.promise;
				}
			}
		})
		.when ("/Details/:ID", {
			controller: "DetailsCtrl",
			controllerAs: "DetailsVM",
			templateUrl: "Details.html",
			resolve: {
				delay: function ($q, $timeout) {
					var delay = $q.defer ();
					$timeout (delay.resolve, 500);
					return delay.promise;
				}
			}
		})
		.when ("/DeleteHost/:ID", {
			controller: "DetailsCtrl",
			controllerAs: "DetailsVM",
			templateUrl: "DeleteHost.html",
			resolve: {
				delay: function ($q, $timeout) {
					var delay = $q.defer ();
					$timeout (delay.resolve, 500);
					return delay.promise;
				}
			}
		})
		.when ("/Services", {
			controller: "HostingCtrl",
			controllerAs: "HostingVM",
			templateUrl: "Services.html",
			resolve: {
				delay: function ($q, $timeout) {
					var delay = $q.defer ();
					$timeout (delay.resolve, 500);
					return delay.promise;
				}
			}
		})
		.when ("/Graphics", {
			controller: "GraphicsCtrl",
			controllerAs: "GraphicsVM",
			templateUrl: "Graphics.html",
			resolve: {
				delay: function ($q, $timeout) {
					var delay = $q.defer ();
					$timeout (delay.resolve, 500);
					return delay.promise;
				}
			}
		})
    })