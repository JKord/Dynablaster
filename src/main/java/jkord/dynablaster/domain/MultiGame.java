package jkord.dynablaster.domain;

import jkord.dynablaster.domain.obj.MapObject;
import jkord.dynablaster.domain.obj.PlayerObject;
import jkord.dynablaster.domain.piece.GameType;
import jkord.dynablaster.domain.piece.MapObjectType;
import jkord.dynablaster.entity.Lobby;
import jkord.dynablaster.entity.LobbyUser;
import jkord.dynablaster.service.GameService;

public class MultiGame extends Game {
    protected Lobby lobby;

    public Lobby getLobby() {
        return lobby;
    }

    public MultiGame(String key) {
        super(key);
        type = GameType.MULTI;
    }

    public void start(Lobby lobby) {
        super.start();
        this.lobby = lobby;

        int i = 0, pozUsers[][] = {{0, 0}, {12, 0}, {0, 10}, {12, 10}};
        for (LobbyUser lobbyUser: lobby.getUsers()) {
            map.setObjToMap(new MapObject(
                MapObjectType.PLAYER, new PlayerObject(lobbyUser.getUser())),
                pozUsers[i][0], pozUsers[i][1]
            );
            i++;
        }

        /*statistics = new Statistic(
            lobby.getUsers()
                .stream()
                .filter(LobbyUser::isActive)
                .map(lobbyUser -> lobbyUser.getUser())
                .collect(Collectors.toSet())
        );*/
    }
}
