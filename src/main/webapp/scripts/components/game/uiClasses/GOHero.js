angular.module('dynablasterApp')
    .factory('GOHero', function (GObj, loaderRes, gameService) {
        function GOHero(poz) {
            GOHero.__super__.constructor.apply(this);
            this.img = {
                up: loaderRes.getResult("heroUp"),
                down: loaderRes.getResult("heroDown"),
                left: loaderRes.getResult("heroLeft"),
                right: loaderRes.getResult("heroRight")
            };
            this.currentImg = null;
            this.obj = new createjs.Shape();
            this.obj.x = poz.x;
            this.obj.y = poz.y;
            this.setImg(this.img.down);

            var self = this;
            gameService.stompSubscribe('/game/hero/move', function(position){
                self.setImg(self.currentImg);
                self.move(position.x, position.y);
            });

            this.catchKeyCode = function(keyCode) {
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
        } __extends(GOHero, GObj);

        return GOHero;
    });
