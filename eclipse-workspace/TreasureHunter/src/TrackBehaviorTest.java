
import static org.junit.Assert.*;

import org.junit.Test;
import javafx.geometry.Point2D;



public class TrackBehaviorTest {

	//Player is on below Alien  => Alien moves down
	
	@Test
	public void testNextMoveSimulation () {
		SpaceMap spaceMap = SpaceMap.getInstance();
		spaceMap.buildMap(100, 0);
		
		Player player = new Player(new Point2D(1,0), 0);
		Enemy enemy = new Enemy(player, new Point2D(0,0));
		
		TrackBehavior track = new TrackBehavior(enemy,player);
		enemy.setBehavior(track);
		
		Point2D nextMove = track.nextMove();
		assertEquals(new Point2D(1,0), nextMove);
	}
	
	//Player is above Alien => Alien moves up
	
	@Test
	public void testNextMoveSimulation2 () {
		SpaceMap spaceMap = SpaceMap.getInstance();
		spaceMap.buildMap(100, 0);
		
		Player player = new Player(new Point2D(0,0), 0);
		Enemy enemy = new Enemy(player, new Point2D(1,0));
		
		TrackBehavior track = new TrackBehavior(enemy,player);
		enemy.setBehavior(track);
		
		Point2D nextMove = track.nextMove();
		assertEquals(new Point2D(0,0), nextMove);
	}
	
	//Player is to the right of Alien => Alien moves right
	
	@Test
	public void testNextMoveSimulation3 () {
		SpaceMap spaceMap = SpaceMap.getInstance();
		spaceMap.buildMap(100, 0);
		
		Player player = new Player(new Point2D(20,30), 0);
		Enemy enemy = new Enemy(player, new Point2D(20,20));
		
		TrackBehavior track = new TrackBehavior(enemy,player);
		enemy.setBehavior(track);
		
		Point2D nextMove = track.nextMove();
		assertEquals(new Point2D(20,21), nextMove);
	}
	
	//Player is to the left of Alien => Alien moves right
	
	@Test
	public void testNextMoveSimulation4 () {
		SpaceMap spaceMap = SpaceMap.getInstance();
		spaceMap.buildMap(100, 0);
		
		Player player = new Player(new Point2D(20,10), 0);
		Enemy enemy = new Enemy(player, new Point2D(20,20));
		
		TrackBehavior track = new TrackBehavior(enemy,player);
		enemy.setBehavior(track);
		
		Point2D nextMove = track.nextMove();
		assertEquals(new Point2D(20,19), nextMove);
	}
	
	//Planet in between alien & spaceship => Alien will choose to go around
	
	@Test
	public void testNextMoveSimulation5 () {
		SpaceMap spaceMap = SpaceMap.getInstance();
		spaceMap.buildMap(100, 0);
		
		Player player = new Player(new Point2D(20,22), 0);
		Enemy enemy = new Enemy(player, new Point2D(20,20));
		
		spaceMap.setInhabitant(Inhabitant.PLANET, new Point2D(20,21));
		spaceMap.setInhabitant(Inhabitant.PLANET, new Point2D(19,21));
		
		TrackBehavior track = new TrackBehavior(enemy,player);
		enemy.setBehavior(track);
		
		Point2D nextMove = track.nextMove();
		assertEquals(new Point2D(21,20), nextMove);
	}
	
}
