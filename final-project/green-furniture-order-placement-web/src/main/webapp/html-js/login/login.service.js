(function() {
	'use strict';

	angular.module('greenFurniture').factory('LoginService', LoginService);
	LoginService.$inject = [ '$uibModal' ];

	function LoginService($uibModal) {
		var service = {
			open : open
		};

		var modalInstance = null;
		var resetModal = function() {
			modalInstance = null;
		};

		return service;

		function open() {
			if (modalInstance !== null)
				return;
			modalInstance = $uibModal.open({
				animation : true,
				templateUrl : 'html-js/login/login.html',
				controller : 'LoginController',
				controllerAs : 'vm'
			});
			modalInstance.result.then(resetModal, resetModal);
		}
	}
})();
