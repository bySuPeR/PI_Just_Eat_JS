angular.module ('JustEatDAO')
	.controller ('UserCtrl', ['UserFactory', function (UserFactory) {
		var userViewModel = this;
		userViewModel.user = {}
		userViewModel.functions = {
			getUser: function () {
				UserFactory.getUserFactory ()
					.then (function (response) {
						userViewModel.user = response;
						console.log ("Getting this user: ", userViewModel.user.id, ". Response: ", response);
					}, function (response) {
						console.log ("Error fetching this user in UserCtrl: ", response)
					})
			}
		}
		userViewModel.functions.getUser ();
	}])