 'use strict';

angular.module('staponeApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-staponeApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-staponeApp-params')});
                }
                return response;
            }
        };
    });
