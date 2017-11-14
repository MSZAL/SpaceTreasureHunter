import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javafx.geometry.Point2D;

public class ShortestPath {
	
	private static ArrayList<Point2D> getAdjList(SpaceMap spaceMap, Point2D p) {
		ArrayList<Point2D> adjList = new ArrayList<Point2D>();
		
		Point2D a = new Point2D(p.getX() + 1, p.getY());
		Point2D b = new Point2D(p.getX() - 1, p.getY());
		Point2D c = new Point2D(p.getX(), p.getY() + 1);
		Point2D d = new Point2D(p.getX(), p.getY() - 1);
		
		Inhabitant ia = spaceMap.getInhabitant(a);
		Inhabitant ib = spaceMap.getInhabitant(b);
		Inhabitant ic = spaceMap.getInhabitant(c);
		Inhabitant id = spaceMap.getInhabitant(d);
				
		if (ia == Inhabitant.EMPTY) adjList.add(a);
		if (ib == Inhabitant.EMPTY) adjList.add(b);
		if (ic == Inhabitant.EMPTY) adjList.add(c);
		if (id == Inhabitant.EMPTY) adjList.add(d);
		
		return adjList;
	}
	
	public static ArrayList<Point2D> bfs(Point2D src, Point2D dest) {
		if (src.equals(dest))
			return null;//
		
		SpaceMap spaceMap = SpaceMap.getInstance();
		
		ArrayList<Point2D> shortestPath = new ArrayList<Point2D>();
		HashMap<Point2D, Boolean> visited = new HashMap<Point2D, Boolean>();
		
		Queue<Point2D> queue = new LinkedList<Point2D>();
		Stack<Point2D> stack = new Stack<Point2D>();
		
		queue.add(src);
		
		visited.put(src, true);
		
		while (!queue.isEmpty()) {
			Point2D p = queue.poll();
			stack.push(p);
			
			ArrayList<Point2D> adjList = getAdjList(spaceMap, p);
			
			for (Point2D v : adjList) {
				if (!visited.containsKey(v)) {
					queue.add(v);
					visited.put(v, true);
					if (v.equals(dest))
						break;
				}
				
			}
		}
		
		Point2D p1;
		Point2D p2 = dest;
		//shortestPath.add(dest);
//		
		while (!stack.isEmpty()) {
			p1 = stack.pop();
			
			if (getAdjList(spaceMap, p1).contains(p2)) {
				shortestPath.add(0,p1);
				p2 = p1;
				if (p2.equals(src))
					break;
			}
			
		}
		
		return shortestPath;
		
	}

}
