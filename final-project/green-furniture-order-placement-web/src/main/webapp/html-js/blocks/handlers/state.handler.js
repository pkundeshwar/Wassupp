(function() {
	'use strict';
	angular.module('greenFurniture').factory('stateHandler', stateHandler);
	stateHandler.$inject = [ '$rootScope', '$state', '$sessionStorage', '$window', 'Auth', 'Principal'];

	function stateHandler($rootScope, $state, $sessionStorage, $window, Auth, Principal) {
		return {
			initialize : initialize
		};

		function initialize() {
			var stateChangeStart = $rootScope.$on('$stateChangeStart',
					function(event, toState, toStateParams, fromState) {
						$rootScope.toState = toState;
						$rootScope.toStateParams = toStateParams;
						$rootScope.fromState = fromState;

						if (toState.external) {
							event.preventDefault();
							$window.open(toState.url, '_self');
						}

						if (Principal.isIdentityResolved()) {
							Auth.authorize();
						}

					});

			var stateChangeSuccess = $rootScope.$on('$stateChangeSuccess',
					function(event, toState, toParams, fromState, fromParams) {
						var titleKey = 'greenFurniture';

						if (toState.data.pageTitle) {
							titleKey = toState.data.pageTitle;
						}
						$window.document.title = titleKey;
					});

			$rootScope.$on('$destroy', function() {
				if (angular.isDefined(stateChangeStart)
						&& stateChangeStart !== null) {
					stateChangeStart();
				}
				if (angular.isDefined(stateChangeSuccess)
						&& stateChangeSuccess !== null) {
					stateChangeSuccess();
				}
			});
		}
	}
})();
