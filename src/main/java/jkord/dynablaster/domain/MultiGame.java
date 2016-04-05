package jkord.dynablaster.domain;

import jkord.dynablaster.domain.piece.GameType;
import jkord.dynablaster.entity.Lobby;
import jkord.dynablaster.entity.LobbyUser;
import jkord.dynablaster.entity.Statistics;

import java.util.stream.Collectors;

public class MultiGame extends Game {
    protected Lobby lobby = new Lobby();

    public MultiGame(String key) {
        super(key);
        type = GameType.MULTI;
    }

    public void start() {
        super.start();
        /*statistics = new Statistics(
            lobby.getUsers()
                .stream()
                .filter(LobbyUser::isActive)
                .map(lobbyUser -> lobbyUser.getUser())
                .collect(Collectors.toSet())
        );*/
    }
}
