'use strict';

angular.module('dynablasterApp')
    .factory('GameService', function ($http) {
        return {
            getMap: function () {
                return $http.get('api/game/map').then(function (response) {
                    return response.data;
                });
            }
        }
    });
