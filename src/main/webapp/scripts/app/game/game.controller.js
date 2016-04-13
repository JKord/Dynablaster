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
            gameService.lobbyCreate($scope.lobby).then(function (response) {
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
    .controller('GameController', function ($scope, $location, $stateParams, $cookies, gameService) {

        function goToList(message) {
            alert(message);
            setTimeout(function() {
                $location.path('/game/list');
            }, 100)
        }

        $scope.startGame = function () {
           window.location = '#/game/play/multi';
        };

        function updateLobby(isAddUser) {
            gameService.lobbyGetFromServer($stateParams.id).then(function (data) {
                $scope.game = data;
                gameService.lobbySet(data);

                if (isAddUser && !data.owner) {
                    gameService.lobbyAddUser(data.id).then(function () {
                        updateLobby(false);
                    }).catch(function (response) {
                        goToList(response.data.message);
                    });
                }

                console.log($scope.game.id);

                if (! data.owner) {
                    gameService.stompSubscribe('/game/start/' + $scope.game.id, function(gameInfo) {
                        console.log(gameInfo.key);
                        $cookies.put('gameKey', gameInfo.key);
                        $scope.startGame();
                    });
                }
            }).catch(function (response) {
                goToList(response.data.message);
            });
        }

        if (gameService.isLobbyGet()) {
            $scope.game = gameService.lobbyGet();
        } else {
            updateLobby(true);
        }

        $scope.switchStatus = function() {

        };

        $scope.updateLobbyUsers = function() {
            $scope.game.users = [];
            updateLobby(false);
        };
    })
    .controller('ListGameController', function ($scope, gameService) {
        gameService.socketInit();

        var lobby = gameService.lobbyGet();
        if (lobby != null) {
            if (!lobby.owner)
                gameService.lobbyRemoveUser(lobby.id);
            gameService.lobbySet(null);
        }

        gameService.lobbyList().then(function (data) {
            $scope.games = data;
        });
    })
;
