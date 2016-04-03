'use strict';

angular.module('staponeApp')
    .controller('OrderDetailsDetailController', function ($scope, $rootScope, $stateParams, entity, OrderDetails, Orders, Products) {
        $scope.orderDetails = entity;
        $scope.load = function (id) {
            OrderDetails.get({id: id}, function(result) {
                $scope.orderDetails = result;
            });
        };
        var unsubscribe = $rootScope.$on('staponeApp:orderDetailsUpdate', function(event, result) {
            $scope.orderDetails = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
