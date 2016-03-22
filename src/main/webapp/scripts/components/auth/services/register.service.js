'use strict';

angular.module('dynablasterApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


