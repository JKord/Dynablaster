package jkord.dynablaster.finder.heuristics;

import jkord.dynablaster.domain.piece.Position;

/**
 * The interface AStar Heuristics have to implement.
 */
public interface AStarHeuristic {

    /**
     * The heuristic tries to guess how far a given Node is from the goal Node.
     * The lower the cost, the more likely a Node will be searched next.
     *
     * @param start The coordinate of the tile being evaluated
     * @param goal The coordinate of the target location
     * @return The cost associated with the given tile
     */
    float getEstimatedDistanceToGoal(Position start, Position goal);
}
