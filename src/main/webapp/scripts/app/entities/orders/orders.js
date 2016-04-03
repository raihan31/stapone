'use strict';

angular.module('staponeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('orders', {
                parent: 'entity',
                url: '/orderss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'staponeApp.orders.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orders/orderss.html',
                        controller: 'OrdersController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orders');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('orders.detail', {
                parent: 'entity',
                url: '/orders/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'staponeApp.orders.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orders/orders-detail.html',
                        controller: 'OrdersDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orders');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Orders', function($stateParams, Orders) {
                        return Orders.get({id : $stateParams.id});
                    }]
                }
            })
            .state('orders.new', {
                parent: 'orders',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/orders/orders-dialog.html',
                        controller: 'OrdersDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    descriptions: null,
                                    totalPrice: null,
                                    totalProducts: null,
                                    isViewed: null,
                                    isDelivered: null,
                                    isReceived: null,
                                    totalMoneyReceived: null,
                                    isPaid: null,
                                    receivedDocument: null,
                                    isCanceled: null,
                                    isTaken: null,
                                    createdAt: null,
                                    updatedAt: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('orders', null, { reload: true });
                    }, function() {
                        $state.go('orders');
                    })
                }]
            })
            .state('orders.edit', {
                parent: 'orders',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/orders/orders-dialog.html',
                        controller: 'OrdersDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Orders', function(Orders) {
                                return Orders.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('orders', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('orders.delete', {
                parent: 'orders',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/orders/orders-delete-dialog.html',
                        controller: 'OrdersDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Orders', function(Orders) {
                                return Orders.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('orders', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
