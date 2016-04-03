'use strict';

angular.module('staponeApp')
	.controller('CatPromotionPhotoDeleteController', function($scope, $uibModalInstance, entity, CatPromotionPhoto) {

        $scope.catPromotionPhoto = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CatPromotionPhoto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
