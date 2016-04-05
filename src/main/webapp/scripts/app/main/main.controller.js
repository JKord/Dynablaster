'use strict';

angular.module('dynablasterApp')
    .controller('MainController', function ($scope, Principal, AlertService) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;

            $scope.gameMulti = function() {
                var alert = AlertService.info('dff');
                $scope.alerts = AlertService.get();
            };
        });
    });
