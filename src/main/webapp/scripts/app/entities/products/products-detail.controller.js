'use strict';

angular.module('staponeApp')
    .controller('ProductsDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, Products, Category) {
        $scope.products = entity;
        $scope.load = function (id) {
            Products.get({id: id}, function(result) {
                $scope.products = result;
            });
        };
        var unsubscribe = $rootScope.$on('staponeApp:productsUpdate', function(event, result) {
            $scope.products = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
