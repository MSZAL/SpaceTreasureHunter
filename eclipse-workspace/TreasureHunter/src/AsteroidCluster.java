import java.util.List;
import java.util.LinkedList;

import javafx.geometry.Point2D;

public class AsteroidCluster implements Debris {
	private Point2D position;
	List<Debris> asteroids = new LinkedList<Debris>();
	int width;
	int height;
	Direction direction;
	
	public AsteroidCluster(Point2D start, Direction direction, int width, int height) {
		position = start;
		this.width = width;
		this.height = height;
		this.direction = direction;
	}
	
	public Point2D getPosition() {
		return position;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	public void move() {
		if(direction.equals(Direction.UP))
			position = new Point2D(position.getX(), position.getY() - 1);	
		else if (direction.equals(Direction.DOWN))
			position = new Point2D(position.getX(), position.getY() + 1);
		else if(direction.equals(Direction.LEFT))
			position = new Point2D(position.getX() - 1, position.getY());
		else if(direction.equals(Direction.RIGHT))
			position = new Point2D(position.getX() + 1, position.getY());
			
		for(Debris asteroid : asteroids) {
			asteroid.move();
		}
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void addAsteroid(Debris asteroid) {
		asteroid.setDirection(direction);
		asteroids.add(asteroid);
	}
	
	public String getImagePath() {
		return ""; //No image for clusters
	}
}
