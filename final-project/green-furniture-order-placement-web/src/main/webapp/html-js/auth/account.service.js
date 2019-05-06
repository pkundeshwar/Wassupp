(function() {
	'use strict';

	angular.module('greenFurniture').factory('Account', Account);
	Account.$inject = [ '$resource' ];

	function Account($resource) {
		var service = $resource('api/rest/account', {}, {
			'get' : {
				method : 'GET',
				params : {},
				isArray : false,
				interceptor : {
					response : function(response) {
						return response;
					}
				}
			}
		});

		return service;
	}
})();
