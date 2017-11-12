import java.util.ArrayList;

import javafx.geometry.Point2D;

public class TrackBehavior implements Behavior {

	Ship enemy;
	Ship player;
	
	public TrackBehavior(Ship enemy, Ship player) {
		this.enemy = enemy;
		this.player = player;
	}
	
	@Override
	public Point2D nextMove() {
		
		Point2D playerPosition = player.getPosition();
		Point2D enemyPosition = enemy.getPosition();
		
		ArrayList<Point2D> path = ShortestPath.bfs(enemyPosition, playerPosition);
		
		return path.get(0);
	}

}
