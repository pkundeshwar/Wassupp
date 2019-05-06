(function() {
	'use strict';
	angular.module('greenFurniture').config(stateConfig);
	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('password', {
			parent : 'account',
			url : '/password',
			data : {
				authorities : [ 'ROLE_USER' ],
				pageTitle : 'Password'
			},
			views : {
				'content@' : {
					templateUrl : 'html-js/password/password.html',
					controller : 'PasswordController',
					controllerAs : 'vm'
				}
			}
		});
	}
})();
