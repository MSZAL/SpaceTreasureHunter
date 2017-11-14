
import static org.junit.Assert.*;

import org.junit.Test;
import javafx.geometry.Point2D;



public class TrackBehaviorTest {

	//Player is on below Alien  => Alien moves down
	
	@Test
	public void testMoveDownSimulation () {
		SpaceMap spaceMap = SpaceMap.getInstance();
		spaceMap.buildMap(100, 0);
		
		Player player = new Player(new Point2D(3,0), 0);
		Enemy enemy = new Enemy(player, new Point2D(0,0));
		
		TrackBehavior track = new TrackBehavior(enemy,player);
		enemy.setBehavior(track);
		
		
		assertEquals(new Point2D(1,0), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(2,0), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(3,0), track.nextMove());
	}
	
	//Player is above Alien => Alien moves up
	
	@Test
	public void testNextMoveUpSimulation () {
		SpaceMap spaceMap = SpaceMap.getInstance();
		spaceMap.buildMap(100, 0);
		
		Player player = new Player(new Point2D(0,0), 0);
		Enemy enemy = new Enemy(player, new Point2D(3,0));
		
		TrackBehavior track = new TrackBehavior(enemy,player);
		enemy.setBehavior(track);
		
		assertEquals(new Point2D(2,0), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(1,0), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(0,0), track.nextMove());
	}
	
	//Player is to the right of Alien => Alien moves right
	
	@Test
	public void testNextMoveRightSimulation () {
		SpaceMap spaceMap = SpaceMap.getInstance();
		spaceMap.buildMap(100, 0);
		
		Player player = new Player(new Point2D(20,30), 0);
		Enemy enemy = new Enemy(player, new Point2D(20,20));
		
		TrackBehavior track = new TrackBehavior(enemy,player);
		enemy.setBehavior(track);
		
		assertEquals(new Point2D(20,21), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(20,22), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(20,23), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(20,24), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(20,25), track.nextMove());
		
	}
	
	//Player is to the left of Alien => Alien moves right
	
	@Test
	public void testNextMoveLeftSimulation () {
		SpaceMap spaceMap = SpaceMap.getInstance();
		spaceMap.buildMap(100, 0);
		
		Player player = new Player(new Point2D(20,10), 0);
		Enemy enemy = new Enemy(player, new Point2D(20,20));
		
		TrackBehavior track = new TrackBehavior(enemy,player);
		enemy.setBehavior(track);
		
		assertEquals(new Point2D(20,19), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(20,18), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(20,17), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(20,16), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(20,15), track.nextMove());
	}
	
	//Planet in between alien & spaceship => Alien will choose to go around
	
	@Test
	public void testNextMoveAroundSimulation () {
		SpaceMap spaceMap = SpaceMap.getInstance();
		spaceMap.buildMap(100, 0);
		
		Player player = new Player(new Point2D(20,10), 0);
		Enemy enemy = new Enemy(player, new Point2D(19,11));
		
		spaceMap.setInhabitant(Inhabitant.PLANET, new Point2D(19,10));
		
		TrackBehavior track = new TrackBehavior(enemy,player);
		enemy.setBehavior(track);
		
		assertEquals(new Point2D(20,11), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(20,10), track.nextMove());
	}
	
	@Test
	public void testNextMoveAroundSimulation2 () {
		SpaceMap spaceMap = SpaceMap.getInstance();
		spaceMap.buildMap(100, 0);
		
		Player player = new Player(new Point2D(20,10), 0);
		Enemy enemy = new Enemy(player, new Point2D(18,10));
		
		spaceMap.setInhabitant(Inhabitant.PLANET, new Point2D(18,9));
		spaceMap.setInhabitant(Inhabitant.PLANET, new Point2D(19,10));
		
		TrackBehavior track = new TrackBehavior(enemy,player);
		enemy.setBehavior(track);
		
		assertEquals(new Point2D(18,11), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(19,11), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(20,11), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(20,10), track.nextMove());
	}
	
	@Test
	public void testNextMoveAroundSimulation3 () {
		SpaceMap spaceMap = SpaceMap.getInstance();
		spaceMap.buildMap(100, 0);
		
		Player player = new Player(new Point2D(18,10), 0);
		Enemy enemy = new Enemy(player, new Point2D(20,10));
		
		spaceMap.setInhabitant(Inhabitant.PLANET, new Point2D(18,9));
		spaceMap.setInhabitant(Inhabitant.PLANET, new Point2D(19,10));
		
		TrackBehavior track = new TrackBehavior(enemy,player);
		enemy.setBehavior(track);
		
		assertEquals(new Point2D(20,11), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(19,11), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(18,11), track.nextMove());
		enemy.move();
		assertEquals(new Point2D(18,10), track.nextMove());
	}
	
}
