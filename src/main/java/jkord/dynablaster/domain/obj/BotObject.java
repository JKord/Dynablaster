package jkord.dynablaster.domain.obj;

import jkord.dynablaster.domain.piece.MapObjectType;
import jkord.dynablaster.domain.piece.Position;
import jkord.dynablaster.finder.AStar;
import jkord.dynablaster.finder.AreaMap;
import jkord.dynablaster.finder.heuristics.*;
import jkord.dynablaster.finder.utils.PrintMap;

import java.util.ArrayList;

public class BotObject extends GameObject {

    protected ArrayList<Position> pathToGo;

    public BotObject() {
        this.type = MapObjectType.MONSTER;
    }

    public void movePath(int x, int y) {
        AreaMap areaMap = new AreaMap(map);
        //AStarHeuristic heuristic = new ClosestHeuristic();
        AStarHeuristic heuristic = new DiagonalHeuristic();
        AStar aStar = new AStar(areaMap, heuristic);
        pathToGo = aStar.calcShortestPath(position.x, position.y, x, y);

        /*if (pathToGo != null)
            new PrintMap(areaMap, pathToGo);*/
    }

    public ArrayList<Position> getPathToGo() {
        return pathToGo;
    }

    @Override
    public void putBomb() {

    }

    @Override
    public void die() {

    }
}
