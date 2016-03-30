package jkord.dynablaster.domain.obj;

import jkord.dynablaster.domain.GameMap;
import jkord.dynablaster.domain.piece.MapObjectType;
import jkord.dynablaster.domain.piece.Position;

abstract class GameObject implements IGameObject {

    protected int id = -1;
    protected GameMap map;
    protected Position position;
    protected MapObjectType type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public MapObjectType getType() {
        return type;
    }

    public void setType(MapObjectType type) {
        this.type = type;
    }

    public void move(int x, int y) {
        if (x >= 0 && y >= 0 && x < GameMap.HORIZONTAL_SIZE && y < GameMap.VERTICAL_SIZE) {
            int oldX = position.x, oldY = position.y;
            if (map.setObjToMap(new MapObject(type, this), x, y)) {
                map.setObjToMapWithoutCheck(new MapObject(MapObjectType.FREE), oldX, oldY);
                setPosition(x, y);
            }
        }
    }
}
