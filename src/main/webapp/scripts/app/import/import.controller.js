'use strict';

angular.module('dynablasterApp')
    .controller('ImportController', function ($scope, SNService) {
        SNService.findGroups().then(function (data) {
            $scope.groups = data;
        });
    });
