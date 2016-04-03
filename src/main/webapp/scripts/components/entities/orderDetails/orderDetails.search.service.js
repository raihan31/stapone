'use strict';

angular.module('staponeApp')
    .factory('OrderDetailsSearch', function ($resource) {
        return $resource('api/_search/orderDetailss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
