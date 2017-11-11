import javafx.geometry.Point2D;

public interface Debris {

	Point2D getLocation();
	int getWidth();
	int getHeight();

	void move(int dx, int dy);
	String getImage();

	void addAsteroid(Debris asteroid);
}
