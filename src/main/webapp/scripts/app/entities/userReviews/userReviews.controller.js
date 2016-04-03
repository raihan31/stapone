'use strict';

angular.module('staponeApp')
    .controller('UserReviewsController', function ($scope, $state, UserReviews, UserReviewsSearch, ParseLinks) {

        $scope.userReviewss = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            UserReviews.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.userReviewss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            UserReviewsSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.userReviewss = result;
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
            $scope.userReviews = {
                rating: null,
                comments: null,
                isFavourite: null,
                createdAt: null,
                updatedAt: null,
                id: null
            };
        };
    });
