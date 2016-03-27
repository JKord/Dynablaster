angular.module('dynablasterApp')
    .factory('GObj', function () {
        function GObj() {}
        GObj.prototype = {
            step: 40,
            size: {w: 34, h: 40},
            start: {x: 60, y: 40},
            position: {x: 0, y: 0},
            obj: null,
            addToStage: function (stage) {
                stage.addChild(this.obj);
            },
            removeFromStage: function (stage) {
                stage.removeChild(this.obj);
            },
            getX: function () { return this.obj.x; },
            setX: function (val) { this.obj.x =  val; },
            getY: function () { return this.obj.y; },
            setY: function (val) { this.obj.y =  val; },
            moveTo: function (x, y) {
                this.obj.x = this.start.x + x;
                this.obj.y = this.start.y + y;
            },
            move: function (x, y) {
                this.position = {x: x, y: y};
                this.moveTo(x * this.step, y * this.step);
            },
            setImg: function(img) {
                this.obj.graphics.clear();
                this.obj.graphics.beginBitmapFill(img).drawRect(0, 0, this.size.w, this.size.h);
            },
            update: function() {}
        };

        return GObj;
    });
