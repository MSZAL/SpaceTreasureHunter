import java.util.List;

import javafx.geometry.Point2D;

enum Direction{
	DOWN,
	UP,
	LEFT,
	RIGHT
}

public interface Debris {

	void setPosition(Point2D position);
	Point2D getPosition();
	void move();
	
	void reset(int offset);
	
	void setDirection(Direction direction);

	AsteroidSprite getSprite();
	
	List<Debris> getAsteroids();

	void addAsteroid(Debris asteroid);
}
