'use strict';

angular.module('staponeApp')
	.controller('ProfileDeleteController', function($scope, $uibModalInstance, entity, Profile) {

        $scope.profile = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Profile.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
