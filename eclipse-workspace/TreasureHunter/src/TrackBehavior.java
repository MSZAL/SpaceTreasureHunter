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
		
		return null;
	}

}
