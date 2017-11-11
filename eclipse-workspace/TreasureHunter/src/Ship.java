import javafx.geometry.Point2D;

public interface Ship {	
	public void moveUp();
	
	public void moveDown();
	
	public void moveLeft();
	
	public void moveRight();
	
	public Point2D getPosition();
	
	public abstract String getImagePath();
}
