'use strict';

angular.module('staponeApp')
    .factory('UserReviewsSearch', function ($resource) {
        return $resource('api/_search/userReviewss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
