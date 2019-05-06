(function() {
    'use strict';

    angular
        .module('greenFurniture')
        .controller('AuthorDialogController',AuthorDialogController);

    AuthorDialogController.$inject = ['$stateParams', '$uibModalInstance', 'entity', 'Author'];

    function AuthorDialogController ($stateParams, $uibModalInstance, entity, Author) {
        var vm = this;

        vm.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.author = entity;

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
            if (vm.author.id !== null) {
                Author.update(vm.author, onSaveSuccess, onSaveError);
            } else {
                Author.save(vm.author, onSaveSuccess, onSaveError);
            }
        }
    }
})();
