import javafx.geometry.Point2D;

import javafx.scene.image.ImageView;

public abstract class AsteroidSprite {
	
	ImageView image;
	boolean rotateable;
	int scale = Game.GRID_SIZE;
	
	private int rotation = 0;
	
	public void setPosition(Point2D position) {
		image.setX(position.getX() * scale);
		image.setY(position.getY());
		
		if(rotateable) {
			rotation += 10;
			image.setRotate(rotation);
		}
	}
	

}
