'use strict';

angular.module('staponeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('orderDetails', {
                parent: 'entity',
                url: '/orderDetailss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'staponeApp.orderDetails.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderDetails/orderDetailss.html',
                        controller: 'OrderDetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderDetails');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('orderDetails.detail', {
                parent: 'entity',
                url: '/orderDetails/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'staponeApp.orderDetails.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderDetails/orderDetails-detail.html',
                        controller: 'OrderDetailsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderDetails');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'OrderDetails', function($stateParams, OrderDetails) {
                        return OrderDetails.get({id : $stateParams.id});
                    }]
                }
            })
            .state('orderDetails.new', {
                parent: 'orderDetails',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/orderDetails/orderDetails-dialog.html',
                        controller: 'OrderDetailsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    noOfProducts: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('orderDetails', null, { reload: true });
                    }, function() {
                        $state.go('orderDetails');
                    })
                }]
            })
            .state('orderDetails.edit', {
                parent: 'orderDetails',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/orderDetails/orderDetails-dialog.html',
                        controller: 'OrderDetailsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['OrderDetails', function(OrderDetails) {
                                return OrderDetails.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('orderDetails', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('orderDetails.delete', {
                parent: 'orderDetails',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/orderDetails/orderDetails-delete-dialog.html',
                        controller: 'OrderDetailsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['OrderDetails', function(OrderDetails) {
                                return OrderDetails.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('orderDetails', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
