'use strict';

angular.module('dynablasterApp')
    .controller('WindowGameController', function ($scope, $stateParams, $location, gameService) {
        $scope.typeGame = $stateParams.type;
        $scope.endGame = function() {
            gameService.endGame().then(function(data) {
                $location.path('/')
            }).catch(function (response) {
                $location.path('/')
            });
        };
    })
    .controller('CreateGameController', function ($scope, $location, $stateParams, gameService) {
        $scope.lobbyForm = { name: null };
        $scope.create = function() {
            $scope.lobbyForm = { name: null };
            gameService.lobbyCreate($scope.lobby).then(function (response) {
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

        function updateLobby(isFirst) {
            gameService.lobbyGetFromServer($stateParams.id).then(function (data) {
                $scope.game = data;
                gameService.lobbySet(data);

                if (isFirst) {
                    if (!data.owner) {
                        gameService.lobbyAddUser(data.id).then(function () {
                            updateLobby(false);
                        }).catch(function (response) {
                            goToList(response.data.message);
                        });

                        gameService.stompSubscribe('/game/start/' + $scope.game.id, function(gameInfo) {
                            $cookies.put('gameKey', gameInfo.key);
                            setTimeout(function() {
                                $scope.startGame();
                            }, 150);
                        });
                    }

                    var urlSubscribeLobbyUpdate = '/game/lobby/' + $scope.game.id + '/update';
                    gameService.stompSubscribe(urlSubscribeLobbyUpdate, function(gameInfo) {
                        $scope.game.users = gameInfo.users;
                        $scope.game.urlSubscribeLobbyUpdate = urlSubscribeLobbyUpdate;
                        gameService.lobbySet($scope.game);
                        $scope.$apply();
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

        $scope.switchStatus = function() { //TODO: add check on server if game will start
            var lobby = gameService.lobbyGet();
            gameService.sendMsg('/game/lobby/user/status', {
                lobbyId: lobby.id,
                active: ! lobby.currentUser.active
            });
        };

        $scope.updateLobbyUsers = function() {
            $scope.game.users = [];
            updateLobby(false);
        };
    })
    .controller('ListGameController', function ($scope, gameService) {
        $scope.searchText = '';
        var lobby = gameService.lobbyGet();
        if (lobby != null) {
            if (!lobby.owner) {
                if (lobby.urlSubscribeLobbyUpdate)
                    gameService.stompUnsubscribe(lobby.urlSubscribeLobbyUpdate);
                gameService.lobbyRemoveUser(lobby.id);
            }
            gameService.lobbySet(null);
        }

        $scope.page = 1;
        $scope.loadAll = function () {
            gameService.lobbyList($scope.page, $scope.searchText).then(function (data) {
                $scope.game = data;
            });
        };
        $scope.search = function() {
            $scope.loadAll();
        };
        $scope.loadAll();
    })
;
