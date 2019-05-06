(function() {
	'use strict';

	angular.module('greenFurniture').controller('JobPostingDetailController',
			JobPostingDetailController);
	JobPostingDetailController.$inject = [ '$stateParams', ' JobPosting' ];

	function JobPostingDetailController($stateParams, JobPosting) {
		var vm = this;

		vm.load = load;
		vm.posting = {};

		vm.load($stateParams.id);

		function load(id) {
			JobPosting.get({
				id : id
			}, function(result) {
				vm.posting = result;
			});
		}
	}
})();
