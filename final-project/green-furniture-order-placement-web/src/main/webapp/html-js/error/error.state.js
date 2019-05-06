(function() {
	'use strict';
	angular.module('greenFurniture').config(stateConfig);
	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('error', {
			parent : 'app',
			url : '/error',
			data : {
				authorities : [],
				pageTitle : 'Error page!'
			},
			views : {
				'content@' : {
					templateUrl : 'html-js/error/error.html'
				}
			}
		}).state('accessdenied', {
			parent : 'app',
			url : '/accessdenied',
			data : {
				authorities : []
			},
			views : {
				'content@' : {
					templateUrl : 'html-js/error/accessdenied.html'
				}
			}
		});
	}
})();
