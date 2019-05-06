(function() {
	'use strict';

	angular.module('greenFurniture').config(stateConfig);
	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('home', {
			parent : 'app',
			url : '/',
			data : {
				authorities : []
			},
			views : {
				'content@' : {
					templateUrl : 'html-js/home/home.html',
					controller : 'HomeController',
					controllerAs : 'vm'
				}
			}
		});
	}
})();
