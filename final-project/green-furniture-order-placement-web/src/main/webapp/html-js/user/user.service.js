(function() {
	'use strict';

	angular.module('greenFurniture').factory('User', User);
	User.$inject = [ '$resource' ];

	function User($resource) {
		var service = $resource('api/rest/users/:login', {}, {
			'query' : {
				method : 'GET',
				isArray : true
			},
			'get' : {
				method : 'GET',
				transformResponse : function(data) {
					data = angular.fromJson(data);
					return data;
				}
			},
			'save' : {
				method : 'POST'
			},
			'update' : {
				method : 'PUT'
			},
			'delete' : {
				method : 'DELETE'
			}
		});

		return service;
	}
})();
