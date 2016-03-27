'use strict';

angular.module('dynablasterApp')
    .factory('gameService', function ($http) {
        return {
            socket: null,
            stompClient: null,
            startGame: function (type) {
                return $http.put('api/game/start/' + type).then(function (response) {
                    return response.data;
                });
            },
            endGame: function () {
                this.socketClose();
                return $http.put('api/game/end').then(function (response) {
                    return response.data;
                });
            },
            socketInit: function(connect) {
                this.socket = new SockJS('/game-msg');
                this.stompClient = Stomp.over(this.socket);
                this.stompClient.debug = null;
                this.stompClient.connect({}, connect);
            },
            socketClose: function() {
                if (this.stompClient != null) {
                    this.stompClient.disconnect();
                    this.socket.close();
                }
            },
            sendMsg: function(name, data) {
                this.stompClient.send('/app/' + name, {}, JSON.stringify(data));
            },
            stompSubscribe: function(name, action) {
                this.stompClient.subscribe(name, function(msg){
                    action(JSON.parse(msg.body));
                });
            }
        }
    });
