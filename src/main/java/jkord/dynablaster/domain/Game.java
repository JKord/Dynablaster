package jkord.dynablaster.domain;

import jkord.dynablaster.domain.obj.PlayerObject;
import jkord.dynablaster.domain.piece.GameType;
import jkord.dynablaster.entity.Statistics;

abstract class Game implements IGame {

    protected String key;
    protected GameMap map;
    protected GameType type;

    protected Statistics statistics;

    protected boolean isRun = false;

    public Game(String key) {
        this.key = key;
    }

    public void start() {
        map = new GameMap();
        isRun = true;
    }

    public void end() {
        isRun = false;
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

    public GameType getType() {
        return type;
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }

    public void update() {
        map.update();
    }

    public void run() {
        while (isRun) {
            update();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) { }
        }
    }
}
