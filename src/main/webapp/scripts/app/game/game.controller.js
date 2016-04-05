'use strict';

angular.module('dynablasterApp')
    .controller('GameController', function ($scope, $stateParams, $location, gameService) {
        $scope.typeGame = $stateParams.type;
        $scope.endGame = function() {
            gameService.endGame().then(function(data) {
                $location.path('/')
            });
        };
    })
    .controller('CreateGameController', function ($scope, $stateParams, $location) {
    })
    .controller('ListGameController', function ($scope, $stateParams, $location) {
    })
;
