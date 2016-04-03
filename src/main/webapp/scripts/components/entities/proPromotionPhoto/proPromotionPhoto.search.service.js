'use strict';

angular.module('staponeApp')
    .factory('ProPromotionPhotoSearch', function ($resource) {
        return $resource('api/_search/proPromotionPhotos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
