package jkord.dynablaster.web.dto;

import jkord.dynablaster.domain.piece.Position;

import java.io.Serializable;
import java.util.ArrayList;

public class BotMsg implements Serializable {
    private int id;
    private ArrayList<Position> path;

    public BotMsg() {}

    public BotMsg(int id, ArrayList<Position> path) {
        this.id = id;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Position> getPath() {
        return path;
    }

    public void setPath(ArrayList<Position> path) {
        this.path = path;
    }
}
