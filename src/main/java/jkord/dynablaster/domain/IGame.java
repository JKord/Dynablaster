package jkord.dynablaster.domain;

import jkord.dynablaster.domain.obj.PlayerObject;

public interface IGame {
    String KEY_NAME = "gameKey";

    void start();
    void end();
    void update();

    String getKey();

    GameMap getMap();
    void setMap(GameMap map);

    PlayerObject getCurrentPlayer(Long id);
}
