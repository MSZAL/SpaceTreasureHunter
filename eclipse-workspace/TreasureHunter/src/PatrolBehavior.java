import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import javafx.geometry.Point2D;

public class PatrolBehavior implements Behavior {
	
	private final int MAX_RANGE = 5;
	
	private SpaceMap spaceMap;
	
	private ArrayList<Point2D> path;
	private int currentIndex;
	private boolean forward;
	
	public PatrolBehavior(Point2D position) {
		spaceMap = SpaceMap.getInstance();
		forward = true;
		
		path = ShortestPath.bfs(position,randomPoint(position));
		
	}
	
	private Point2D randomPoint(Point2D location) {
		Random r = new Random();
		Point2D randomPoint = null;
		
		while (spaceMap.getInhabitant(randomPoint) == null) {
			int x = r.nextInt(MAX_RANGE * 2) - MAX_RANGE;
			int y = r.nextInt(MAX_RANGE * 2) - MAX_RANGE;

			
			//System.out.print(x + "," + y);
			
			randomPoint = new Point2D(location.getX() + x, location.getY() + y);
		}
		
		return randomPoint;
	}
	

	
	@Override
	public Point2D nextMove() {
		if (currentIndex == path.size() - 1) forward = false;
		if (currentIndex == 0)               forward = true;
		
		if (forward) {
			currentIndex = currentIndex + 1;
			return path.get(currentIndex);
		}
		else {
			currentIndex = currentIndex - 1;
			return path.get(currentIndex);
		}
	}

}
