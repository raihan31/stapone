'use strict';

angular.module('staponeApp')
    .controller('OrdersController', function ($scope, $state, Orders, OrdersSearch, ParseLinks) {

        $scope.orderss = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Orders.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.orderss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            OrdersSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.orderss = result;
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
            $scope.orders = {
                descriptions: null,
                totalPrice: null,
                totalProducts: null,
                isViewed: null,
                isDelivered: null,
                isReceived: null,
                totalMoneyReceived: null,
                isPaid: null,
                receivedDocument: null,
                isCanceled: null,
                isTaken: null,
                createdAt: null,
                updatedAt: null,
                id: null
            };
        };
    });
