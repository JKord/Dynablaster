package jkord.dynablaster.domain.obj;

import jkord.dynablaster.domain.GameMap;
import jkord.dynablaster.domain.piece.Position;

abstract class GameObject implements IGameObject {

    protected GameMap map;
    protected Position position;

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
    }
}
