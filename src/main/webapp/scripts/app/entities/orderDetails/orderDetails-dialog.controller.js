'use strict';

angular.module('staponeApp').controller('OrderDetailsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'OrderDetails', 'Orders', 'Products',
        function($scope, $stateParams, $uibModalInstance, entity, OrderDetails, Orders, Products) {

        $scope.orderDetails = entity;
        $scope.orderss = Orders.query();
        $scope.productss = Products.query();
        $scope.load = function(id) {
            OrderDetails.get({id : id}, function(result) {
                $scope.orderDetails = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('staponeApp:orderDetailsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.orderDetails.id != null) {
                OrderDetails.update($scope.orderDetails, onSaveSuccess, onSaveError);
            } else {
                OrderDetails.save($scope.orderDetails, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
