package jkord.dynablaster.domain.obj;

import jkord.core.domain.User;
import jkord.dynablaster.domain.GameMap;
import jkord.dynablaster.domain.piece.Direction;
import jkord.dynablaster.domain.piece.MapObjectType;

public class PlayerObject extends GameObject {

    private User user;

    public PlayerObject(User user) {
        this.user = user;
    }

    @Override
    public void move(int x, int y) {
        if (x >= 0 && y >= 0 && x < GameMap.HORIZONTAL_SIZE && y < GameMap.VERTICAL_SIZE) {
            int oldX = position.x, oldY = position.y;
            if (map.setObjToMap(new MapObject(MapObjectType.PLAYER, this), x, y)) {
                map.setObjToMapWithoutCheck(new MapObject(MapObjectType.FREE), oldX, oldY);
                setPosition(x, y);
            }
        }
    }

    public void move(Direction direction) {
        int x = position.x, y = position.y;
        switch (direction) {
            case UP: y--; break;
            case DOWN: y++; break;
            case LEFT: x--; break;
            case RIGHT: x++; break;
        }
        move(x, y);
    }

    @Override
    public void putBomb() {

    }

    @Override
    public void die() {

    }

    public User getUser() {
        return user;
    }
}
