angular.module('MyHome')
	.controller('GraphicsCtrl', ['GraphicsFactory', function (GraphicsFactory) {
		var GraphicsViewModel = this;
		GraphicsViewModel.n_hosts = -1;
		GraphicsViewModel.n_ava_hosts = -1;
		GraphicsViewModel.t_n_likes = -1;
		GraphicsViewModel.a_n_likes_host = -1;
		GraphicsViewModel.functions = {
			loadGraphicsNumberHosts : function () {
				GraphicsFactory.loadGraphicsNumberHostsFactory ()
				.then (function (response) {
					GraphicsViewModel.n_hosts = response;
				},
				function (response) {
					console.log("ERROR: The number of hosts value could not be fetched.", response);
				})
			},
			loadGraphicsNumberAvailableHosts : function () {
				GraphicsFactory.loadGraphicsNumberAvailableHostsFactory ()
				.then (function (response) {
					GraphicsViewModel.n_ava_hosts = response;
				},
				function (response) {
					console.log("ERROR: The number of available hosts value could not be fetched.", response);
				})
			},
			loadGraphicsNumberLikes : function () {
				GraphicsFactory.loadGraphicsNumberLikesFactory ()
				.then (function (response) {
					GraphicsViewModel.t_n_likes = response;
				},
				function (response) {
					console.log("ERROR: The total number of likes value could not be fetched.", response);
				})
			},
			loadGraphicsAverageNumberLikesHost : function () {
				GraphicsFactory.loadGraphicsAverageNumberLikesHostFactory ()
				.then (function (response) {
					GraphicsViewModel.a_n_likes_host = response;
				},
				function (response) {
					console.log("ERROR: The average number of likes per host value could not be fetched.", response);
				})
			}
		}
		GraphicsViewModel.functions.loadGraphicsAverageNumberLikesHost ();
		GraphicsViewModel.functions.loadGraphicsNumberAvailableHosts ();
		GraphicsViewModel.functions.loadGraphicsNumberHosts ();
		GraphicsViewModel.functions.loadGraphicsNumberLikes ();
	}])