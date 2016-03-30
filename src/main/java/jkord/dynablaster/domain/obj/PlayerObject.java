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

    public User getUser() {
        return user;
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
    public void die() {

    }

    @Override
    public void putBomb(Position position) {
        for (int i = 0; i < 2; i++) {
            if (position.x < GameMap.VERTICAL_SIZE)
                burstBomb(position.x + i, position.y);
            if (position.x - i >= 0)
                burstBomb(position.x - i, position.y);
            if (position.y + i < GameMap.HORIZONTAL_SIZE)
                burstBomb(position.x, position.y + i);
            if (position.y - i >= 0)
                burstBomb(position.x, position.y - i);
        }
    }

    protected void burstBomb(int x, int y) {
        MapObject obj = map.getMap()[x][y];
        if (obj.getType() == MapObjectType.WALL)
            return;
        if (obj.gameObject != null) {
            obj.gameObject.die();
        }
        map.destroyObj(obj, x, y);
        map.setFastObjToMap(new MapObject(MapObjectType.FREE), x, y);
    }
}
