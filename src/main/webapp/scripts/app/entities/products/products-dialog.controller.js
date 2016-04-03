'use strict';

angular.module('staponeApp').controller('ProductsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Products', 'Category',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, Products, Category) {

        $scope.products = entity;
        $scope.categorys = Category.query();
        $scope.load = function(id) {
            Products.get({id : id}, function(result) {
                $scope.products = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('staponeApp:productsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.products.id != null) {
                Products.update($scope.products, onSaveSuccess, onSaveError);
            } else {
                Products.save($scope.products, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setLogo = function ($file, products) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        products.logo = base64Data;
                        products.logoContentType = $file.type;
                    });
                };
            }
        };
        $scope.datePickerForCreatedAt = {};

        $scope.datePickerForCreatedAt.status = {
            opened: false
        };

        $scope.datePickerForCreatedAtOpen = function($event) {
            $scope.datePickerForCreatedAt.status.opened = true;
        };
        $scope.datePickerForUpdatedAt = {};

        $scope.datePickerForUpdatedAt.status = {
            opened: false
        };

        $scope.datePickerForUpdatedAtOpen = function($event) {
            $scope.datePickerForUpdatedAt.status.opened = true;
        };
}]);
