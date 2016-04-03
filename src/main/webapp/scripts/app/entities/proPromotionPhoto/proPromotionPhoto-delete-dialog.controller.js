'use strict';

angular.module('staponeApp')
	.controller('ProPromotionPhotoDeleteController', function($scope, $uibModalInstance, entity, ProPromotionPhoto) {

        $scope.proPromotionPhoto = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProPromotionPhoto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
