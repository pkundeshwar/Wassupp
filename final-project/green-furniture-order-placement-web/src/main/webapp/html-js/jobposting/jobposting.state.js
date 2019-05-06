(function() {
	'use strict';

	angular.module('greenFurniture').config(stateConfig);
	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider
				.state('jobposting', {
					parent : 'entity',
					url : '/job_postings',
					data : {
						authorities : [ 'ROLE_USER', 'ROLE_ADMIN' ],
						pageTitle : 'Job Postings'
					},
					views : {
						'content@' : {
							templateUrl : 'html-js/jobposting/jobposting.html',
							controller : 'JobPostingController',
							controllerAs : 'vm'
						}
					},
					params : {},
					resolve : {}
				})

				.state('jobposting.new',{
					url : '/new',
					data : {
						authorities : [ 'ROLE_USER', 'ROLE_ADMIN' ]
					},
					onEnter : ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) { 
						$uibModal.open({
							templateUrl : 'html-js/jobposting/jobposting-dialog.html',
							controller : 'JobPostingDialogController',
							controllerAs : 'vm',
							backdrop : 'static',
							size : 'lg',
							resolve : {
								entity : function() {
									return {
										id : null,
										description : null,
										location : null,
										title : null,
										requirements : null
									};
								}
							}
						}).result.then(function() {
									$state.go('jobposting', null, {
										reload : true
									});
								}, function() {
									$state.go('jobposting');
								});
							} ]
				})
				.state('jobposting.edit', {
					url : '/{id}/edit',
					data : {
						authorities : [ 'ROLE_USER', 'ROLE_ADMIN' ]
					},
					onEnter : ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
						$uibModal.open({
							templateUrl : 'html-js/jobposting/jobposting-dialog.html',
							controller : 'JobPostingDialogController',
							controllerAs : 'vm',
							backdrop : 'static',
							size : 'lg',
							resolve : { 
								entity : ['JobPosting', function(JobPosting,id) { 
											console.log('we are here');
											console.log($stateParams.id);
											return JobPosting.get({id : $stateParams.id});
										}]
							}
						}).result.then(function() {
								$state.go('jobposting', null, {
									reload : true
								});
							}, function() {
								$state.go('^');
							});
							} ]
				})
			.state('jobposting-detail', {
				parent : 'jobposting',
				url : '/{id}',
				data : {
					authorities : [ 'ROLE_USER', 'ROLE_ADMIN' ],
					pageTitle : 'JobPosting Detail'
				},
				views : {
					'content@' : {
						templateUrl : 'html-js/jobposting/jobposting-detail.html',
						controller : 'JobPostingDetailController',
						controllerAs : 'vm'
					}
				}
		})
		.state('jobposting.delete', {
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
							templateUrl : 'html-js/jobposting/jobposting-delete-dialog.html',
							controller : 'JobPostingDeleteController',
							controllerAs : 'vm',
							size : 'md',
							resolve : {
								entity : ['JobPosting', function(JobPosting) {
											return JobPosting.get({id : $stateParams.id});
										} ]
							}
						}).result.then(function() {
							$state.go('jobposting', null, {
								reload : true
							});
						}, function() {
							$state.go('^');
						});
					} ]
		});
	}
})();
