package jkord.dynablaster.web.dto;

import jkord.dynablaster.domain.obj.MapObject;
import jkord.dynablaster.domain.piece.MapObjectType;
import jkord.dynablaster.domain.piece.Position;

import java.io.Serializable;

public class MapPositionObject implements Serializable {
    private int id;
    private Position position;
    private MapObjectType type;

    public MapPositionObject(Position position, MapObject mapObject) {
        this.id = mapObject.getId();
        this.type = mapObject.getType();
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public MapObjectType getType() {
        return type;
    }

    public void setType(MapObjectType type) {
        this.type = type;
    }
}
