import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javafx.geometry.Point2D;

class depthPoint {
	Point2D point;
	int depth;
	
	depthPoint (Point2D p, int depth) {
		this.point = p;
		this.depth = depth;
	}
}

public class ShortestPath {
	
	private static ArrayList<Point2D> getAdjList(SpaceMap spaceMap, Point2D p) {
		ArrayList<Point2D> adjList = new ArrayList<>();
		
		Point2D a = new Point2D(p.getX() + 1, p.getY());
		Point2D b = new Point2D(p.getX() - 1, p.getY());
		Point2D c = new Point2D(p.getX(), p.getY() + 1);
		Point2D d = new Point2D(p.getX(), p.getY() - 1);
		
		Inhabitant ia = spaceMap.getInhabitant(new Point2D(p.getX() + 1, p.getY()));
		Inhabitant ib = spaceMap.getInhabitant(new Point2D(p.getX() - 1, p.getY()));
		Inhabitant ic = spaceMap.getInhabitant(new Point2D(p.getX(), p.getY() + 1));
		Inhabitant id = spaceMap.getInhabitant(new Point2D(p.getX(), p.getY() - 1));
				
		if (ia != null && ia == Inhabitant.EMPTY || ia == Inhabitant.PLAYER) adjList.add(a);
		if (ib != null && ib == Inhabitant.EMPTY || ib == Inhabitant.PLAYER) adjList.add(b);
		if (ic != null && ic == Inhabitant.EMPTY || ic == Inhabitant.PLAYER) adjList.add(c);
		if (id != null && id == Inhabitant.EMPTY || id == Inhabitant.PLAYER) adjList.add(d);
		
		return adjList;
	}
	
	public static ArrayList<Point2D> findShortestPath(Point2D src, Point2D dest) {
		if (src.equals(dest))
			return null;//
		
		SpaceMap spaceMap = SpaceMap.getInstance();
		
		int[][] distMap = new int[spaceMap.dimensions][spaceMap.dimensions];
		
		for (int i = 0; i < distMap.length; i++) {
			Arrays.fill(distMap[i], Integer.MAX_VALUE);
		}
		
		ArrayList<Point2D> visited = new ArrayList<>();
		Queue<depthPoint> queue = new LinkedList<>();
		
		ArrayList<Point2D> shortestPath = new ArrayList<>();
		
		queue.add(new depthPoint(dest,0));
		visited.add(dest);
		
		//Constructs a distance for each cell
		while (!queue.isEmpty()) {
			depthPoint p = queue.poll();
			
			if (p.equals(src))
				break;
			
			distMap[(int) p.point.getY()][(int) p.point.getX()] = p.depth;
			
			ArrayList<Point2D> adjList = getAdjList(spaceMap, p.point);
			
			for (Point2D x : adjList) {
				if (!visited.contains(x)) {
					queue.add(new depthPoint(x,p.depth + 1));
					visited.add(x);
				}
			}
		}
		
		while (!src.equals(dest)) {
			ArrayList<Point2D> adjList = getAdjList(spaceMap, src);
			
			Point2D next = null;
			int min = distMap[(int) src.getY()][(int) src.getX()];
			
			for (Point2D p : adjList) {
				int dist = distMap[(int) p.getY()][(int) p.getX()]; 
				if (dist < min) {
					min = dist;
					next = p;
				}
			}
			if (next == null)
				break;
			
			src = next;
			shortestPath.add(next);
		}
		
		return shortestPath;
		
	}

}
