'use strict';

angular.module('staponeApp')
    .controller('CatPromotionPhotoDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, CatPromotionPhoto, Category) {
        $scope.catPromotionPhoto = entity;
        $scope.load = function (id) {
            CatPromotionPhoto.get({id: id}, function(result) {
                $scope.catPromotionPhoto = result;
            });
        };
        var unsubscribe = $rootScope.$on('staponeApp:catPromotionPhotoUpdate', function(event, result) {
            $scope.catPromotionPhoto = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
