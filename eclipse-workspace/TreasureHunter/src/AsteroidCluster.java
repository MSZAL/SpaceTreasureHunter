import java.util.List;
import java.util.LinkedList;

import javafx.geometry.Point2D;

public class AsteroidCluster implements Debris {
	private Point2D location;
	List<Debris> asteroids = new LinkedList<Debris>();
	int width;
	int height;
	
	public AsteroidCluster(Point2D start, int width, int height) {
		location = start;
		this.width = width;
		this.height = height;
	}
	
	public Point2D getLocation() {
		return location;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	public void move(int dx, int dy) {
		location.add(dx, dy);
		for(Debris asteroid : asteroids) {
			asteroid.move(dx, dy);
		}
	}

	public void addAsteroid(Debris asteroid) {
		asteroids.add(asteroid);
	}
	
	public String getImage() {
		return ""; //No image for clusters
	}
}
