package jkord.dynablaster.domain.obj;

import java.io.Serializable;

public interface IGameObject extends Serializable {
    void move(int x, int y);
    void putBomb();
    void die();
}
