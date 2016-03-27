'use strict';

angular.module('dynablasterApp')
    .controller('GameWindowController', function ($scope, $location, $state) {
        $scope.$state = $state;
    });
