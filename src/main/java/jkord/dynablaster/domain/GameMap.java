package jkord.dynablaster.domain;

import jkord.dynablaster.domain.obj.MapObject;
import jkord.dynablaster.domain.obj.PlayerObject;
import jkord.dynablaster.domain.piece.MapObjectType;
import org.codehaus.jackson.annotate.JsonValue;

import java.io.Serializable;
import java.util.*;

public class GameMap implements Serializable {

    public static final int HORIZONTAL_SIZE = 13;
    public static final int VERTICAL_SIZE = 11;
    public static final int COUNT_BRICKS = 30;
    public static final int COUNT_MONSTERS = 5;

    private static final  List<String> PROTECTED_AREA = Arrays.asList(
        "00", "01", "10",
        "011", "012", "112",
        "90", "100", "101",
        "1011", "1012", "912"
    );

    protected MapObject mapObjects[][] = new MapObject[HORIZONTAL_SIZE][VERTICAL_SIZE];
    protected Map<Long, PlayerObject> players = new HashMap<>();

    public GameMap() {
        createMap();
    }

    @JsonValue
    public MapObject[][] getMap() {
        return mapObjects;
    }

    public boolean setObjToMap(MapObject obj, int x, int y) {
        if (mapObjects[x][y].getType() == MapObjectType.FREE) {
            setObjToMapWithoutCheck(obj, x, y);

            return true;
        } else {
            return false;
        }
    }

    public void setObjToMapWithoutCheck(MapObject obj, int x, int y) {
        mapObjects[x][y] = obj;
        switch (mapObjects[x][y].getType()) {
            case PLAYER: {
                PlayerObject player = (PlayerObject) mapObjects[x][y].getGameObject();
                player.setPosition(x, y);
                player.setMap(this);

                players.put(player.getUser().getId(), player);
            } break;
            case MONSTER: case ENEMY: {
                // TODO
            } break;
        }
    }

    public Map<Long, PlayerObject> getPlayers() {
        return players;
    }

    protected void createMap() {
        for (int i = 0; i < VERTICAL_SIZE; i++) {
            mapObjects[0][i] = new MapObject(MapObjectType.FREE);
            mapObjects[HORIZONTAL_SIZE - 1][i] = new MapObject(MapObjectType.FREE);
        }

        for (int i = 1; i < HORIZONTAL_SIZE; i++) {
            for (int j = 0; j < VERTICAL_SIZE; j++) {
                if (i  % 2 != 0 && j  % 2 != 0) {
                    mapObjects[i][j] = new MapObject(MapObjectType.WALL);
                } else {
                    mapObjects[i][j] = new MapObject(MapObjectType.FREE);
                }
            }
        }

        generateRandObj(COUNT_BRICKS, MapObjectType.BRICK);
        generateRandObj(COUNT_MONSTERS, MapObjectType.MONSTER);
    }

    private void generateRandObj(int count, MapObjectType type) {
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int x = rand.nextInt(HORIZONTAL_SIZE - 1) + 1,
                y = rand.nextInt(VERTICAL_SIZE - 1) + 1;

            if (mapObjects[x][y].getType() == MapObjectType.FREE &&
                ! PROTECTED_AREA.contains(String.valueOf(x) + String.valueOf(y)))
            {
                mapObjects[x][y] = new MapObject(type);
            } else {
                i--;
            }
        }
    }
}
