(function() {
	'use strict';

	angular.module('greenFurniture').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('account', {
			abstract : true,
			parent : 'app'
		});
	}
})();
