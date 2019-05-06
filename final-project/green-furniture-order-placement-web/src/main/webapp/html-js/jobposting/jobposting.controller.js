(function() {
    'use strict';

    angular
        .module('greenFurniture')
        .controller('JobPostingController', JobPostingController);

    JobPostingController.$inject = ['ParseLinks', 'AlertService', '$state', 'JobPosting'];

    function JobPostingController(ParseLinks, AlertService, $state, JobPosting ) {
        var vm = this;

        vm.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        vm.loadAll = loadAll;
        vm.jobposting = [];
        vm.clear = clear;
        vm.links = null;

        vm.loadAll();
        
        function loadAll () {
			console.log('load all called');
            JobPosting.query({},onSuccess, onError);
        }

        function onSuccess(data, headers) {
            console.log(data);
			vm.jobpostings = data;
			
        }

        function onError(error) {
			console.log('error');
            AlertService.error(error.data.message);
        }

        function clear () {
            vm.posting = {
                id: null, firstName: null, lastName: null, nationality: null
            };
        }
    }
})();
