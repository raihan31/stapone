'use strict';

angular.module('staponeApp')
	.controller('OrderDetailsDeleteController', function($scope, $uibModalInstance, entity, OrderDetails) {

        $scope.orderDetails = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            OrderDetails.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
