'use strict';

angular.module('staponeApp')
    .factory('CatPromotionPhotoSearch', function ($resource) {
        return $resource('api/_search/catPromotionPhotos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
