'use strict';

angular.module('staponeApp').controller('ProfileDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'Profile', 'User',
        function($scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, Profile, User) {

        $scope.profile = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Profile.get({id : id}, function(result) {
                $scope.profile = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('staponeApp:profileUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.profile.id != null) {
                Profile.update($scope.profile, onSaveSuccess, onSaveError);
            } else {
                Profile.save($scope.profile, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setPhoto = function ($file, profile) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        profile.photo = base64Data;
                        profile.photoContentType = $file.type;
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
