import javafx.geometry.Point2D;

public abstract class Ship {
	
	private Point2D position;
	
	public Ship(Point2D position) {
		this.position = position;
	}
	
	public void moveUp() {
		position = new Point2D(position.getX(),position.getY() - 1);
	}
	
	public void moveDown() {
		position = new Point2D(position.getX(),position.getY() + 1);
	}
	
	public void moveLeft() {
		position = new Point2D(position.getX() - 1,position.getY());
	}
	
	public void moveRight() {
		position = new Point2D(position.getX() + 1,position.getY());
	}
	
	public Point2D getPosition() {
		return position;
	}
	
	public abstract String getImagePath();
}
