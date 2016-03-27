'use strict';

angular.module('dynablasterApp')
    .factory('gameService', function ($http) {
        var socket = null;

        return {
            stompClient: null,
            startGame: function (type) {
                return $http.put('api/game/start/' + type).then(function (response) {
                    return response.data;
                });
            },
            socketInit: function(connect) {
                socket = new SockJS('/game-msg');
                this.stompClient = Stomp.over(socket);
                var self = this;

                this.stompClient.connect({}, function(frame) {
                    console.log('Connected: ' + frame);
                    connect();
                });
            },
            socketClose: function() {
                if (this.stompClient != null) {
                    this.stompClient.disconnect();
                    sock.close();
                }
            },
            sendMsg: function(name, data) {
                this.stompClient.send('/app/' + name, {}, JSON.stringify(data));
            },
            stompSubscribe: function(name, action) {
                this.stompClient.subscribe(name, function(msg){
                    var msg = JSON.parse(msg.body);
                    console.log(msg);
                    action(msg);
                });
            }
        }
    });
