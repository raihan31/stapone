'use strict';

angular.module('staponeApp')
	.controller('DeptPromotionPhotoDeleteController', function($scope, $uibModalInstance, entity, DeptPromotionPhoto) {

        $scope.deptPromotionPhoto = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DeptPromotionPhoto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
