'use strict';

angular.module('pssnApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


