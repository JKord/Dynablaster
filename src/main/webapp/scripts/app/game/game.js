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
                        templateUrl: 'scripts/app/game/game.html',
                        controller: 'GameController'
                    }
                },
                resolve: {}
            })
            .state('game_create', {
                parent: 'site',
                url: '/game/create',
                data: { authorities: ['ROLE_USER'] },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/game/gameCreate.html',
                        controller: 'CreateGameController'
                    }
                },
                resolve: {}
            })
            .state('game_list', {
                parent: 'site',
                url: '/game/list',
                data: { authorities: ['ROLE_USER'] },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/game/gameList.html',
                        controller: 'ListGameController'
                    }
                },
                resolve: {}
            })
        ;
    });
