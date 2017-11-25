import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import javafx.geometry.Point2D;

public class PatrolBehavior implements Behavior {
	
	private final int MIN_RANGE = Game.GRID_SIZE / 10;
	private final int BOUND = Game.GRID_SIZE / 5;
	
	private SpaceMap spaceMap;
	
	private Point2D position;
	private ArrayList<Point2D> path;
	private int currentIndex;
	private boolean forward;
	
	public PatrolBehavior(Point2D position) {
		spaceMap = SpaceMap.getInstance();
		path = new ArrayList<>();
		forward = true;
		this.position = position;
		
		path.add(position);
		path = ShortestPath.findShortestPath(position,randomPoint(position));	
	}
	
	private Point2D randomPoint(Point2D location) {
		Random r = new Random();
		Point2D randomPoint = null;
		
		while (randomPoint == null) {
			int randDist = r.nextInt(BOUND) + MIN_RANGE;
			
			if (spaceMap.getInhabitant(new Point2D (position.getX() + randDist,position.getY())) == Inhabitant.EMPTY)
				randomPoint = new Point2D(position.getX() + randDist, position.getY());
			
			else if (spaceMap.getInhabitant(new Point2D (position.getX(),position.getY() + randDist)) == Inhabitant.EMPTY)
				randomPoint = new Point2D(position.getX(), position.getY() + randDist);
			
			else if (spaceMap.getInhabitant(new Point2D (position.getX() - randDist,position.getY())) == Inhabitant.EMPTY)
				randomPoint = new Point2D(position.getX() - randDist, position.getY());
			
			else if (spaceMap.getInhabitant(new Point2D (position.getX(),position.getY() - randDist)) == Inhabitant.EMPTY)
				randomPoint = new Point2D(position.getX(), position.getY() - randDist);
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
