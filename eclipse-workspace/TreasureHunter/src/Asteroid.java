import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Asteroid implements Debris {
	final String imagePath = "/asteroid.png";
	private Point2D position;
	Direction direction;
	ImageView image;
	
	private final int SCALE = 10;
	
	public Asteroid(Point2D start, Direction direction) {
		position = start;
		this.direction = direction;
		
		Image picture = new Image(imagePath, 1 * SCALE, 1 * SCALE, true, true);
		image = new ImageView(picture);
		image.setX(position.getX() * SCALE);
		image.setY(position.getY() * SCALE);
	}
	
	public Point2D getPosition() {
		return position;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
		
		image.setX(position.getX() * SCALE);
		image.setY(position.getY() * SCALE);
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
		
		image.setX(position.getX() * SCALE);
		image.setY(position.getY() * SCALE);
	}

	public List<Debris> getAsteroids(){
		return null;
	}
	
	public ImageView getImageView() {
		return image;
	}

	public void addAsteroid(Debris asteroid) {
		//Do nothing
	}

}
