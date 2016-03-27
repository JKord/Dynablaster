package jkord.dynablaster.finder.utils;

import jkord.dynablaster.domain.piece.Position;
import jkord.dynablaster.finder.AreaMap;
import jkord.dynablaster.finder.Node;
import jkord.dynablaster.finder.Path;
import java.util.ArrayList;

public class PrintMap {
	public PrintMap(AreaMap map, Path shortestPath) {
		Node node;
		for(int y=0; y<map.getMapHeight(); y++) {

			if(y==0) {
				for (int i=0; i<=map.getMapWith(); i++)
					System.out.print("-");
				System.out.println();
			}
			System.out.print("|");

			for(int x=0; x<map.getMapWith(); x++) {
				node = map.getNode(x, y);


				if(node.isObstacle) {
					System.out.print("X");
				} else if(node.isStart) {
					System.out.print("s");
				} else if(node.isGoal) {
					System.out.print("g");
				} else if (shortestPath.contains(node.getX(), node.getY())) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
				if(y==map.getMapHeight())
					System.out.print("_");
			}

			System.out.print("|");
			System.out.println();
		}
		for (int i=0; i<=map.getMapHeight(); i++)
			System.out.print("-");
	}

	public PrintMap(AreaMap map, ArrayList<Position> shortestPath) {
		Node node;
		for(int y=0; y<map.getMapHeight(); y++) {

			if(y==0) {
				for (int i=0; i<=map.getMapHeight(); i++)
					System.out.print("-");
				System.out.println();
			}
			System.out.print("|");

			for(int x=0; x<map.getMapWith(); x++) {
				node = map.getNode(x, y);


				if(node.isObstacle) {
					System.out.print("X");
				} else if(node.isStart) {
					System.out.print("s");
				} else if(node.isGoal) {
					System.out.print("g");
				} else if (shortestPath.contains(new Position(node.getX(), node.getY()))) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
				if(y==map.getMapHeight())
					System.out.print("_");
			}

			System.out.print("|");
			System.out.println();
		}
		for (int i=0; i<=map.getMapHeight(); i++)
			System.out.print("-");
	}
}