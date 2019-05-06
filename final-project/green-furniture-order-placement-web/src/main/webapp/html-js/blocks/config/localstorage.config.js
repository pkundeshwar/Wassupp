(function() {
	'use strict';
	angular.module('greenFurniture').config(localStorageConfig);
	localStorageConfig.$inject = [ '$localStorageProvider', '$sessionStorageProvider' ];
	function localStorageConfig($localStorageProvider, $sessionStorageProvider) {
		$localStorageProvider.setKeyPrefix('gfur-');
		$sessionStorageProvider.setKeyPrefix('gfur-');
	}
})();
