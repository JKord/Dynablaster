angular.module('dynablasterApp')
    .factory('GOMap', function (loaderRes, gameService, GOHero, GOBot) {
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

            this.map = [];
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
                this.bricks.forEach(function(brick) { stage.addChild(brick.obj); });
                this.gems.forEach(function(gem) { gem.addToStage(stage); });
                this.bots.forEach(function(bot) { bot.addToStage(stage); });
                this.stage = stage;
            },
            removeFromStage: function (stage) {
                stage.removeChild(this.ground);
                this.walls.forEach(function(wall) { stage.removeChild(wall); });
                this.bricks.forEach(function(brick) { stage.removeFromStage(brick.obj); });
                this.gems.forEach(function(gem) { gem.removeFromStage(stage.obj); });
                this.bots.forEach(function(bot) { bot.removeFromStage(stage.obj); });
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
                this.map = map;
                map.forEach(function(items, i) {
                    items.forEach(function(item, j) {
                        var x = self.start.x + self.size.w * i,
                            y = self.start.y + self.size.h * j;

                        switch (item.type) {
                            case 'WALL': {
                                self.walls.push(self.createGO(self.img.wall, x, y));
                            } break;
                            case 'BRICK': {
                                self.bricks.push({obj: self.createGO(self.img.brick, x, y), x: i, y: j});
                            } break;
                            case 'MONSTER': case 'ENEMY': {
                                self.bots.push(new GOBot({x: x, y: y}, item.id, item.type));
                            } break;
                            case 'PLAYER': {
                                self.gems.push(new GOHero({x: x, y: y}, item.id ));
                            } break;
                        }
                    });
                });
            },
            update: function() {
                //this.bots.forEach(function(bot) { bot.update(); });
            },
            destroyObject: function(obj){
                var self = this;
                switch (obj.type) {
                    case 'BRICK': {
                        this.bricks.forEach(function(item, i, bricks) {
                            if (item.x == obj.position.x && item.y == obj.position.y) {
                                bricks.splice(i, 1);
                                self.stage.removeChild(item.obj);

                                return true;
                            }
                        });
                    } break;
                    case 'MONSTER': case 'ENEMY': {
                        this.bots.forEach(function(item, i, bots) {
                            if (item.id == obj.id) {
                                bots.splice(i, 1);
                                item.removeFromStage(self.stage);

                                return true;
                            }
                        });
                        if (this.bots.length < 1) {
                            gameService.endGameMsg('You Win!');
                        }
                    } break;
                    case 'PLAYER': {
                        console.log(this.gems);
                        this.gems.forEach(function(item, i, gems) {
                            if (item.id == obj.id) {
                                gems.splice(i, 1);
                                item.removeFromStage(self.stage);
                                gameService.endGameMsg('You died!');

                                return true;
                            }
                        });
                    } break;
                }
                this.map[obj.position.x][obj.position.y] = {id: -1, type:"FREE"};
            }
        };
        return GOGround;
    });
