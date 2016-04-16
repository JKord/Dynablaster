'use strict';

angular.module('dynablasterApp')
    .directive('gameWindow', function(loaderRes, gameService, GOMap) {
        return {
            restrict: 'EAC',
            replace: true,
            scope: {
                width: '=width',
                height: '=height',
                typeGame: '=',
                score: '=score'
            },
            template: "<canvas width='640' height='520'></canvas>",
            link: function (scope, elem, attrs) {
                var w, h, gameObj = {};

                gameService.startGame(scope.typeGame).then(function (data) {
                    scope.game = data;
                    //gameService.socketInit(function() {
                    gameService.sendMsg('game/start', {});
                    drawGame();
                    elem[0].width = scope.width;
                    elem[0].height = scope.height;
                    w = scope.width;
                    h = scope.height;
                    //});
                    console.log(scope.game);
                });

                function drawGame() {
                    if (scope.stage) {
                        scope.stage.autoClear = true;
                        scope.stage.removeAllChildren();
                        scope.stage.update();
                    } else {
                        scope.stage = new createjs.Stage(elem[0]);
                    }

                    w = scope.stage.canvas.width;
                    h = scope.stage.canvas.height;

                    loaderRes.getLoader().addEventListener("complete", handleComplete);
                    loaderRes.loadAssets();
                }

                function getKeyUserFromLobby(gems) {
                    var lobby = gameService.lobbyGet(),
                        key = 0;

                    if (lobby) {
                        lobby.users.forEach(function(lobbyUser, index) {
                            if (lobbyUser.user.id == lobby.currentUser.user.id) {
                                key = index;
                            }
                            gems[index].user = lobbyUser.user;
                        });
                    }

                    return key;
                }

                function handleComplete() {
                    gameObj.map = new GOMap();
                    gameObj.map.loadObj(scope.game.map);
                    gameObj.map.addToStage(scope.stage);
                    gameService.goMap = gameObj.map;

                    gameObj.hero = gameObj.map.gems[getKeyUserFromLobby(gameObj.map.gems)];

                    console.log(scope.game.map);
                    console.log(gameObj.map.bots);

                    window.onkeydown = keydown;
                    createjs.Ticker.timingMode = createjs.Ticker.RAF;
                    createjs.Ticker.addEventListener("tick", tick);
                    scope.$apply();
                }

                function keydown(event) {
                    gameObj.hero.catchKeyCode(event.keyCode);
                }
                function update(event) {

                }

                var time = 1;
                function tick(event) {
                    if (time < event.timeStamp) {
                        time = event.timeStamp + 1100;
                        gameObj.map.update();
                    }
                    scope.stage.update(event);
                }
            }
        }
    });
