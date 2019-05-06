(function() {
	'use strict';

	angular.module('greenFurniture').controller('AuthorDetailController', AuthorDetailController);
	AuthorDetailController.$inject = [ '$stateParams', 'Author' ];

	function AuthorDetailController($stateParams, Author) {
		var vm = this;

		vm.load = load;
		vm.author = {};

		vm.load($stateParams.id);

		function load(id) {
			Author.get({id : id}, function(result) {
				vm.author = result;
			});
		}
	}
})();
