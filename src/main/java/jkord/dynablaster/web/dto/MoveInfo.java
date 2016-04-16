package jkord.dynablaster.web.dto;

import jkord.dynablaster.domain.piece.Position;

import java.io.Serializable;

public class MoveInfo implements Serializable {
    protected String direction;
    protected Position position;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
