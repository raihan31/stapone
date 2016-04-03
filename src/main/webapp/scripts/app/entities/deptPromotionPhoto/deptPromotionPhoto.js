'use strict';

angular.module('staponeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('deptPromotionPhoto', {
                parent: 'entity',
                url: '/deptPromotionPhotos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'staponeApp.deptPromotionPhoto.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/deptPromotionPhoto/deptPromotionPhotos.html',
                        controller: 'DeptPromotionPhotoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('deptPromotionPhoto');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('deptPromotionPhoto.detail', {
                parent: 'entity',
                url: '/deptPromotionPhoto/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'staponeApp.deptPromotionPhoto.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/deptPromotionPhoto/deptPromotionPhoto-detail.html',
                        controller: 'DeptPromotionPhotoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('deptPromotionPhoto');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DeptPromotionPhoto', function($stateParams, DeptPromotionPhoto) {
                        return DeptPromotionPhoto.get({id : $stateParams.id});
                    }]
                }
            })
            .state('deptPromotionPhoto.new', {
                parent: 'deptPromotionPhoto',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/deptPromotionPhoto/deptPromotionPhoto-dialog.html',
                        controller: 'DeptPromotionPhotoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    photo: null,
                                    photoContentType: null,
                                    descriptions: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('deptPromotionPhoto', null, { reload: true });
                    }, function() {
                        $state.go('deptPromotionPhoto');
                    })
                }]
            })
            .state('deptPromotionPhoto.edit', {
                parent: 'deptPromotionPhoto',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/deptPromotionPhoto/deptPromotionPhoto-dialog.html',
                        controller: 'DeptPromotionPhotoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DeptPromotionPhoto', function(DeptPromotionPhoto) {
                                return DeptPromotionPhoto.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('deptPromotionPhoto', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('deptPromotionPhoto.delete', {
                parent: 'deptPromotionPhoto',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/deptPromotionPhoto/deptPromotionPhoto-delete-dialog.html',
                        controller: 'DeptPromotionPhotoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DeptPromotionPhoto', function(DeptPromotionPhoto) {
                                return DeptPromotionPhoto.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('deptPromotionPhoto', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
