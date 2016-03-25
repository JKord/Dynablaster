package jkord.dynablaster.domain;

import java.io.Serializable;

public interface IGameObject extends Serializable {
    void move(int x, int y);
    void putBomb();
    void die();
}
