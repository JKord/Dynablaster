angular.module('dynablasterApp')
    .factory('GOBot', function (GObj, loaderRes, gameService) {
        function GOBot(poz, id) {
            GOBot.__super__.constructor.apply(this);
            this.size = {w: 40, h: 40};

            this.id = id;
            this.obj = new createjs.Shape();
            this.obj.x = poz.x;
            this.obj.y = poz.y;

            this.setImg(loaderRes.getResult("monster1"));

            var self = this;
            gameService.stompSubscribe('/game/bot/move', function(data){
                if (self.id == data.id) {
                    self.pathToGo = data.path;
                    self.pathToGoI = 0;
                }
            });

            this.newPath = function (){
                gameService.sendMsg('bot/move', {id: this.id, path: []});
            };

            this.update = function() {
                if (self.pathToGo) {
                    if (self.pathToGo[self.pathToGoI]) {
                        var poz = self.pathToGo[self.pathToGoI];
                        self.move(poz.x, poz.y);
                        self.pathToGoI++;
                    } else {
                        self.pathToGo = null;
                        self.pathToGoI = 0;
                        setTimeout(function() {
                            self.newPath();
                            console.log('newPath');
                        }, 2000);
                    }
                }
            };
            this.newPath();

        } __extends(GOBot, GObj);

        return GOBot;
    });
