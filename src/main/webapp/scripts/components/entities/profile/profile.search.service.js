'use strict';

angular.module('staponeApp')
    .factory('ProfileSearch', function ($resource) {
        return $resource('api/_search/profiles/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
