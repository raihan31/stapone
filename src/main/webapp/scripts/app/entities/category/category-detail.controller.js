'use strict';

angular.module('staponeApp')
    .controller('CategoryDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, Category, Department) {
        $scope.category = entity;
        $scope.load = function (id) {
            Category.get({id: id}, function(result) {
                $scope.category = result;
            });
        };
        var unsubscribe = $rootScope.$on('staponeApp:categoryUpdate', function(event, result) {
            $scope.category = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
