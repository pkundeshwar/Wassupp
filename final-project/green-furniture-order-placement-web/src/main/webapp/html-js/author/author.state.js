(function() {
	'use strict';

	angular.module('greenFurniture').config(stateConfig);
	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider
				.state('author', {
					parent : 'entity',
					url : '/authors',
					data : {
						authorities : [ 'ROLE_USER', 'ROLE_ADMIN' ],
						pageTitle : 'Authors'
					},
					views : {
						'content@' : {
							templateUrl : 'html-js/author/author.html',
							controller : 'AuthorController',
							controllerAs : 'vm'
						}
					},
					params : {},
					resolve : {}
				})

				.state('author.new',{
					url : '/new',
					data : {
						authorities : [ 'ROLE_USER', 'ROLE_ADMIN' ]
					},
					onEnter : ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) { 
						$uibModal.open({
							templateUrl : 'html-js/author/author-dialog.html',
							controller : 'AuthorDialogController',
							controllerAs : 'vm',
							backdrop : 'static',
							size : 'lg',
							resolve : {
								entity : function() {
									return {
										id : null,
										firstName : null,
										lastName : null,
										nationality : null
									};
								}
							}
						}).result.then(function() {
									$state.go('author', null, {
										reload : true
									});
								}, function() {
									$state.go('author');
								});
							} ]
				})
				.state('author.edit', {
					url : '/{id}/edit',
					data : {
						authorities : [ 'ROLE_USER', 'ROLE_ADMIN' ]
					},
					onEnter : ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
						$uibModal.open({
							templateUrl : 'html-js/author/author-dialog.html',
							controller : 'AuthorDialogController',
							controllerAs : 'vm',
							backdrop : 'static',
							size : 'lg',
							resolve : {
								entity : ['Author', function(Author) {
											return Author.get({id : $stateParams.id});
										}]
							}
						}).result.then(function() {
								$state.go('author', null, {
									reload : true
								});
							}, function() {
								$state.go('^');
							});
							} ]
				})
			.state('author-detail', {
				parent : 'author',
				url : '/{id}',
				data : {
					authorities : [ 'ROLE_USER', 'ROLE_ADMIN' ],
					pageTitle : 'Author Detail'
				},
				views : {
					'content@' : {
						templateUrl : 'html-js/author/author-detail.html',
						controller : 'AuthorDetailController',
						controllerAs : 'vm'
					}
				}
		})
		.state('author.delete', {
			url : '/{id}/delete',
			data : {
				authorities : [ 'ROLE_USER', 'ROLE_ADMIN' ]
			},
			onEnter : [
					'$stateParams',
					'$state',
					'$uibModal',
					function($stateParams, $state, $uibModal) {
						$uibModal.open({
							templateUrl : 'html-js/author/author-delete-dialog.html',
							controller : 'AuthorDeleteController',
							controllerAs : 'vm',
							size : 'md',
							resolve : {
								entity : ['Author', function(Author) {
											return Author.get({id : $stateParams.id});
										} ]
							}
						}).result.then(function() {
							$state.go('author', null, {
								reload : true
							});
						}, function() {
							$state.go('^');
						});
					} ]
		});
	}
})();
