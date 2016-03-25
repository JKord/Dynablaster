package jkord.dynablaster.domain.obj;

import jkord.core.domain.User;
import jkord.dynablaster.domain.piece.MapObjectType;
import jkord.dynablaster.domain.piece.Position;

public class PlayerObject extends GameObject {

    private User user;

    public PlayerObject(User user) {
        this.user = user;
    }

    @Override
    public void move(int x, int y) {
        if (map.setObjToMap(new MapObject(MapObjectType.PLAYER, this), x, y)) {
            map.setObjToMapWithoutCheck(new MapObject(MapObjectType.FREE), x, y);
            setPosition(x, y);
        }
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
