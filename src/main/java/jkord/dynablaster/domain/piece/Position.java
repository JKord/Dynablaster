package jkord.dynablaster.domain.piece;

import java.io.Serializable;

public class Position implements Cloneable, Serializable {
    public int x;
    public int y;

    public Position() { }
    public Position(int x, int y) {
        set(x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position pt = (Position) obj;
            return (x == pt.x) && (y == pt.y);
        }
        return super.equals(obj);
    }

    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }

    public Position clone() {
        return new Position(x, y);
    }
}
