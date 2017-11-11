import java.io.File;

import java.util.Observable;
import javafx.geometry.Point2D;

public class Player extends Observable implements Ship{
	int lives;
	Point2D position;
	
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
		//Get current system file path and append link to image
		String filePath = new File("").getAbsolutePath();
		filePath = filePath.concat("/img/player.png");
		return filePath;
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
