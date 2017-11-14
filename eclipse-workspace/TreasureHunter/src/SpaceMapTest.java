import static org.junit.Assert.*;

import org.junit.Test;

import javafx.geometry.Point2D;

public class SpaceMapTest {

	@Test
	public void testSetInhabitantHqppy() {
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
	public void testSetInhabitantSad() {
		SpaceMap map = SpaceMap.getInstance();
		map.buildMap(25, 0);
		
		Point2D nullSpot = new Point2D(2, 2);
		map.setInhabitant(null, nullSpot);
		
		assertEquals(map.getInhabitant(nullSpot), Inhabitant.EMPTY);
		
		map.setInhabitant(Inhabitant.ALIEN, null);
		for(int i = 0; i < map.getDimensions(); i++) {
			for (int j = 0; j < map.getDimensions(); j++) {
				Point2D mapSpot = new Point2D(i, j);
				assertTrue(map.getInhabitant(mapSpot).equals(Inhabitant.EMPTY) || map.getInhabitant(mapSpot).equals(Inhabitant.TREASURE));
			}
		}
	}
	
	@Test
	public void testIsOnMapSad() {
		SpaceMap map = SpaceMap.getInstance();
		map.buildMap(25, 0);
		
		//Top left
		assertEquals(map.isOnMap(new Point2D(-1, -1)), false);
		assertEquals(map.isOnMap(new Point2D(0, -1)), false);
		assertEquals(map.isOnMap(new Point2D(-1, 0)), false);
		
		//Top right
		assertEquals(map.isOnMap(new Point2D(25, 0)), false);
		assertEquals(map.isOnMap(new Point2D(25, -1)), false);
		assertEquals(map.isOnMap(new Point2D(24, -1)), false);
		
		//Bottom right
		assertEquals(map.isOnMap(new Point2D(24, 25)), false);
		assertEquals(map.isOnMap(new Point2D(25, 25)), false);
		assertEquals(map.isOnMap(new Point2D(25, 24)), false);
		
		//Bottom right
		assertEquals(map.isOnMap(new Point2D(0, 25)), false);
		assertEquals(map.isOnMap(new Point2D(-1, 25)), false);
		assertEquals(map.isOnMap(new Point2D(-1, 24)), false);
	}
	
	@Test
	public void testIsOnMapHappy() {
		SpaceMap map = SpaceMap.getInstance();
		map.buildMap(25, 0);
		
		//Top left
		assertEquals(map.isOnMap(new Point2D(0, 0)), true);
		
		//Top right
		assertEquals(map.isOnMap(new Point2D(24, 0)), true);
		
		//Bottom right
		assertEquals(map.isOnMap(new Point2D(24, 24)), true);
		
		//Bottom right
		assertEquals(map.isOnMap(new Point2D(0, 24)), true);
		
		//Middle point
		assertEquals(map.isOnMap(new Point2D(25/2, 25/2)), true);
		
	}

}
