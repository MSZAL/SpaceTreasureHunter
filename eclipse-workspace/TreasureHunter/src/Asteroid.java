import javafx.geometry.Point2D;

public class Asteroid implements Debris {
	final String imagePath = "/asteroid.png";
	private Point2D position;
	Direction direction;
	
	public Asteroid(Point2D start, Direction direction) {
		position = start;
		this.direction = direction;
	}
	
	public Point2D getPosition() {
		return position;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
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
	}
	
	public int getWidth() {
		return 1;
	}
	public int getHeight() {
		return 1;
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void addAsteroid(Debris asteroid) {
		//Do nothing
	}

}
