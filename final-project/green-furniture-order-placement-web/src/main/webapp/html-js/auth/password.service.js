(function() {
	'use strict';

	angular.module('greenFurniture').factory('Password', Password);
	Password.$inject = [ '$resource' ];

	function Password($resource) {
		var service = $resource('api/rest/account/change_password', {}, {});
		return service;
	}
})();
