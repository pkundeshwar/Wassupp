(function() {
	'use strict';

	angular.module('greenFurniture',
			[ 
			  'ngStorage', 
			  'ngResource', 
			  'ngCookies', 
			  'ngAria',
			  'ngCacheBuster',
			  'ngFileUpload',
			  'ui.bootstrap',
			  'ui.bootstrap.datetimepicker', 
			  'ui.router',
			  'infinite-scroll', 
			  'angular-loading-bar'
			 ]).run(run);

	run.$inject = [ 'stateHandler' ];

	function run(stateHandler) {
		stateHandler.initialize();
	}
})();
