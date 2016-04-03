'use strict';

angular.module('staponeApp')
    .controller('ProPromotionPhotoDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, ProPromotionPhoto, Products) {
        $scope.proPromotionPhoto = entity;
        $scope.load = function (id) {
            ProPromotionPhoto.get({id: id}, function(result) {
                $scope.proPromotionPhoto = result;
            });
        };
        var unsubscribe = $rootScope.$on('staponeApp:proPromotionPhotoUpdate', function(event, result) {
            $scope.proPromotionPhoto = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
