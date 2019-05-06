(function() {
	'use strict';

	angular.module('greenFurniture').config(stateConfig);
	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('register', {
			parent : 'account',
			url : '/register',
			data : {
				authorities : [],
				pageTitle : 'Registration'
			},
			views : {
				'content@' : {
					templateUrl : 'html-js/register/register.html',
					controller : 'RegisterController',
					controllerAs : 'vm'
				}
			}
		});
	}
})();
