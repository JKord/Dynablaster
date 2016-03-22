package jkord.dynablaster.domain;

import java.util.stream.Collectors;

public class Game {

    protected GameMap map;
    protected Lobby lobby;
    protected Statistics statistics;

    public Game() {
        lobby = new Lobby();
    }

    public void start() {
        map = new GameMap();
        statistics = new Statistics(
            lobby.lobbyUser
                .stream()
                .filter(LobbyUser::isActive)
                .map(lobbyUser -> lobbyUser.getUser())
                .collect(Collectors.toSet())
        );
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
