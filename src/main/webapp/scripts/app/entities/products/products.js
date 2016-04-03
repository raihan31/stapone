'use strict';

angular.module('staponeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('products', {
                parent: 'entity',
                url: '/productss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'staponeApp.products.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/products/productss.html',
                        controller: 'ProductsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('products');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('products.detail', {
                parent: 'entity',
                url: '/products/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'staponeApp.products.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/products/products-detail.html',
                        controller: 'ProductsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('products');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Products', function($stateParams, Products) {
                        return Products.get({id : $stateParams.id});
                    }]
                }
            })
            .state('products.new', {
                parent: 'products',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/products/products-dialog.html',
                        controller: 'ProductsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    logo: null,
                                    logoContentType: null,
                                    descriptions: null,
                                    measuredUnit: null,
                                    unitItem: null,
                                    price: null,
                                    availableItem: null,
                                    soldItem: null,
                                    status: null,
                                    sharedNo: null,
                                    createdAt: null,
                                    updatedAt: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('products', null, { reload: true });
                    }, function() {
                        $state.go('products');
                    })
                }]
            })
            .state('products.edit', {
                parent: 'products',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/products/products-dialog.html',
                        controller: 'ProductsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Products', function(Products) {
                                return Products.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('products', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('products.delete', {
                parent: 'products',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/products/products-delete-dialog.html',
                        controller: 'ProductsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Products', function(Products) {
                                return Products.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('products', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
