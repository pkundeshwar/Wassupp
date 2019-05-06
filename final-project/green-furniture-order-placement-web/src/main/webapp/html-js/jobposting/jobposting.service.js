(function() {
	'use strict';

	angular.module('greenFurniture').factory('JobPosting', JobPosting);
	JobPosting.$inject = [ '$resource' ];

	function JobPosting($resource) {
		var service = $resource('api/rest/job_postings/:id', {}, {
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
