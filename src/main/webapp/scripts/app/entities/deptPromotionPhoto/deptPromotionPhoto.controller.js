'use strict';

angular.module('staponeApp')
    .controller('DeptPromotionPhotoController', function ($scope, $state, DataUtils, DeptPromotionPhoto, DeptPromotionPhotoSearch, ParseLinks) {

        $scope.deptPromotionPhotos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            DeptPromotionPhoto.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.deptPromotionPhotos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DeptPromotionPhotoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.deptPromotionPhotos = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.deptPromotionPhoto = {
                name: null,
                photo: null,
                photoContentType: null,
                descriptions: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    });
