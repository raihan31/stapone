'use strict';

angular.module('staponeApp')
	.controller('OrdersDeleteController', function($scope, $uibModalInstance, entity, Orders) {

        $scope.orders = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Orders.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
