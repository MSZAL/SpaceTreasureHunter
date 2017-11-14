import java.awt.Dimension;
import java.util.Observable;
import javafx.geometry.Point2D;

public class Player extends Observable implements Ship{
	int lives;
	Point2D position;
	String imagePath = "/spaceship.png";
	
	public Player(Point2D start, int lives) {
		position = start;
		this.lives = lives;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void deductLife() {
		lives--;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void moveUp() {
		if (position.getY() - 1 >= 0) {
			position = new Point2D(position.getX(),position.getY() - 1);
		
			setChanged();
			notifyObservers();
		}
	}
	
	public void moveDown() {
		if (position.getY() + 1 < Game.GRID_SIZE) {
			position = new Point2D(position.getX(),position.getY() + 1);
			
			setChanged();
			notifyObservers();
		}
	}
	
	public void moveLeft() {
		if (position.getX() - 1 >= 0) {
			position = new Point2D(position.getX() - 1,position.getY());
			
			setChanged();
			notifyObservers();
		}
	}
	
	public void moveRight() {
		if (position.getX() + 1 < Game.GRID_SIZE) {
			position = new Point2D(position.getX() + 1,position.getY());
			
			setChanged();
			notifyObservers();
		}
	}
	
	public Point2D getPosition() {
		return position;
	}



}
