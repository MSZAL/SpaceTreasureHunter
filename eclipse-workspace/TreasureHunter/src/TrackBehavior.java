import java.util.ArrayList;

import javafx.geometry.Point2D;

public class TrackBehavior implements Behavior {

	Enemy enemy;
	Player player;
	SpaceMap spaceMap;
	
	public TrackBehavior(Enemy enemy, Player player) {
		this.enemy = enemy;
		this.player = player;
		this.spaceMap = SpaceMap.getInstance();
	}
	//Returns the next closest move to Spaceship
	
	@Override
	public Point2D nextMove() {
		Point2D playerPosition = player.getPosition();
		Point2D enemyPosition = enemy.getPosition(); 
		
		if (playerPosition.equals(spaceMap.getTreasure())) return null;
		if (playerPosition.equals(enemyPosition)) return null;
		
		//Does a BFS search and establishes path for alien. Alien takes next move in that array
		
		ArrayList<Point2D> path = ShortestPath.findShortestPath(enemyPosition, playerPosition);
		
		return path.get(0);
	}

}
