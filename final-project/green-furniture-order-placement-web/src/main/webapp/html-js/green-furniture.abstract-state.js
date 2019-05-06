(function() {
	'use strict';
	angular.module('greenFurniture').config(stateConfig);
	stateConfig.$inject = [ '$stateProvider' ];
	
	function stateConfig($stateProvider) {
		$stateProvider.state('app', {
			abstract : true,
			views : {
				'navbar@' : {
					templateUrl : 'html-js/navbar/navbar.html',
					controller : 'NavbarController',
					controllerAs : 'vm'
				}
			},
			resolve : {
				authorize : [ 'Auth', function(Auth) {
					return Auth.authorize();
				} ]
			}
		});
	}
})();
