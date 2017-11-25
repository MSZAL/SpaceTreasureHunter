
public class AsteroidBuilder {
	
	public AsteroidSprite buildAsteroid(String type) {
		if(type.equals("SpaceRock")) {
			return new SpaceRockSprite();
		} else if(type.equals("SpaceJunk")) {
			return new SpaceJunkSprite();
		}
		
		return null;
	}

}
