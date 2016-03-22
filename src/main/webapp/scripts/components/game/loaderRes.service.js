'use strict';

angular.module('dynablasterApp')
    .factory('loaderRes', function (DIR_RES) {
        var manifest = [
                {src: "brick.png", id: "brick"},
                {src: "concrete.png", id: "concrete"},
                {src: "cr.png", id: "monster1"},
                {src: "hero_down.png", id: "heroDown"},
                {src: "hero_left.png", id: "heroLeft"},
                {src: "hero_right.png", id: "heroRight"},
                {src: "hero_up.png", id: "heroUp"},
            ],
            loader = new createjs.LoadQueue(true);

        /*createjs.Sound.registerPlugins([createjs.HTMLAudioPlugin]);
         loader.installPlugin(createjs.Sound);*/

        return {
            getResult: function (asset) {
                return loader.getResult(asset);
            },
            getLoader: function () {
                return loader;
            },
            loadAssets: function () {
                loader.loadManifest(manifest, true, DIR_RES + 'images/game/');
            }
        }
    });
