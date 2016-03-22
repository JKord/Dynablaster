 'use strict';

angular.module('dynablasterApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-dynablasterApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-dynablasterApp-params')});
                }
                return response;
            }
        };
    });
