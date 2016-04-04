package jkord.dynablaster.domain.obj;
import jkord.dynablaster.domain.GameMap;
import jkord.dynablaster.domain.piece.MapObjectType;
import jkord.dynablaster.domain.piece.Position;
import jkord.dynablaster.finder.AStar;
import jkord.dynablaster.finder.AreaMap;
import jkord.dynablaster.finder.heuristics.*;
import jkord.dynablaster.web.MsgRoute;
import jkord.dynablaster.web.dto.BotMsg;
import java.util.ArrayList;

public class BotObject extends GameObject {

    protected ArrayList<Position> pathToGo;
    protected int pathToGoIndex;

    public BotObject() {
        this.type = MapObjectType.MONSTER;
    }

    public ArrayList<Position> getPathToGo() {
        return pathToGo;
    }

    public void movePath(int x, int y) {
        AreaMap areaMap = new AreaMap(map);
        //AStarHeuristic heuristic = new ClosestHeuristic();
        AStarHeuristic heuristic = new DiagonalHeuristic();
        AStar aStar = new AStar(areaMap, heuristic);
        clearPath();
        pathToGo = aStar.calcShortestPath(position.x, position.y, x, y);
    }

    @Override
    public void update() {
        super.update();
        collision();
        move();
    }

    @Override
    public void putBomb(Position position) {

    }

    @Override
    public void die() {
        // TODO: put logic from service
    }

    protected void move() {
        if (pathToGo == null) {
            Position pozRand = GameMap.getRandPosition();
            for (int i = 0; i < 10; i++) {
                if (map.isFreePosition(pozRand.getX(), pozRand.getY()))
                    break;
                pozRand = GameMap.getRandPosition();
            }
            movePath(pozRand.getX(), pozRand.getY());
        } else {
            pathToGoIndex++;
            if (pathToGoIndex > pathToGo.size() - 1) {
                clearPath();
                return;
            }
            Position pozToGo = pathToGo.get(pathToGoIndex);
            if (! map.isFreePosition(pozToGo.x, pozToGo.y)) {
                clearPath();
                return;
            }

            move(pozToGo.getX(), pozToGo.getY());
        }
        SMessaging.send(String.format(MsgRoute.BOT_MOVE, id), new BotMsg(id, position));
    }

    protected void collision() {
        if (pathToGo != null) {
            int nextPathToGoIndex = pathToGoIndex + 1;
            if (nextPathToGoIndex < pathToGo.size() - 1) {
                MapObject obj = map.get(pathToGo.get(nextPathToGoIndex));
                if (obj.getType() == MapObjectType.PLAYER) {
                    obj.getGameObject().die();
                }
            }
        }
    }

    protected void clearPath() {
        pathToGo = null;
        pathToGoIndex = 0;
    }
}
