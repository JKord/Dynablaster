package jkord.dynablaster.web.dto;

import jkord.dynablaster.domain.piece.Position;

import java.io.Serializable;
import java.util.ArrayList;

public class BotMsg implements Serializable {
    private int id;
    private Position position;

    public BotMsg() {}

    public BotMsg(int id, Position position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
