(function() {
    'use strict';

    angular
        .module('greenFurniture')
        .controller('JobPostingDialogController',JobPostingDialogController);

    JobPostingDialogController.$inject = ['$stateParams', '$uibModalInstance', 'entity', 'JobPosting'];

    function JobPostingDialogController ($stateParams, $uibModalInstance, entity, JobPosting) {
        var vm = this;

        vm.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.posting = entity;

        function clear () { 
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $uibModalInstance.close(result);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function save () {
            vm.isSaving = true;
            if (vm.posting.id !== null) {
                JobPosting.update(vm.posting, onSaveSuccess, onSaveError);
            } else {
                JobPosting.save(vm.posting, onSaveSuccess, onSaveError);
            }
        }
    }
})();
