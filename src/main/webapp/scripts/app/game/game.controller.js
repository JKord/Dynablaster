'use strict';

angular.module('dynablasterApp')
    .controller('GameController', function ($scope, $stateParams) {
        $scope.typeGame = $stateParams.type;
    });
