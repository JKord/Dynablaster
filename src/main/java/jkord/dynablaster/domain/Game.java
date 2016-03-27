package jkord.dynablaster.domain;

import jkord.dynablaster.domain.obj.PlayerObject;
import jkord.dynablaster.entity.Lobby;
import jkord.dynablaster.entity.LobbyUser;
import jkord.dynablaster.entity.Statistics;

import java.util.stream.Collectors;

abstract class Game implements IGame {

    protected String key;
    protected GameMap map;

    protected Statistics statistics;

    public Game(String key) {
        this.key = key;
    }

    public void start() {
        map = new GameMap();
    }

    public void end() {
        // TODO
    }

    public void update() {
        map.update();
    }

    public String getKey() {
        return key;
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public PlayerObject getCurrentPlayer(Long id) {
        return map.getPlayers().get(id);
    }
}
