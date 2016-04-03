'use strict';

angular.module('staponeApp')
    .controller('ProPromotionPhotoController', function ($scope, $state, DataUtils, ProPromotionPhoto, ProPromotionPhotoSearch, ParseLinks) {

        $scope.proPromotionPhotos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            ProPromotionPhoto.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.proPromotionPhotos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ProPromotionPhotoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.proPromotionPhotos = result;
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
            $scope.proPromotionPhoto = {
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
