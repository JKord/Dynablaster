'use strict';

angular.module('dynablasterApp')
    .directive('gameWindow', function(loaderRes, gameService, GOMap) {
        return {
            restrict: 'EAC',
            replace: true,
            scope: {
                width: '=width',
                height: '=height',
                score: '=score'
            },
            template: "<canvas width='640' height='520'></canvas>",
            link: function (scope, elem, attrs) {
                var w, h, gameObj = {}, type = 'single';

                gameService.startGame(type).then(function (data) {
                    scope.game = data;
                    gameService.socketInit(function() {
                        gameService.sendMsg('game/start', {});
                        drawGame();
                        elem[0].width = scope.width;
                        elem[0].height = scope.height;
                        w = scope.width;
                        h = scope.height;
                    });
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

                function handleComplete() {
                    gameObj.map = new GOMap(scope.game.map);
                    gameObj.map.addToStage(scope.stage);

                    window.onkeydown = keydown;
                    createjs.Ticker.timingMode = createjs.Ticker.RAF;
                    createjs.Ticker.addEventListener("tick", tick);
                    scope.$apply();
                }

                function keydown(event) {
                    gameObj.map.gems.forEach(function(gem) {
                        gem.catchKeyCode(event.keyCode);
                    });
                }
                function update(event) {

                }

                var time = 1;
                function tick(event) {
                    var deltaS = event.delta / 1000,
                        deltaT = event.delta / 1000 * 1000 >> 1;

                    if (time < event.timeStamp) {
                        time = event.timeStamp + 1100;
                        gameObj.map.update();
                    }

                    /*if (deltaT == 15)
                        gameObj.map.update();*/

                    //var position = grant.getX() + 150 * deltaS;
                    /*grant.setX((position >= w + grant.getWidth()) ? -grant.getWidth() : position);
                    ground.setX((ground.getX() - deltaS * 150) % ground.getTileWidth());
                    hill.move(deltaS * -30, 0);
                    if (hill.getX() + hill.getImageWidth() * hill.getScaleX() <= 0) {
                        hill.setX(w);
                    }
                    hill2.move(deltaS * -45, 0);
                    if (hill2.getX() + hill2.getImageWidth() * hill2.getScaleX() <= 0) {
                        hill2.setX(w);
                    }*/

                    scope.stage.update(event);
                }
            }
        }
    });
