'use strict';

angular.module('staponeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('proPromotionPhoto', {
                parent: 'entity',
                url: '/proPromotionPhotos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'staponeApp.proPromotionPhoto.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/proPromotionPhoto/proPromotionPhotos.html',
                        controller: 'ProPromotionPhotoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('proPromotionPhoto');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('proPromotionPhoto.detail', {
                parent: 'entity',
                url: '/proPromotionPhoto/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'staponeApp.proPromotionPhoto.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/proPromotionPhoto/proPromotionPhoto-detail.html',
                        controller: 'ProPromotionPhotoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('proPromotionPhoto');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ProPromotionPhoto', function($stateParams, ProPromotionPhoto) {
                        return ProPromotionPhoto.get({id : $stateParams.id});
                    }]
                }
            })
            .state('proPromotionPhoto.new', {
                parent: 'proPromotionPhoto',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/proPromotionPhoto/proPromotionPhoto-dialog.html',
                        controller: 'ProPromotionPhotoDialogController',
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
                        $state.go('proPromotionPhoto', null, { reload: true });
                    }, function() {
                        $state.go('proPromotionPhoto');
                    })
                }]
            })
            .state('proPromotionPhoto.edit', {
                parent: 'proPromotionPhoto',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/proPromotionPhoto/proPromotionPhoto-dialog.html',
                        controller: 'ProPromotionPhotoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProPromotionPhoto', function(ProPromotionPhoto) {
                                return ProPromotionPhoto.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('proPromotionPhoto', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('proPromotionPhoto.delete', {
                parent: 'proPromotionPhoto',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/proPromotionPhoto/proPromotionPhoto-delete-dialog.html',
                        controller: 'ProPromotionPhotoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProPromotionPhoto', function(ProPromotionPhoto) {
                                return ProPromotionPhoto.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('proPromotionPhoto', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
