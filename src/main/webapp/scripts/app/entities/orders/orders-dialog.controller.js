'use strict';

angular.module('staponeApp').controller('OrdersDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Orders', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Orders, User) {

        $scope.orders = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Orders.get({id : id}, function(result) {
                $scope.orders = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('staponeApp:ordersUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.orders.id != null) {
                Orders.update($scope.orders, onSaveSuccess, onSaveError);
            } else {
                Orders.save($scope.orders, onSaveSuccess, onSaveError);
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
