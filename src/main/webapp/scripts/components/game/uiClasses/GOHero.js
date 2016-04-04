angular.module('dynablasterApp')
    .factory('GOHero', function (GObj, loaderRes, gameService) {
        function GOHero(poz, id) {
            GOHero.__super__.constructor.apply(this);
            this.img = {
                up: loaderRes.getResult("heroUp"),
                down: loaderRes.getResult("heroDown"),
                left: loaderRes.getResult("heroLeft"),
                right: loaderRes.getResult("heroRight"),
                bomb: loaderRes.getResult("bomb"),
                burst: loaderRes.getResult("burst")
            };
            this.currentImg = null;
            this.obj = new createjs.Shape();
            this.obj.x = poz.x;
            this.obj.y = poz.y;
            this.id = id;
            this.setImg(this.img.down);

            var self = this;
            gameService.stompSubscribe('/game/hero/' + this.id + '/move', function(position){
                self.setImg(self.currentImg);
                self.move(position.x, position.y);
            });
            gameService.stompSubscribe('/game/hero/' + this.id + '/bomb', function(data){
                console.log(data);
                data.forEach(function(item) {
                    gameService.goMap.destroyObject(item);
                });
            });
            gameService.stompSubscribe('/game/hero/' + this.id + '/die', function(){
                gameService.endGameMsg('You died!');
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
                    case 32: { // Bomb
                        self.putBomb();
                    } break;
                }
                if (direction) {
                    gameService.sendMsg('player/move', { direction: direction });
                    this.currentImg = img;
                }
            };
            this.putBomb = function() {
                var bomb = new createjs.Shape();
                var position = self.position;
                bomb.graphics.beginBitmapFill(self.img.bomb).drawRect(0, 0, 40, 40);
                bomb.x = self.getX();
                bomb.y = self.getY();
                self.stage.addChild(bomb);

                setTimeout(function() {
                    bomb.graphics.clear();
                    bomb.graphics.beginBitmapFill(self.img.burst).drawRect(0, 0, 145, 141);
                    bomb.x = bomb.x - 50;
                    bomb.y = bomb.y - 50;
                    gameService.sendMsg('/player/bomb', position);
                    setTimeout(function() {
                        self.stage.removeChild(bomb);
                    }, 800);
                }, 3000);

            };
        } __extends(GOHero, GObj);

        return GOHero;
    });
