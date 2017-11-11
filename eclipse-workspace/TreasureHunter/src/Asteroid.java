import java.io.File;

import javafx.geometry.Point2D;

public class Asteroid implements Debris {
	private Point2D position;
	int width;
	int height;
	
	public Asteroid(Point2D start, int width, int height) {
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
	}
	
	public String getImagePath() {
		//Get current system file path and append link to image
		String filePath = new File("").getAbsolutePath();
		filePath = filePath.concat("/img/asteroid.png");
		return filePath;
	}

	public void addAsteroid(Debris asteroid) {
		//Do nothing
	}

}
