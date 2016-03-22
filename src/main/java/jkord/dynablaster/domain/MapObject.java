package jkord.dynablaster.domain;

import java.io.Serializable;

enum MapObjectType {
    FREE,
    BRICK,
    WALL,
    BOMB,
    PLAYER,
    BOT,
    ENEMY
}

public class MapObject implements Serializable {
    protected MapObjectType type;
    protected GameObject gameObject;

    public MapObject(MapObjectType type) {
        this.type = type;
    }

    public MapObjectType getType() {
        return type;
    }

    public void setType(MapObjectType type) {
        this.type = type;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }
}
