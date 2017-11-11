import java.util.ArrayList;
import java.util.Random;

import javafx.geometry.Point2D;

public class PatrolBehavior implements Behavior {
	
	private final int MAX_DIST = 5;
	
	private SpaceMap spaceMap;
	
	private ArrayList<Point2D> path;
	private int currentIndex;
	private boolean forward;
	
	public PatrolBehavior(Point2D position) {
		path = new ArrayList<Point2D>();
		spaceMap = SpaceMap.getInstance();
		forward = true;
		
		path.add(position);
		
		//Placeholder until algorithm implemented
		for (int i = 0; i < MAX_DIST; i++) {
			if (spaceMap.getInhabitant(path.get(i)) == Inhabitant.EMPTY) {
				path.add(new Point2D(path.get(i).getX() + 1, path.get(i).getY()));
			}
		}
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
