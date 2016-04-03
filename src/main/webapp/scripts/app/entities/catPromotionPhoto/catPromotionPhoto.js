'use strict';

angular.module('staponeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('catPromotionPhoto', {
                parent: 'entity',
                url: '/catPromotionPhotos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'staponeApp.catPromotionPhoto.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/catPromotionPhoto/catPromotionPhotos.html',
                        controller: 'CatPromotionPhotoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('catPromotionPhoto');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('catPromotionPhoto.detail', {
                parent: 'entity',
                url: '/catPromotionPhoto/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'staponeApp.catPromotionPhoto.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/catPromotionPhoto/catPromotionPhoto-detail.html',
                        controller: 'CatPromotionPhotoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('catPromotionPhoto');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CatPromotionPhoto', function($stateParams, CatPromotionPhoto) {
                        return CatPromotionPhoto.get({id : $stateParams.id});
                    }]
                }
            })
            .state('catPromotionPhoto.new', {
                parent: 'catPromotionPhoto',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/catPromotionPhoto/catPromotionPhoto-dialog.html',
                        controller: 'CatPromotionPhotoDialogController',
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
                        $state.go('catPromotionPhoto', null, { reload: true });
                    }, function() {
                        $state.go('catPromotionPhoto');
                    })
                }]
            })
            .state('catPromotionPhoto.edit', {
                parent: 'catPromotionPhoto',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/catPromotionPhoto/catPromotionPhoto-dialog.html',
                        controller: 'CatPromotionPhotoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CatPromotionPhoto', function(CatPromotionPhoto) {
                                return CatPromotionPhoto.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('catPromotionPhoto', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('catPromotionPhoto.delete', {
                parent: 'catPromotionPhoto',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/catPromotionPhoto/catPromotionPhoto-delete-dialog.html',
                        controller: 'CatPromotionPhotoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CatPromotionPhoto', function(CatPromotionPhoto) {
                                return CatPromotionPhoto.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('catPromotionPhoto', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
