package jkord.dynablaster.domain;

import jkord.dynablaster.domain.obj.PlayerObject;
import jkord.dynablaster.domain.piece.GameType;
import jkord.dynablaster.entity.Statistic;

public interface IGame extends Runnable {
    String KEY_NAME = "gameKey";

    void start();
    void end();
    void update();

    boolean isRun();
    void setRun(boolean run);

    String getKey();
    GameType getType();

    GameMap getMap();
    void setMap(GameMap map);

    PlayerObject getCurrentPlayer(Long id);
    Statistic getStatistic();
}
