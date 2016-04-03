'use strict';

angular.module('staponeApp').controller('DeptPromotionPhotoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'DeptPromotionPhoto', 'Department',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, DeptPromotionPhoto, Department) {

        $scope.deptPromotionPhoto = entity;
        $scope.departments = Department.query();
        $scope.load = function(id) {
            DeptPromotionPhoto.get({id : id}, function(result) {
                $scope.deptPromotionPhoto = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('staponeApp:deptPromotionPhotoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.deptPromotionPhoto.id != null) {
                DeptPromotionPhoto.update($scope.deptPromotionPhoto, onSaveSuccess, onSaveError);
            } else {
                DeptPromotionPhoto.save($scope.deptPromotionPhoto, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setPhoto = function ($file, deptPromotionPhoto) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        deptPromotionPhoto.photo = base64Data;
                        deptPromotionPhoto.photoContentType = $file.type;
                    });
                };
            }
        };
}]);
