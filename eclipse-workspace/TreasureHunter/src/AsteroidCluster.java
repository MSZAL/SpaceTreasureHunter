import java.util.List;
import java.util.LinkedList;

import javafx.geometry.Point2D;

public class AsteroidCluster implements Debris {
	private Point2D position;
	List<Debris> asteroids = new LinkedList<Debris>();
	int width;
	int height;
	
	public AsteroidCluster(Point2D start, int width, int height) {
		position = start;
		this.width = width;
		this.height = height;
	}
	
	public Point2D getPosition() {
		return position;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	public void move(int dx, int dy) {
		position = position.add(dx, dy);
		for(Debris asteroid : asteroids) {
			asteroid.move(dx, dy);
		}
	}

	public void addAsteroid(Debris asteroid) {
		asteroids.add(asteroid);
	}
	
	public String getImagePath() {
		return ""; //No image for clusters
	}
}
