'use strict';

angular.module('staponeApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


