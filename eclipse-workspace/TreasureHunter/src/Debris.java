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
	
	void setDirection(Direction direction);
	
	int getWidth();
	int getHeight();

	String getImagePath();

	void addAsteroid(Debris asteroid);
}
