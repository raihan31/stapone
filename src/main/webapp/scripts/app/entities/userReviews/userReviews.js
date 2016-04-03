'use strict';

angular.module('staponeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userReviews', {
                parent: 'entity',
                url: '/userReviewss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'staponeApp.userReviews.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userReviews/userReviewss.html',
                        controller: 'UserReviewsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userReviews');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('userReviews.detail', {
                parent: 'entity',
                url: '/userReviews/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'staponeApp.userReviews.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userReviews/userReviews-detail.html',
                        controller: 'UserReviewsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userReviews');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UserReviews', function($stateParams, UserReviews) {
                        return UserReviews.get({id : $stateParams.id});
                    }]
                }
            })
            .state('userReviews.new', {
                parent: 'userReviews',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/userReviews/userReviews-dialog.html',
                        controller: 'UserReviewsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    rating: null,
                                    comments: null,
                                    isFavourite: null,
                                    createdAt: null,
                                    updatedAt: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('userReviews', null, { reload: true });
                    }, function() {
                        $state.go('userReviews');
                    })
                }]
            })
            .state('userReviews.edit', {
                parent: 'userReviews',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/userReviews/userReviews-dialog.html',
                        controller: 'UserReviewsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UserReviews', function(UserReviews) {
                                return UserReviews.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('userReviews', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('userReviews.delete', {
                parent: 'userReviews',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/userReviews/userReviews-delete-dialog.html',
                        controller: 'UserReviewsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['UserReviews', function(UserReviews) {
                                return UserReviews.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('userReviews', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
