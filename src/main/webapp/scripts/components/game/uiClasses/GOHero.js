angular.module('dynablasterApp')
    .factory('GOHero', ['loaderRes', 'gameService', function (loaderRes, gameService) {
        function GOHero(obj) {
            this.img = {
                up: loaderRes.getResult("heroUp"),
                down: loaderRes.getResult("heroDown"),
                left: loaderRes.getResult("heroLeft"),
                right: loaderRes.getResult("heroRight")
            };
            this.currentImg = null;

            this.step = 40;
            this.size = {w: 34, h: 40};
            this.start = {x: 60, y: 40};

            this.player = new createjs.Shape();
            this.setImg(this.img.down);
            this.player.x = obj.x;
            this.player.y = obj.y;

            var self = this;
            gameService.stompClient.subscribe('/game/hero/move', function(message){
                var position = JSON.parse(message.body);
                console.log(position);
                console.log(34);

                self.setImg(self.currentImg);
                self.moveTo(position.x * self.step, position.y * self.step);
            });
        }
        GOHero.prototype = {
            addToStage: function (stage) {
                stage.addChild(this.player);
            },
            removeFromStage: function (stage) {
                stage.removeChild(this.player);
            },
            getX: function () { return this.player.x; },
            setX: function (val) { this.player.x =  val; },
            getY: function () { return this.player.y; },
            setY: function (val) { this.player.y =  val; },
            moveTo: function (x, y) {
                this.player.x = this.start.x + x;
                this.player.y = this.start.y + y;
            },
            setImg: function(img) {
                this.player.graphics.clear();
                this.player.graphics.beginBitmapFill(img).drawRect(0, 0, this.size.w, this.size.h);
            },
            catchKeyCode: function(keyCode) {
                var img, direction;
                switch (keyCode) {
                    case 38: case 87: { // Up
                        img = this.img.up;
                        direction = 'up';
                    } break;
                    case 40: case 83: { // Down
                        img = this.img.down;
                        direction = 'down';
                    } break;
                    case 37: case 65: { // Left
                        img = this.img.left;
                        direction = 'left';
                    } break;
                    case 39: case 68: { // Right
                        img = this.img.right;
                        direction = 'right';
                    } break;
                    case 32: { // TODO: Bomb
                        alert('Burst!');
                    } break;
                }
                if (direction) {
                    gameService.sendMsg('player/move', { direction: direction });
                    this.currentImg = img;
                }
            }
        };

        return GOHero;
    }]);
