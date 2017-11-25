import java.util.Observable;
import javafx.geometry.Point2D;

public class Player extends Observable {
	Point2D position;
	String imagePath = "/spaceship.png";
	
	public Player(Point2D start) {
		position = start;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void moveUp() {
		position = new Point2D(position.getX(),position.getY() - 1);
		
		setChanged();
		notifyObservers();
	}
	
	public void moveDown() {
		position = new Point2D(position.getX(),position.getY() + 1);
			
		setChanged();
		notifyObservers();
	}
	
	public void moveLeft() {
		position = new Point2D(position.getX() - 1,position.getY());
			
		setChanged();
		notifyObservers();
	}
	
	public void moveRight() {
		position = new Point2D(position.getX() + 1,position.getY());
			
		setChanged();
		notifyObservers();
	}
	
	public Point2D getPosition() {
		return position;
	}



}
