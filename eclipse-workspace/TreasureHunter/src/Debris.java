import javafx.geometry.Point2D;

public interface Debris {

	Point2D getPosition();
	int getWidth();
	int getHeight();

	void move(int dx, int dy);
	String getImagePath();

	void addAsteroid(Debris asteroid);
}
