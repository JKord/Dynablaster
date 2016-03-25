package jkord.dynablaster.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jkord.dynablaster.domain.piece.MapObjectType;
import java.io.Serializable;

public class MapObject implements Serializable {

    protected MapObjectType type;

    @JsonIgnore
    protected IGameObject gameObject;

    public MapObject(MapObjectType type) {
        this.type = type;
    }

    public MapObject(MapObjectType type, IGameObject gameObject) {
        this.type = type;
        this.gameObject = gameObject;
    }

    public MapObjectType getType() {
        return type;
    }

    public void setType(MapObjectType type) {
        this.type = type;
    }

    public IGameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(IGameObject gameObject) {
        this.gameObject = gameObject;
    }
}
