angular.module('MyHome')
    .factory("DetailsFactory", ['$http', function ($http) {
        var url = 'https://localhost:8443/MyHome3/rest/Details/';
        var DetailsInterface = {
            loadHostDetailsFactory: function (HostID) {
                console.log("Fetching host data...");
                var extraUrl = url + "loadHostDetails/" + HostID;
                return $http.get (extraUrl)
                .then (function (response) { //Porque es HTTP GET, se pone response.data, si no, sería response.status
					return response.data;
				})
        	},
        	loadHostCategoriesFactory : function (HostID) {
				console.log ("Fetching all host category data...");
				var extraUrl = url + "loadHostCategoryData/" + HostID;
				return $http.get (extraUrl)
				.then (function (response) {
					return response.data;
				})
			},
        	loadLikeFactory: function (HostID) {
                console.log("Fetching host like data...");
                var extraUrl = url + "loadHostLikeDetails/" + HostID;
                return $http.get (extraUrl)
                .then (function (response) { //Porque es HTTP GET, se pone response.data, si no, sería response.status
					return response.data;
				})
        	},
        	itsMineFactory : function (HostID) {
				console.log ("Fetching belonging data...");
				var extraUrl = url + "loadHostBelongingData/" + HostID;
				return $http.get (extraUrl)
                .then (function (response) { //Porque es HTTP GET, se pone response.data, si no, sería response.status
					return response.data;
				})
			},
			deleteHostFactory : function (HostID) {
				console.log ("Fetching data to be deleted...");
				var extraUrl = url + "deleteHost/" + HostID;
				return $http.delete (extraUrl)
				.then (function (response) {
					return response.status;
				})
			},
			editHostFactory : function (host, categoryStr) {
				var extraUrl = url + "editHost/" + categoryStr;
				return $http.put (extraUrl, host)
				.then (function (response) {
					return response.status;
				})
			},
			bookHostFactory : function (host) {
				var extraUrl = url + "updateAvailability/";
				return $http.put (extraUrl, host)
				.then (function (response) {
					return response.status;
				})
			},
			likeHostFactory : function (host) {
				var extraUrl = url + "likeHost/";
				return $http.put (extraUrl, host)
				.then (function (response) {
					return response.status;
				})
			}
        }
        return DetailsInterface;
    }])