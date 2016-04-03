'use strict';

angular.module('staponeApp')
    .controller('DeptPromotionPhotoDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, DeptPromotionPhoto, Department) {
        $scope.deptPromotionPhoto = entity;
        $scope.load = function (id) {
            DeptPromotionPhoto.get({id: id}, function(result) {
                $scope.deptPromotionPhoto = result;
            });
        };
        var unsubscribe = $rootScope.$on('staponeApp:deptPromotionPhotoUpdate', function(event, result) {
            $scope.deptPromotionPhoto = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
