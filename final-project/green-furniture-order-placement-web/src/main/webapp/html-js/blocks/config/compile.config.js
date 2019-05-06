(function() {
	'use strict';
	angular.module('greenFurniture').config(compileServiceConfig);
	compileServiceConfig.$inject = [ '$compileProvider'];

	function compileServiceConfig($compileProvider) {
		// disable debug data on prod profile to improve performance
		//set it to true for dev environment
		$compileProvider.debugInfoEnabled(false);
	}
})();
