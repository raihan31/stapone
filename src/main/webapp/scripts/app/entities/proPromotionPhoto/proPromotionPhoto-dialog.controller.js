'use strict';

angular.module('staponeApp').controller('ProPromotionPhotoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'ProPromotionPhoto', 'Products',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, ProPromotionPhoto, Products) {

        $scope.proPromotionPhoto = entity;
        $scope.productss = Products.query();
        $scope.load = function(id) {
            ProPromotionPhoto.get({id : id}, function(result) {
                $scope.proPromotionPhoto = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('staponeApp:proPromotionPhotoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.proPromotionPhoto.id != null) {
                ProPromotionPhoto.update($scope.proPromotionPhoto, onSaveSuccess, onSaveError);
            } else {
                ProPromotionPhoto.save($scope.proPromotionPhoto, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setPhoto = function ($file, proPromotionPhoto) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        proPromotionPhoto.photo = base64Data;
                        proPromotionPhoto.photoContentType = $file.type;
                    });
                };
            }
        };
}]);
