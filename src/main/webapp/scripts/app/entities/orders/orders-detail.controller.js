'use strict';

angular.module('staponeApp')
    .controller('OrdersDetailController', function ($scope, $rootScope, $stateParams, entity, Orders, User) {
        $scope.orders = entity;
        $scope.load = function (id) {
            Orders.get({id: id}, function(result) {
                $scope.orders = result;
            });
        };
        var unsubscribe = $rootScope.$on('staponeApp:ordersUpdate', function(event, result) {
            $scope.orders = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
