'use strict';

angular.module('staponeApp')
    .factory('CategorySearch', function ($resource) {
        return $resource('api/_search/categorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
