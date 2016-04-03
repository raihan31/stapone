'use strict';

angular.module('staponeApp')
	.controller('UserReviewsDeleteController', function($scope, $uibModalInstance, entity, UserReviews) {

        $scope.userReviews = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            UserReviews.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
