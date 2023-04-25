angular.module('MyHome')
    .factory("UsersFactory", ['$http', function ($http) {
        var url = 'https://localhost:8443/MyHome3/rest/Users/';
        var UsersInterface = {
            loadUserDataFactory : function () {
                console.log("Fetching current user...");
                var extraUrl = url + "loadUser";
                return $http.get (extraUrl)
                .then (function (response) { //Porque es HTTP GET, se pone response.data, si no, sería response.status
					return response.data;
				})
        	},
        	deleteCurrentUserFactory : function () {
				console.log ("Deleting current user...");
                var extraUrl = url + "deleteUser";
                return $http.delete (extraUrl)
                .then (function (response) { //Porque es HTTP GET, se pone response.data, si no, sería response.status
					return response.status;
				})
			},
			updateCurrentUserFactory : function (currentUser) { //Siempre que se vaya a actualizar algún dato, hay que pasar por parámetro el dato que se va a actualizar.
				console.log ("Updating current user...");
				var extraUrl = url + "updateUser";
				return $http.put (extraUrl, currentUser)
				.then (function (response) {
					return response.status;
				})
			}
        }
        return UsersInterface;
    }])