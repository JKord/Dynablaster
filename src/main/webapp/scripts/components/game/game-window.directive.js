'use strict';

angular.module('dynablasterApp')
    .directive('gameWindow',['loaderRes', 'GOHero', function(loaderRes, GOHero) {
        return {
            restrict: 'EAC',
            replace: true,
            scope: {
                width: '=width',
                height: '=height',
                score: '=score'
            },
            template: "<canvas width='960' height='400'></canvas>",
            link: function (scope, elem, attrs) {
                var w, h, gameObj = {};

                drawGame();
                elem[0].width = scope.width;
                elem[0].height = scope.height;
                w = scope.width;
                h = scope.height;

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
                    gameObj.hero = new GOHero({x: 10, y: 10});
                    gameObj.hero.addToStage(scope.stage);


                    window.onkeydown = keydown;
                    createjs.Ticker.timingMode = createjs.Ticker.RAF;
                    createjs.Ticker.addEventListener("tick", tick);
                    scope.$apply();
                }

                function keydown(event) {
                    gameObj.hero.catchKeyCode(event.keyCode);
                }

                function tick(event) {
                    var deltaS = event.delta / 1000;

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
    }]);
