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
            endGameMsg: function(msg) {
                this.endGame();
                alert(msg);
                setTimeout(function() {
                    window.location.href = '/';
                }, 1000);
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
            },
            createLobby: function(lobby) {
                return $http.put('/api/game/lobby/create', lobby).then(function (response) {
                    return response.data;
                });
            }
        }
    });
