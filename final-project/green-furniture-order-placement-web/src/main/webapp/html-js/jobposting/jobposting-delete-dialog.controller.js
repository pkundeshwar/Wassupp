(function() {
    'use strict';

    angular.module('greenFurniture').controller('JobPostingDeleteController', JobPostingDeleteController);
    JobPostingDeleteController.$inject = ['$uibModalInstance', 'entity', 'JobPosting'];

    function JobPostingDeleteController ($uibModalInstance, entity, JobPosting) {
        var vm = this;

        vm.posting = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            JobPosting.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
