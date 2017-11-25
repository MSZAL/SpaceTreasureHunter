import java.util.Observable;
import java.util.Observer;

import javax.annotation.PostConstruct;

import javafx.geometry.Point2D;

public class Enemy implements Observer{
	
	Behavior behavior;
	Point2D position;
	String imagePath = "/ufo.png";
	
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
			if (p != null) {
				position = p;
			}
		}
	}

	public Point2D getPosition() {
		return position;
	}

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
