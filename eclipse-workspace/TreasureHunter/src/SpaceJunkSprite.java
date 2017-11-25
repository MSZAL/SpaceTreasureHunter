import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SpaceJunkSprite extends AsteroidSprite {
	
	private final String imagePath = "/spacejunk.png";

	public SpaceJunkSprite() {
		this.rotateable = false;
		
		Image picture = new Image(imagePath, 1 * scale, 1 * scale, true, true);
		image = new ImageView(picture);
	}
}

