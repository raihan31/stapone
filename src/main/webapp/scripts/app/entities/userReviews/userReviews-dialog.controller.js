'use strict';

angular.module('staponeApp').controller('UserReviewsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserReviews', 'User', 'Products',
        function($scope, $stateParams, $uibModalInstance, entity, UserReviews, User, Products) {

        $scope.userReviews = entity;
        $scope.users = User.query();
        $scope.productss = Products.query();
        $scope.load = function(id) {
            UserReviews.get({id : id}, function(result) {
                $scope.userReviews = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('staponeApp:userReviewsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.userReviews.id != null) {
                UserReviews.update($scope.userReviews, onSaveSuccess, onSaveError);
            } else {
                UserReviews.save($scope.userReviews, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForCreatedAt = {};

        $scope.datePickerForCreatedAt.status = {
            opened: false
        };

        $scope.datePickerForCreatedAtOpen = function($event) {
            $scope.datePickerForCreatedAt.status.opened = true;
        };
        $scope.datePickerForUpdatedAt = {};

        $scope.datePickerForUpdatedAt.status = {
            opened: false
        };

        $scope.datePickerForUpdatedAtOpen = function($event) {
            $scope.datePickerForUpdatedAt.status.opened = true;
        };
}]);
