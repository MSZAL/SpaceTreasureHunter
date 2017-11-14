import java.util.ArrayList;

import javafx.geometry.Point2D;

public class TrackBehavior implements Behavior {

	Ship enemy;
	Ship player;
	
	public TrackBehavior(Ship enemy, Ship player) {
		this.enemy = enemy;
		this.player = player;
	}
	//Returns the next closest move to Spaceship
	
	@Override
	public Point2D nextMove() {
		
		Point2D playerPosition = player.getPosition();
		Point2D enemyPosition = enemy.getPosition();
		
		//Does a BFS search and establishes path for alien. Alien takes next move in that array
		
		ArrayList<Point2D> path = ShortestPath.bfs(enemyPosition, playerPosition);
		int i = 1;
		
		if (enemyPosition.getY() == playerPosition.getY() && enemyPosition.getX() != playerPosition.getX()) {
			for (int j = 0; j < path.size(); j++) {
				if (path.get(j).getY() > playerPosition.getY() || path.get(j).getY() < playerPosition.getY()) {
					i = j + 1;
					if (path.size() <= i) {
						i--;
					}
					path.set(i,new Point2D(path.get(i).getX(), playerPosition.getY()));
					break;
				}
			}
		}
		
		return path.get(i);
	}

}
