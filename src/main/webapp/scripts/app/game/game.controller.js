'use strict';

angular.module('dynablasterApp')
    .controller('WindowGameController', function ($scope, $stateParams, $location, gameService) {
        $scope.typeGame = $stateParams.type;
        $scope.endGame = function() {
            gameService.endGame().then(function(data) {
                $location.path('/')
            });
        };
    })
    .controller('CreateGameController', function ($scope, $location, $stateParams, gameService) {
        $scope.lobbyForm = { name: null };
        $scope.create = function() {
            $scope.lobbyForm = { name: null };
            gameService.createLobby($scope.lobby).then(function (response) {
                console.log(response);
                if (response.status != 400) {
                    $location.path('/game/' + response.id);
                }
            }).catch(function (response) {
                if (response.data.fieldErrors) {
                    response.data.fieldErrors.forEach(function(error) {
                        $scope.lobbyForm[error.field] = { msg: error.message };
                    });
                } else {
                    alert(response.data.message);
                }
            });
        }
    })
    .controller('GameController', function ($scope, $stateParams) {
        $scope.game = {
            name: 'Game' + $stateParams.id,
            users: [
                {name: 'user 1'},
                {name: 'user 2'},
                {name: 'user 3'},
                {name: 'user 4'},
                {name: 'user 5'}
            ],
            isOwner: true
        };

        $scope.startGame = function() {
            window.location = '#/game/play/single';
        }
    })
    .controller('ListGameController', function ($scope) {
        $scope.games = [
            {id: 1, name: 'Game 1'},
            {id: 2, name: 'Game 2'},
            {id: 3, name: 'Game 3'},
            {id: 4, name: 'Game 4'},
            {id: 5, name: 'Game 5'},
            {id: 6, name: 'Game 6'}
        ];
    })
;
