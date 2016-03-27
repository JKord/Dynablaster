package jkord.dynablaster.domain;

import jkord.core.domain.User;
import jkord.dynablaster.domain.obj.MapObject;
import jkord.dynablaster.domain.obj.PlayerObject;
import jkord.dynablaster.domain.piece.GameType;
import jkord.dynablaster.domain.piece.MapObjectType;

public class SingleGame extends Game {

    private User user;

    public SingleGame(String key) {
        super(key);
        type = GameType.SINGLE;
    }

    public void start(User user) {
        super.start();
        this.user = user;
        map.setObjToMap(new MapObject(MapObjectType.PLAYER, new PlayerObject(user)), 0, 0);
    }

    public User getUser() {
        return user;
    }
}
