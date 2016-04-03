'use strict';

angular.module('staponeApp')
    .factory('OrderDetails', function ($resource, DateUtils) {
        return $resource('api/orderDetailss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
