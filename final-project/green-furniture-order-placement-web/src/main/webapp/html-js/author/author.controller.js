(function() {
    'use strict';

    angular
        .module('greenFurniture')
        .controller('AuthorController', AuthorController);

    AuthorController.$inject = ['ParseLinks', 'AlertService', '$state', 'Author'];

    function AuthorController(ParseLinks, AlertService, $state, Author ) {
        var vm = this;

        vm.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        vm.loadAll = loadAll;
        vm.authors = [];
        vm.clear = clear;
        vm.links = null;

        vm.loadAll();
        
        function loadAll () {
			console.log('test');
            Author.query({},onSuccess, onError);
        }

        function onSuccess(data, headers) {
			console.log('test');
			console.log(data);
            vm.authors = data;
        }

        function onError(error) {
			console.log('test');
            AlertService.error(error.data.message);
        }

        function clear () {
            vm.author = {
                id: null, firstName: null, lastName: null, nationality: null
            };
        }
    }
})();
