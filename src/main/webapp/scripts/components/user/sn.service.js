'use strict';

angular.module('dynablasterApp')
    .factory('SNService', function ($http) {
        return {
            findGroups: function () {
                return $http.get('api/social/groups').then(function (response) {
                    return response.data;
                });
            }
        }
    });
