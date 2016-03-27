package jkord.dynablaster.domain;

import jkord.dynablaster.domain.obj.PlayerObject;
import jkord.dynablaster.domain.piece.GameType;
import jkord.dynablaster.entity.Statistics;

public interface IGame {
    String KEY_NAME = "gameKey";

    void start();
    void end();
    void update();

    String getKey();
    GameType getType();

    GameMap getMap();
    void setMap(GameMap map);

    PlayerObject getCurrentPlayer(Long id);
    Statistics getStatistics();
}
