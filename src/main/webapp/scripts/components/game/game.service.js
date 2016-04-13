'use strict';

angular.module('dynablasterApp')
    .factory('gameService', function ($http, localStorageService, $cookies, Principal) {
        return {
            socket: null,
            stompClient: null,
            data: {},
            subscribes: [],
            startGame: function (type) {
                return $http.put('api/game/start/' + type).then(function (response) {
                    return response.data;
                });
            },
            endGame: function () {
                this.socketClose();
                this.lobbySet(null);
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
            sendMsg: function(name, data) {
                this.stompClient.send('/app/' + name, {}, JSON.stringify(data));
            },
            stompSubscribe: function(name, action) {
                if (this.subscribes.indexOf(name) == -1) {
                    this.stompClient.subscribe(name, function(msg){
                        action(JSON.parse(msg.body));
                    });
                    this.subscribes.push(name);
                }
            },

            // ---------------------------------------- Lobby ----------------------------------------------------------
            lobbyCreate: function(lobby) {
                return $http.put('/api/game/lobby/create', lobby).then(function (response) {
                    return response.data;
                });
            },
            lobbyList: function() {
                return $http.get('/api/game/lobby/list').then(function (response) {
                    return response.data;
                });
            },
            isLobbyGet: function() {
                return localStorageService.get('lobby') != null;
            },
            lobbySetCurrentUser: function(data) {
                if (data.users) {
                    Principal.identity().then(function(user) {
                        data.users.forEach(function(lobbyUser) {
                            if (lobbyUser.user.id == user.id) {
                                data.currentLobbyUser = lobbyUser;
                                return true;
                            }
                        });
                    });
                }
            },
            lobbyGetFromServer: function(id) {
                var self = this;
                return $http.get('/api/game/lobby/' + id + '/get').then(function(response) {
                    self.lobbySetCurrentUser(response.data);
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
                this.lobbySetCurrentUser(lobby);

                return localStorageService.set('lobby', lobby);
            }
        }
    });
