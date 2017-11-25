import java.util.List;
import java.util.LinkedList;

import javafx.geometry.Point2D;

public class AsteroidCluster implements Debris {
	private Point2D startPosition;
	private Point2D position;
	List<Debris> asteroids = new LinkedList<Debris>();
	Direction direction;
	
	public AsteroidCluster(Point2D start, Direction direction) {
		startPosition = start;
		position = start;
		this.direction = direction;
	}
	
	public Point2D getPosition() {
		return position;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
		this.startPosition = position;
	}

	public void move() {
		//Check direction of movement and calculate new position
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
	
	//Reset the position to the start with an offset value
	public void reset(int offset) {
		if(direction.equals(Direction.UP) || direction.equals(Direction.DOWN))
			position = new Point2D(offset, startPosition.getY());	
		else if(direction.equals(Direction.LEFT) || (direction.equals(Direction.RIGHT)))
			position = new Point2D(startPosition.getX(), offset);
		
		for(Debris asteroid : asteroids) {
			asteroid.reset(offset);
		}
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void addAsteroid(Debris asteroid) {
		asteroid.setDirection(direction);
		asteroids.add(asteroid);
	}
	
	public List<Debris> getAsteroids() {
		return asteroids;
	}
	
	public AsteroidSprite getSprite() {
		return null; //No image for clusters
	}
}
