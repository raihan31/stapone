'use strict';

angular.module('staponeApp')
    .controller('UserReviewsDetailController', function ($scope, $rootScope, $stateParams, entity, UserReviews, User, Products) {
        $scope.userReviews = entity;
        $scope.load = function (id) {
            UserReviews.get({id: id}, function(result) {
                $scope.userReviews = result;
            });
        };
        var unsubscribe = $rootScope.$on('staponeApp:userReviewsUpdate', function(event, result) {
            $scope.userReviews = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
