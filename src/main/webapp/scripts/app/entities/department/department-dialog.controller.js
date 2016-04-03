'use strict';

angular.module('staponeApp').controller('DepartmentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Department',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, Department) {

        $scope.department = entity;
        $scope.load = function(id) {
            Department.get({id : id}, function(result) {
                $scope.department = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('staponeApp:departmentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.department.id != null) {
                Department.update($scope.department, onSaveSuccess, onSaveError);
            } else {
                Department.save($scope.department, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setLogo = function ($file, department) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        department.logo = base64Data;
                        department.logoContentType = $file.type;
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
