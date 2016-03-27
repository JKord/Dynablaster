package jkord.dynablaster.domain;

import jkord.core.domain.User;
import jkord.dynablaster.domain.obj.MapObject;
import jkord.dynablaster.domain.piece.GameType;
import jkord.dynablaster.domain.piece.MapObjectType;

public class SingleWithBotsGame extends SingleGame {

    public SingleWithBotsGame(String key) {
        super(key);
        type = GameType.BOTS;
    }

    public void start(User user) {
        super.start(user);
        int pozEnemy[][] = {{12, 0}, {0, 10}, {12, 10}};
        for (int i = 0; i < 3; i++) {
            map.setObjToMap(new MapObject(MapObjectType.ENEMY), pozEnemy[i][0], pozEnemy[i][1]);
        }
    }
}
