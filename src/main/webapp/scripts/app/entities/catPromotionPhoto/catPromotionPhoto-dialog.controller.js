'use strict';

angular.module('staponeApp').controller('CatPromotionPhotoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'CatPromotionPhoto', 'Category',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, CatPromotionPhoto, Category) {

        $scope.catPromotionPhoto = entity;
        $scope.categorys = Category.query();
        $scope.load = function(id) {
            CatPromotionPhoto.get({id : id}, function(result) {
                $scope.catPromotionPhoto = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('staponeApp:catPromotionPhotoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.catPromotionPhoto.id != null) {
                CatPromotionPhoto.update($scope.catPromotionPhoto, onSaveSuccess, onSaveError);
            } else {
                CatPromotionPhoto.save($scope.catPromotionPhoto, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setPhoto = function ($file, catPromotionPhoto) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        catPromotionPhoto.photo = base64Data;
                        catPromotionPhoto.photoContentType = $file.type;
                    });
                };
            }
        };
}]);
