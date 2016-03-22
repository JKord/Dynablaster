'use strict';

angular.module('dynablasterApp')
    .controller('GameController', function ($scope, GameService) {
        GameService.getMap().then(function (data) {
            $scope.map = data;
        });
    });
