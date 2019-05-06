(function() {
	'use strict';

	angular.module('greenFurniture').factory('Register', Register);
	Register.$inject = [ '$resource' ];

	function Register($resource) {
		var service = $resource('api/rest/register', {}, {});
		return service;
	}
})();
