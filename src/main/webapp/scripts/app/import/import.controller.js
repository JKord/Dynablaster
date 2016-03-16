'use strict';

angular.module('pssnApp')
    .controller('ImportController', function ($scope, SNService) {
        SNService.findGroups().then(function (data) {
            $scope.groups = data;
        });
    });
