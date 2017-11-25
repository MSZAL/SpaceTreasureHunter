import static org.junit.Assert.*;

import org.junit.Test;

import javafx.geometry.Point2D;

public class AsteroidTest {

	@Test
	public void testMoveUp() {
		Asteroid asteroid = new Asteroid(new Point2D(0, 100), Direction.UP, null);
		
		//Test moving once
		asteroid.move();
		assertEquals(new Point2D(0, 99), asteroid.getPosition());
		
		//Test moving four more times
		asteroid.move();
		asteroid.move();
		asteroid.move();
		asteroid.move();
		
		assertEquals(new Point2D(0, 95), asteroid.getPosition());	
	}
	
	@Test
	public void testMoveDown() {
		Asteroid asteroid = new Asteroid(new Point2D(0, 0), Direction.DOWN, null);
		
		//Test moving once
		asteroid.move();
		assertEquals(new Point2D(0, 1), asteroid.getPosition());
		
		//Test moving four more times
		asteroid.move();
		asteroid.move();
		asteroid.move();
		asteroid.move();
		
		assertEquals(new Point2D(0, 5), asteroid.getPosition());
	}
	
	
	@Test
	public void testMoveLeft() {
		Asteroid asteroid = new Asteroid(new Point2D(100, 0), Direction.LEFT, null);
		
		//Test moving once
		asteroid.move();
		assertEquals(new Point2D(99, 0), asteroid.getPosition());
		
		//Test moving four more times
		asteroid.move();
		asteroid.move();
		asteroid.move();
		asteroid.move();
		
		assertEquals(new Point2D(95, 0), asteroid.getPosition());	
	}
	
	@Test
	public void testMoveRight() {
		Asteroid asteroid = new Asteroid(new Point2D(0, 0), Direction.RIGHT, null);
		
		//Test moving once
		asteroid.move();
		assertEquals(new Point2D(1, 0), asteroid.getPosition());
		
		//Test moving four more times
		asteroid.move();
		asteroid.move();
		asteroid.move();
		asteroid.move();
		
		assertEquals(new Point2D(5, 0), asteroid.getPosition());	
	}
	
	@Test
	public void testChangeDirection() {
		//Initially check right movement
		Asteroid asteroid = new Asteroid(new Point2D(50, 50), Direction.RIGHT, null);
		asteroid.move();
		assertEquals(new Point2D(51, 50), asteroid.getPosition());
		
		//Test changing to the left
		asteroid.setDirection(Direction.LEFT);
		asteroid.move();
		assertEquals(new Point2D(50, 50), asteroid.getPosition());
		
		//Test changing to upwards
		asteroid.setDirection(Direction.UP);
		asteroid.move();
		assertEquals(new Point2D(50, 49), asteroid.getPosition());
		
		//Test changing downwards
		asteroid.setDirection(Direction.DOWN);
		asteroid.move();
		assertEquals(new Point2D(50, 50), asteroid.getPosition());
		
		//Test null value
		asteroid.setDirection(null);
		asteroid.move();
		assertEquals(new Point2D(50, 51), asteroid.getPosition());
	}
	
	@Test
	public void testReset() {
		Point2D startingPosition = new Point2D(50, 50);
		//Initially check right movement
		Asteroid asteroid = new Asteroid(startingPosition, Direction.RIGHT, null);
		
		asteroid.reset(50);
		
		assertEquals(startingPosition, asteroid.getPosition());
	}
	
	@Test
	public void testResetWithNewOffset() {
		Point2D startingPosition = new Point2D(50, 50);
		
		//Initially check right movement
		Asteroid asteroid = new Asteroid(startingPosition, Direction.RIGHT, null);
		
		int testValue = 40;
		asteroid.reset(testValue);
		
		assertEquals(new Point2D(startingPosition.getX(), testValue), asteroid.getPosition());
		
		//Check left movement
		asteroid.setDirection(Direction.LEFT);
		
		testValue = 23;
		asteroid.reset(testValue);
		
		assertEquals(new Point2D(startingPosition.getX(), testValue), asteroid.getPosition());
		
		//Check upwards movement
		asteroid.setDirection(Direction.UP);
		
		testValue = 82;
		asteroid.reset(testValue);
		
		assertEquals(new Point2D(testValue, startingPosition.getY()), asteroid.getPosition());
		
		//Check downwards movement
		asteroid.setDirection(Direction.DOWN);
		
		testValue = 111;
		asteroid.reset(testValue);
		
		assertEquals(new Point2D(testValue, startingPosition.getY()), asteroid.getPosition());
	}
}
