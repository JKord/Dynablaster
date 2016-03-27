package jkord.dynablaster.domain.obj;

import jkord.core.domain.User;
import jkord.dynablaster.domain.GameMap;
import jkord.dynablaster.domain.piece.Direction;
import jkord.dynablaster.domain.piece.MapObjectType;
import jkord.dynablaster.domain.piece.Position;

public class PlayerObject extends GameObject {

    private User user;

    public PlayerObject(User user) {
        this.user = user;
        this.type = MapObjectType.PLAYER;
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
    public void putBomb(Position position) {

    }

    @Override
    public void die() {

    }

    public User getUser() {
        return user;
    }
}
