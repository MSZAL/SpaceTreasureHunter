import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

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

	ImageView getImageView();
	
	List<Debris> getAsteroids();

	void addAsteroid(Debris asteroid);
}
