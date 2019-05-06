(function() {
	'use strict';

	angular.module('greenFurniture').factory('Author', Author);
	Author.$inject = [ '$resource' ];

	function Author($resource) {
		var service = $resource('api/rest/authors/:id', {}, {
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
