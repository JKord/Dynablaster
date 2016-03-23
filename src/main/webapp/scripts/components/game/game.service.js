'use strict';

angular.module('dynablasterApp')
    .factory('gameService', function ($http) {
        var socket = null, stompClient = null;

        return {
            getMap: function () {
                return $http.get('api/game/map').then(function (response) {
                    return response.data;
                });
            },
            socketInit: function() {
                socket = new SockJS('/game-msg');
                stompClient = Stomp.over(socket);

                stompClient.connect({}, function(frame) {
                    console.log('Connected: ' + frame);

                    stompClient.subscribe('/move', function(message){
                        console.log(message);
                        console.log(3434);
                    });
                });
            },
            sendMsg: function(data) {
                //JSON.parse(text.body).content
                stompClient.send('/app/game-msg', {}, data);
            },
            socketClose: function() {
                sock.close();
            }
        }
    });
