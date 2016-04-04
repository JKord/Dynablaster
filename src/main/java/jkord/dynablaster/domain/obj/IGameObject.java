package jkord.dynablaster.domain.obj;

import jkord.dynablaster.domain.GameMap;
import jkord.dynablaster.domain.piece.Position;

import java.awt.*;
import java.io.Serializable;

public interface IGameObject extends Serializable {
    GameMap getMap();
    void setMap(GameMap map);

    int getId();
    void setId(int id);
    Position getPosition();
    void setPosition(Position position);
    void setPosition(int x, int y);

    void move(int x, int y);
    void putBomb(Position position);
    void die();

    void update();
}
