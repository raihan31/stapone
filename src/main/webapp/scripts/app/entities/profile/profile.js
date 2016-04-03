'use strict';

angular.module('staponeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('profile', {
                parent: 'entity',
                url: '/profiles',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'staponeApp.profile.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/profile/profiles.html',
                        controller: 'ProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('profile');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('profile.detail', {
                parent: 'entity',
                url: '/profile/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'staponeApp.profile.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/profile/profile-detail.html',
                        controller: 'ProfileDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('profile');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Profile', function($stateParams, Profile) {
                        return Profile.get({id : $stateParams.id});
                    }]
                }
            })
            .state('profile.new', {
                parent: 'profile',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/profile/profile-dialog.html',
                        controller: 'ProfileDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    address: null,
                                    contactNo: null,
                                    location: null,
                                    photo: null,
                                    photoContentType: null,
                                    aboutMe: null,
                                    createdAt: null,
                                    UpdatedAt: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('profile', null, { reload: true });
                    }, function() {
                        $state.go('profile');
                    })
                }]
            })
            .state('profile.edit', {
                parent: 'profile',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/profile/profile-dialog.html',
                        controller: 'ProfileDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Profile', function(Profile) {
                                return Profile.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('profile', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('profile.delete', {
                parent: 'profile',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/profile/profile-delete-dialog.html',
                        controller: 'ProfileDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Profile', function(Profile) {
                                return Profile.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('profile', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
