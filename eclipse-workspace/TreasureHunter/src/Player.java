import java.io.File;

import javafx.geometry.Point2D;

public class Player extends Ship{
	int lives;
	
	public Player(Point2D start, int lives) {
		super(start);
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

}
