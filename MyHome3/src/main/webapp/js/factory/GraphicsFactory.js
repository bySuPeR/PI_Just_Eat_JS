angular.module('MyHome')
    .factory("GraphicsFactory", ['$http', function ($http) {
        var url = 'https://localhost:8443/MyHome3/rest/Graphics/';
        var GraphicsInterface = {
            loadGraphicsNumberHostsFactory: function () {
                console.log("Calculating number of hosts...");
                var extraUrl = url + "loadNumberHosts";
                return $http.get (extraUrl)
                .then (function (response) { //Porque es HTTP GET, se pone response.data, si no, sería response.status
					return response.data;
				})
        	},
        	loadGraphicsNumberAvailableHostsFactory: function () {
                console.log("Calculating number of available hosts...");
                var extraUrl = url + "loadNumberAvailableHosts";
                return $http.get (extraUrl)
                .then (function (response) { //Porque es HTTP GET, se pone response.data, si no, sería response.status
					return response.data;
				})
        	},
        	loadGraphicsNumberLikesFactory: function () {
                console.log("Calculating number of likes...");
                var extraUrl = url + "loadNumberLikes";
                return $http.get (extraUrl)
                .then (function (response) { //Porque es HTTP GET, se pone response.data, si no, sería response.status
					return response.data;
				})
        	},
        	loadGraphicsAverageNumberLikesHostFactory: function () {
                console.log("Calculating average number of likes per host...");
                var extraUrl = url + "loadNumberLikesHosts";
                return $http.get (extraUrl)
                .then (function (response) { //Porque es HTTP GET, se pone response.data, si no, sería response.status
					return response.data;
				})
        	}
        }
        return GraphicsInterface;
    }])