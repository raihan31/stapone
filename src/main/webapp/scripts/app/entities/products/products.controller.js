'use strict';

angular.module('staponeApp')
    .controller('ProductsController', function ($scope, $state, DataUtils, Products, ProductsSearch, ParseLinks) {

        $scope.productss = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Products.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.productss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ProductsSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.productss = result;
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
            $scope.products = {
                name: null,
                logo: null,
                logoContentType: null,
                descriptions: null,
                measuredUnit: null,
                unitItem: null,
                price: null,
                availableItem: null,
                soldItem: null,
                status: null,
                sharedNo: null,
                createdAt: null,
                updatedAt: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    });
