'use strict';

angular.module('dynablasterApp')
    .controller('NavbarController', function ($scope, $http, $cacheFactory, $location, $state, Auth, Principal, ENV, gameService) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };

        $http.defaults.cache = $cacheFactory('lruCache', { capacity: 1 });
        gameService.socketInit();
    });
