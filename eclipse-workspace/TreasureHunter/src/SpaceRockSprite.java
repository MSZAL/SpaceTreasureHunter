import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SpaceRockSprite extends AsteroidSprite {
	
	private final String imagePath = "/asteroid.png";

	public SpaceRockSprite() {
		this.rotateable = true;
		
		Image picture = new Image(imagePath, 1 * scale, 1 * scale, true, true);
		image = new ImageView(picture);
	}
	
}
