angular.module('dynablasterApp')
    .factory('GOMap', function (loaderRes, GOHero, GOBot) {
        function GOGround() {
            this.img = {
                ground: loaderRes.getResult('ground'),
                brick: loaderRes.getResult('brick'),
                wall: loaderRes.getResult('wall'),
                monster1: loaderRes.getResult('monster1')
            };

            this.ground = new createjs.Shape();
            this.ground.graphics.beginBitmapFill(this.img.ground).drawRect(0, 0, 800, 645);
            this.ground.x = this.ground.y = 0;

            this.bricks = [];
            this.walls = [];
            this.bots = [];
            this.gems = [];

            this.start = {x: 60, y: 40};
            this.size = {w: 40, h: 40};
        }
        GOGround.prototype = {
            addToStage: function (stage) {
                stage.addChild(this.ground);
                this.walls.forEach(function(wall) { stage.addChild(wall); });
                this.bricks.forEach(function(brick) { stage.addChild(brick); });
                this.gems.forEach(function(gem) {
                    gem.addToStage(stage);
                    gem.stage = stage;
                });
                this.bots.forEach(function(bot) { bot.addToStage(stage); });
            },
            removeFromStage: function (stage) {
                stage.removeChild(this.ground);
                this.walls.forEach(function(wall) { stage.removeChild(wall); });
                this.bricks.forEach(function(brick) { stage.addChild(brick); });
                this.gems.forEach(function(gem) { gem.removeFromStage(stage); });
                this.bots.forEach(function(bot) { bot.removeFromStage(stage); });
            },
            createGO: function(img, x, y) {
                var obj = new createjs.Shape();
                obj.graphics.beginBitmapFill(img).drawRect(0, 0, this.size.w, this.size.h);
                obj.x = x;
                obj.y = y;

                return obj;
            },
            loadObj: function(map) {
                var self = this;
                map.forEach(function(items, i) {
                    items.forEach(function(item, j) {
                        var x = self.start.x + self.size.w * i,
                            y = self.start.y + self.size.h * j;

                        switch (item.type) {
                            case 'WALL': {
                                self.walls.push(self.createGO(self.img.wall, x, y));
                            } break;
                            case 'BRICK': {
                                self.bricks.push(self.createGO(self.img.brick, x, y));
                            } break;
                            case 'MONSTER': case 'ENEMY': {
                                self.bots.push(new GOBot({x: x, y: y}, item.id, item.type));
                            } break;
                            case 'PLAYER': {
                                self.gems.push(new GOHero({x: x, y: y}));
                            } break;
                        }
                    });
                });
            },
            update: function() {
                this.bots.forEach(function(bot) { bot.update(); });
            }
        };
        return GOGround;
    });
