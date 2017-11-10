import javafx.geometry.Point2D;
import java.util.Arrays;
import java.util.Random;

enum Inhabitant{
	EMPTY, 
	PLANET, 
	ASTEROID, 
	PLAYER, 
	ALIEN,
	TREASURE
}

public class SpaceMap {
	private static SpaceMap instance;
	private Inhabitant[][] grid;
	
	private SpaceMap() {}
	
	public static SpaceMap getInstance() {
		if(instance == null)
			instance = new SpaceMap();
		
		return instance;
	}
	
	public void buildMap(int dimensions, int planetCount) {
		grid = new Inhabitant[dimensions][dimensions];
		Arrays.fill(grid, Inhabitant.EMPTY); //Initialize to be empty

		//Add planets
		for (int i = planetCount; i > 0;){
			Random rando = new Random();
			int randoX = rando.nextInt(dimensions);
			int randoY = rando.nextInt(dimensions);
			
			//Check if planet is already present, if not add it
			if(isPointSafe(new Point2D(randoX, randoY))) {
				grid[randoX][randoY] = Inhabitant.PLANET;
				i--;
			}
		}
	}
	
	public void setPosition(Inhabitant type, Point2D location) {
		
	}
	
	//Check if obstruction is present
	public boolean isPointSafe(Point2D point){
		int x = (int)point.getX();
		int y = (int)point.getY();

		boolean isSafe = false;

		if(x >= 0 && y >= 0 && x < grid.length && y < grid[0].length)
			isSafe = grid[x][y].equals(Inhabitant.EMPTY);

		return isSafe;
	}
}
