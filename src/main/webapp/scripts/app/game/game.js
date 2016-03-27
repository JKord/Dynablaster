'use strict';

angular.module('dynablasterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('game', {
                parent: 'site',
                url: '/game/:type',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/game/game.html',
                        controller: 'GameController'
                    }
                },
                resolve: {

                }
            });
    });
