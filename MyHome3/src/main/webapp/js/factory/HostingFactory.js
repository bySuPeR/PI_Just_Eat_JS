angular.module('MyHome')
    .factory("HostingFactory", ['$http', function($http) {
        var url = 'https://localhost:8443/MyHome3/rest/Hosting/';
        var HostingInterface = {
            loadHostsFactory: function () {
                console.log("Fetching all hosts...");
                var extraUrl = url + "loadHosts";
                return $http.get (extraUrl)
                .then (function (response) { //Porque es HTTP GET, se pone response.data, si no, sería response.status
					return response.data;
				})
        	},
        	searchNameDescriptionFactory : function (nameDescription) {
				console.log ("Fetching all searched hosts...");
				var extraUrl = url + "searchedHosts/" + nameDescription;
				return $http.get (extraUrl)
				.then (function (response) { //Porque es HTTP GET, se pone response.data, si no, sería response.status
					return response.data;
				})
			},
			reverseFavouritesOrderFactory : function () {
				console.log ("Reversing order...");
				var extraUrl = url + "reversedOrder/";
				return $http.get (extraUrl)
				.then (function (response) {
					return response.data;
				})
			},
			statusShowFactory : function (hostStatus) {
				console.log ("Retrieving status data...");
				var extraUrl = url + "status/" + hostStatus;
				return $http.get (extraUrl)
				.then (function (response) {
					return response.data;
				})
			},
			loadMyHostsFactory : function () {
				console.log ("Retrieving data from my hosts...");
				var extraUrl = url + "MyHosts/";
				return $http.get (extraUrl)
				.then (function (response) {
					return response.data;
				})
			},
			addNewHostFactory : function (newHost, categoryStr) {
				var extraUrl = url + "newHost/" + categoryStr;
				return $http.post (extraUrl, newHost)
				.then (function (response) {
					return response.status;
				})
			},
			filterServicesFactory : function (selectedServices) {
				console.log("Fetching all hosts that meet the condition...");
                var extraUrl = url + "loadServiceHosts/" + selectedServices;
                return $http.get (extraUrl)
                .then (function (response) { //Porque es HTTP GET, se pone response.data, si no, sería response.status
					return response.data;
				})
			},
			searchHostByCategoryFactory : function (pickedCategory) {
				console.log ("Fetching all hosts that meet the required condition...");
				var extraUrl = url + "loadCategoryHosts/" + pickedCategory;
                return $http.get (extraUrl)
                .then (function (response) { //Porque es HTTP GET, se pone response.data, si no, sería response.status
					return response.data;
				})
			},
			searchHostByPriceFactory : function (selectedPrice) {
				console.log ("Fetching all hosts that meet the required price condition...");
				var extraUrl = url + "loadPriceHosts/" + selectedPrice;
                return $http.get (extraUrl)
                .then (function (response) { //Porque es HTTP GET, se pone response.data, si no, sería response.status
					return response.data;
				})
			}
        }
        return HostingInterface;
    }])