package jkord.dynablaster.domain.obj;

import jkord.dynablaster.finder.AStar;
import jkord.dynablaster.finder.AreaMap;
import jkord.dynablaster.finder.heuristics.*;

import java.awt.*;
import java.util.ArrayList;

public class BotObject extends GameObject {

    protected ArrayList<Point> pathToGo;

    @Override
    public void move(int x, int y) {
        AreaMap areaMap = new AreaMap(map);
        //AStarHeuristic heuristic = new ClosestHeuristic();
        AStarHeuristic heuristic = new DiagonalHeuristic();
        AStar aStar = new AStar(areaMap, heuristic);
        pathToGo = aStar.calcShortestPath(position.x, position.y, x, y);
    }

    public ArrayList<Point> getPathToGo() {
        return pathToGo;
    }

    @Override
    public void putBomb() {

    }

    @Override
    public void die() {

    }
}
