'use strict';

angular.module('dynablasterApp')
    .factory('gameService', function ($http, localStorageService, $cookies) {
        return {
            socket: null,
            stompClient: null,
            data: {},
            subscribes: {},
            startGame: function (type) {
                return $http.put('api/game/start/' + type).then(function (response) {
                    return response.data;
                });
            },
            endGame: function (isEnd) {
                this.lobbySet(null);
                if (isEnd == undefined || isEnd) {
                    return $http.put('api/game/end').then(function (response) {
                        return response.data;
                    });
                }
            },
            endGameMsg: function(msg, isEnd) {
                this.endGame(isEnd);
                alert(msg);
                setTimeout(function() {
                    window.location.href = '/';
                }, 1000);
            },

            // --------------------------------------- Socket ----------------------------------------------------------
            socketInit: function(connect) {
                if (this.socket == null) {
                    this.socket = new SockJS('/game-msg');
                    this.stompClient = Stomp.over(this.socket);
                    this.stompClient.debug = null;
                    this.stompClient.connect({}, connect);
                } else {
                    if (connect)
                        connect();
                }
            },
            socketClose: function() {
                if (this.stompClient != null) {
                    this.stompClient.disconnect();
                    this.socket.close();
                }
            },
            runFnStompConnected: function(connect) {
                if (!this.stompClient.connected) {
                    this.stompClient.connect({}, connect);
                } else {
                    connect();
                }
            },
            sendMsg: function(name, data) {
                var self = this;
                this.runFnStompConnected(function() {
                    self.stompClient.send('/app/' + name, {}, JSON.stringify(data));
                })
            },
            stompSubscribe: function(name, action) {
                var self = this;
                this.runFnStompConnected(function() {
                    if (self.subscribes[name] == undefined) {
                        self.subscribes[name] = self.stompClient.subscribe(name, function(msg){
                            action(JSON.parse(msg.body));
                        });
                    }
                });
            },
            stompUnsubscribe: function(name) {
                if (this.subscribes[name]) {
                    this.subscribes[name].unsubscribe();
                    delete this.subscribes[name];
                }
                console.log(this.subscribes);
            },

            // ---------------------------------------- Lobby ----------------------------------------------------------
            lobbyCreate: function(lobby) {
                return $http.put('/api/game/lobby/create', lobby).then(function (response) {
                    return response.data;
                });
            },
            lobbyList: function(page, searchText) {
                return $http.get('/api/game/lobby/list?size=10&page=' + (page - 1) + '&searchText=' + searchText)
                    .then(function (response) {
                        return { lobbies: response.data, totalItems: response.headers('X-Total-Count') };
                    });
            },
            isLobbyGet: function() {
                return localStorageService.get('lobby') != null;
            },
            lobbyGetFromServer: function(id) {
                return $http.get('/api/game/lobby/' + id + '/get').then(function(response) {
                    return response.data;
                });
            },
            lobbyAddUser: function(id) {
                return $http.put('/api/game/lobby/' + id + '/addUser').then(function(response) {
                    return response.data;
                });
            },
            lobbyRemoveUser: function(id) {
                return $http.delete('/api/game/lobby/' + id + '/removeUser').then(function(response) {
                    return response.data;
                });
            },
            lobbyGet: function() {
                return localStorageService.get('lobby');
            },
            lobbySet: function(lobby) {
                if (lobby) {
                    $cookies.put('lobbyId', lobby.id);
                } else {
                    $cookies.put('lobbyId', null);
                    $cookies.put('gameKey', null);
                }

                return localStorageService.set('lobby', lobby);
            }
        }
    });
