import java.util.List;

import javafx.geometry.Point2D;

public class Asteroid implements Debris {
	private Point2D position;
	Direction direction;
	AsteroidSprite sprite;
	
	public Asteroid(Point2D start, Direction direction, AsteroidSprite sprite) {
		position = start;
		this.direction = direction;
		
		this.sprite = sprite;
	}
	
	public Point2D getPosition() {
		return position;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
		
		if(sprite != null) sprite.setPosition(position);
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
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
		
		if(sprite != null) sprite.setPosition(position);
	}

	public List<Debris> getAsteroids(){
		return null;
	}
	
	public AsteroidSprite getSprite() {
		return sprite;
	}

	public void addAsteroid(Debris asteroid) {
		//Do nothing
	}

}
