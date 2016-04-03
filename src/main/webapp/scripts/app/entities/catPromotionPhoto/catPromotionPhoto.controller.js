'use strict';

angular.module('staponeApp')
    .controller('CatPromotionPhotoController', function ($scope, $state, DataUtils, CatPromotionPhoto, CatPromotionPhotoSearch, ParseLinks) {

        $scope.catPromotionPhotos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            CatPromotionPhoto.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.catPromotionPhotos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CatPromotionPhotoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.catPromotionPhotos = result;
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
            $scope.catPromotionPhoto = {
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
