'use strict';

angular.module('dynablasterApp')
    .controller('GameController', function ($scope, $stateParams, $location, gameService) {
        $scope.typeGame = $stateParams.type;
        $scope.endGame = function() {
            gameService.endGame().then(function(data) {
                $location.path('/')
            });
        };
    });
