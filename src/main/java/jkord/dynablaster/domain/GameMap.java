package jkord.dynablaster.domain;

import org.codehaus.jackson.annotate.JsonValue;

import java.io.Serializable;

public class GameMap implements Serializable {

    public static final int HORIZONTAL_SIZE = 13;
    public static final int VERTICAL_SIZE = 11;

    protected MapObject mapObjects[][];

    public GameMap() {
        mapObjects = new MapObject[VERTICAL_SIZE][HORIZONTAL_SIZE];

        for (int i = 0; i < HORIZONTAL_SIZE; i++) {
            mapObjects[0][i] = new MapObject(MapObjectType.FREE);
            mapObjects[VERTICAL_SIZE - 1][i] = new MapObject(MapObjectType.FREE);
        }

        for (int i = 1; i < VERTICAL_SIZE; i++) {
            for (int j = 0; j < HORIZONTAL_SIZE - 1; j++) {
                if (i  % 2 != 0 && j  % 2 != 0) {
                    mapObjects[i][j] = new MapObject(MapObjectType.WALL);
                } else {
                    mapObjects[i][j] = new MapObject(MapObjectType.FREE);
                }
            }
        }
    }

    @JsonValue
    public MapObject[][] getMap() {
        return mapObjects;
    }

    public boolean setObjToMap(MapObject obj, int x, int y) {
        if (mapObjects[x][y].type == MapObjectType.FREE) {
            mapObjects[x][y] = obj;

            return true;
        } else {
            return false;
        }
    }
}
