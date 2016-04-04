package jkord.dynablaster.web.dto;

import jkord.dynablaster.domain.IGame;
import jkord.dynablaster.domain.obj.MapObject;

import java.io.Serializable;

public class GameDTO implements Serializable {
    private String key;

    private MapObject map[][];

    public GameDTO(String key, MapObject mapObjects[][]) {
        this.key = key;
        this.map = mapObjects;
    }

    public GameDTO(IGame game) {
        this(game.getKey(), game.getMap().getMap());
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MapObject[][] getMap() {
        return map;
    }

    public void setMap(MapObject[][] map) {
        this.map = map;
    }
}
