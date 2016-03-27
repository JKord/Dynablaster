package jkord.dynablaster.domain.obj;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jkord.dynablaster.domain.obj.IGameObject;
import jkord.dynablaster.domain.piece.MapObjectType;
import java.io.Serializable;

public class MapObject implements Serializable {

    protected int id = -1;
    protected MapObjectType type;

    @JsonIgnore
    protected IGameObject gameObject;

    public MapObject(MapObjectType type) {
        this.type = type;
    }

    public MapObject(MapObjectType type, IGameObject gameObject, int id) {
        this.type = type;
        this.gameObject = gameObject;
        this.id = id;
    }

    public MapObject(MapObjectType type, IGameObject gameObject) {
       this(type, gameObject, -1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
