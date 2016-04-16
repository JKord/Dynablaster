'use strict';

angular.module('dynablasterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('game_play', {
                parent: 'site',
                url: '/game/play/:type',
                data: { authorities: ['ROLE_USER'] },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/game/window.html',
                        controller: 'WindowGameController'
                    }
                }
            })
            .state('game_create', {
                parent: 'site',
                url: '/game/create',
                data: { authorities: ['ROLE_USER'] },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/game/create.html',
                        controller: 'CreateGameController'
                    }
                }
            })
            .state('game_list', {
                parent: 'site',
                url: '/game/list',
                data: { authorities: ['ROLE_USER'] },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/game/list.html',
                        controller: 'ListGameController'
                    }
                }
            })
            .state('game', {
                parent: 'site',
                url: '/game/:id',
                data: { authorities: ['ROLE_USER'] },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/game/game.html',
                        controller: 'GameController'
                    }
                }
            })
        ;
    });
