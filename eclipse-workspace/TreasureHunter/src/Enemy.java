import java.util.Observable;
import java.util.Observer;

import javax.annotation.PostConstruct;

import javafx.geometry.Point2D;

public class Enemy implements Ship, Observer{
	
	Behavior behavior;
	Point2D position;
	String imagePath = "";
	
	public Enemy (Player player, Point2D position) {
		player.addObserver(this);
		this.position = position;	
	}
	
	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}
	
	public void move() {
		if (behavior != null) {
			Point2D p = behavior.nextMove();
			
			if (p.getX() > position.getX()) moveRight();
			if (p.getX() < position.getX()) moveLeft();
			if (p.getY() > position.getY()) moveDown();
			if (p.getY() < position.getY()) moveUp();
		}
	}

	@Override
	public void moveUp() {
		position = new Point2D(position.getX(), position.getY() - 1);	
	}

	@Override
	public void moveDown() {
		position = new Point2D(position.getX(), position.getY() + 1);
	}

	@Override
	public void moveLeft() {
		position = new Point2D(position.getX() - 1, position.getY());
	}

	@Override
	public void moveRight() {
		position = new Point2D(position.getX() + 1, position.getY());
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public String getImagePath() {
		return imagePath;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Player) {
			move();
		}
	}

}
