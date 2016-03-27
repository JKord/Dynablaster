package jkord.dynablaster.domain;

import jkord.core.service.util.RandomUtil;
import jkord.dynablaster.domain.obj.BotObject;
import jkord.dynablaster.domain.obj.IGameObject;
import jkord.dynablaster.domain.obj.MapObject;
import jkord.dynablaster.domain.obj.PlayerObject;
import jkord.dynablaster.domain.piece.MapObjectType;
import jkord.dynablaster.domain.piece.Position;
import org.codehaus.jackson.annotate.JsonValue;

import java.io.Serializable;
import java.util.*;

public class GameMap implements Serializable {

    public static final int HORIZONTAL_SIZE = 13;
    public static final int VERTICAL_SIZE = 11;
    public static final int COUNT_BRICKS = 25;
    public static final int COUNT_MONSTERS = 5;

    private static final  List<String> PROTECTED_AREA = Arrays.asList(
        "00", "01", "10",
        "011", "012", "112",
        "90", "100", "101",
        "1110", "1210", "129"
    );

    protected MapObject mapObjects[][] = new MapObject[HORIZONTAL_SIZE][VERTICAL_SIZE];
    protected Map<Long, PlayerObject> players = new HashMap<>();
    protected Map<Integer, BotObject> bots = new HashMap<>();

    public GameMap() {
        createMap();
    }

    @JsonValue
    public MapObject[][] getMap() {
        return mapObjects;
    }

    public Map<Long, PlayerObject> getPlayers() {
        return players;
    }

    public Map<Integer, BotObject> getBots() {
        return bots;
    }

    public static Position getRandPosition() {
        Random rand = new Random();
        return new Position(
            rand.nextInt(HORIZONTAL_SIZE - 1) + 1,
            rand.nextInt(VERTICAL_SIZE - 1) + 1
        );
    }

    public boolean isFreePosition(int x, int y) {
        return mapObjects[x][y].getType() == MapObjectType.FREE;
    }

    public boolean setObjToMap(MapObject obj, int x, int y) {
        if (isFreePosition(x, y)) {
            setObjToMapWithoutCheck(obj, x, y);

            return true;
        } else {
            return false;
        }
    }

    public void setObjToMapWithoutCheck(MapObject obj, int x, int y) {
        IGameObject gameObj = obj.getGameObject();
        switch (obj.getType()) {
            case PLAYER: {
                setIdToMapObj(obj);
                PlayerObject player = (PlayerObject) gameObj;
                players.put(player.getUser().getId(), player);
            } break;
            case MONSTER: case ENEMY: {
                setIdToMapObj(obj);
                gameObj = new BotObject();
                bots.put(obj.getId(), (BotObject) gameObj);
            } break;
        }
        if (gameObj != null) {
            gameObj.setPosition(x, y);
            gameObj.setMap(this);
        }
        mapObjects[x][y] = obj;
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
            Position pos = getRandPosition();

            if (mapObjects[pos.x][pos.y].getType() == MapObjectType.FREE &&
                ! PROTECTED_AREA.contains(String.valueOf(pos.x) + String.valueOf(pos.y)))
            {
                setObjToMapWithoutCheck(new MapObject(type), pos.x, pos.y);
            } else {
                i--;
            }
        }
    }

    private void setIdToMapObj(MapObject obj) {
        if (obj.getId() == -1) {
            obj.setId(RandomUtil.generateId());
        }
    }
}
