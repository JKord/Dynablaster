angular.module('dynablasterApp')
    .factory('GOHero', ['loaderRes', function (loaderRes) {
        function GOHero(obj) {
            this.img = {
                up: loaderRes.getResult("heroUp"),
                down: loaderRes.getResult("heroDown"),
                left: loaderRes.getResult("heroLeft"),
                right: loaderRes.getResult("heroRight")
            };

            this.step = 40;
            this.size = {w: 34, h: 40};

            this.player = new createjs.Shape();
            this.setImg(this.img.down);
            this.player.x = obj.x;
            this.player.y = obj.y;
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
                this.player.x = this.player.x + x;
                this.player.y = this.player.y + y;
            },
            setImg: function(img) {
                this.player.graphics.clear();
                this.player.graphics.beginBitmapFill(img).drawRect(0, 0, this.size.w, this.size.h);
            },
            catchKeyCode: function(keyCode) {
                switch (keyCode) {
                    case 38: case 87: { // Up
                        this.setImg(this.img.up);
                        this.moveTo(0, -this.step);
                    } break;
                    case 40: case 83: { // Down
                        this.setImg(this.img.down);
                        this.moveTo(0, this.step);
                    } break;
                    case 37: case 65: { // Left
                        this.setImg(this.img.left);
                        this.moveTo(-this.step, 0);
                    } break;
                    case 39: case 68: { // Right
                        this.setImg(this.img.right);
                        this.moveTo(this.step, 0);
                    } break;
                    case 32: { // TODO: Bomb
                        alert('Burst!');
                    } break;
                }
            }
        };

        return GOHero;
    }]);
