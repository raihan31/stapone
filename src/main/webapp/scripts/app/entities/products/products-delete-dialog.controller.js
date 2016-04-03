'use strict';

angular.module('staponeApp')
	.controller('ProductsDeleteController', function($scope, $uibModalInstance, entity, Products) {

        $scope.products = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Products.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
