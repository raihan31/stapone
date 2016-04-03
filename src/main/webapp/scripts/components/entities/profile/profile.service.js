'use strict';

angular.module('staponeApp')
    .factory('Profile', function ($resource, DateUtils) {
        return $resource('api/profiles/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createdAt = DateUtils.convertDateTimeFromServer(data.createdAt);
                    data.UpdatedAt = DateUtils.convertDateTimeFromServer(data.UpdatedAt);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
