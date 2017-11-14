import static org.junit.Assert.*;

import org.junit.Test;

import javafx.geometry.Point2D;

public class SpaceMapTest {

	@Test
	public void testSetInhabitant() {
		SpaceMap map = SpaceMap.getInstance();
		map.buildMap(25, 0);
		
		System.out.println(map.getDimensions());
		
		Point2D asteroidSpot = new Point2D(2, 2);
		map.setInhabitant(Inhabitant.ASTEROID, asteroidSpot);
		
		Point2D alienSpot = new Point2D(2, 4);
		map.setInhabitant(Inhabitant.ALIEN, alienSpot);
		
		Point2D planetSpot = new Point2D(4, 4);
		map.setInhabitant(Inhabitant.PLANET, planetSpot);
		
		Point2D treasureSpot = new Point2D(5, 5);
		map.setInhabitant(Inhabitant.TREASURE, treasureSpot);
		
		assertEquals(Inhabitant.ASTEROID, map.getInhabitant(asteroidSpot));
		assertEquals(Inhabitant.ALIEN, map.getInhabitant(alienSpot));
		assertEquals(Inhabitant.PLANET, map.getInhabitant(planetSpot));
		assertEquals(Inhabitant.TREASURE, map.getInhabitant(treasureSpot));
		assertEquals(Inhabitant.EMPTY, map.getInhabitant(new Point2D(0, 0)));
		
		//Check overwrite
		map.setInhabitant(Inhabitant.EMPTY, alienSpot);
		assertEquals(Inhabitant.EMPTY, map.getInhabitant(alienSpot));
	}
	
	@Test
	public void testIsOnMap() {
		SpaceMap map = SpaceMap.getInstance();
		map.buildMap(25, 0);
		
		//Top left
		assertEquals(map.isOnMap(new Point2D(0, 0)), true);
		assertEquals(map.isOnMap(new Point2D(-1, -1)), false);
		
		//Top right
		assertEquals(map.isOnMap(new Point2D(24, 0)), true);
		assertEquals(map.isOnMap(new Point2D(25, 0)), false);
		assertEquals(map.isOnMap(new Point2D(25, -1)), false);
		assertEquals(map.isOnMap(new Point2D(24, -1)), false);
		
		//Bottom right
		assertEquals(map.isOnMap(new Point2D(24, 24)), true);
		assertEquals(map.isOnMap(new Point2D(25, 25)), false);
		
		//Bottom right
		assertEquals(map.isOnMap(new Point2D(0, 24)), true);
		assertEquals(map.isOnMap(new Point2D(0, 25)), false);
		assertEquals(map.isOnMap(new Point2D(-1, 25)), false);
		assertEquals(map.isOnMap(new Point2D(-1, 24)), false);
		
		//Middle point
		assertEquals(map.isOnMap(new Point2D(25/2, 25/2)), true);
		
	}

}
