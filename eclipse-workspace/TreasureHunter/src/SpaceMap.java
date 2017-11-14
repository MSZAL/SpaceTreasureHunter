import javafx.geometry.Point2D;
import java.util.Arrays;
import java.util.Random;

enum Inhabitant{
	EMPTY, 
	PLANET, 
	ASTEROID,
	ALIEN,
	TREASURE,
	PLAYER
}

public class SpaceMap {
	private static SpaceMap instance;
	private Inhabitant[][] grid;
	
	private SpaceMap() {}
	
	int dimensions = 0;
	
	//Get singleton
	public static SpaceMap getInstance() {
		if(instance == null)
			instance = new SpaceMap();
		
		return instance;
	}
	
	
	//Create Inhabitant array and populate it with planets
	public void buildMap(int dimensions, int planetCount) {
		this.dimensions = dimensions;
		grid = new Inhabitant[dimensions][dimensions];
		for (int i = 0; i < dimensions; i++) {
			Arrays.fill(grid[i], Inhabitant.EMPTY); //Initialize to be empty
		}

		//Add planets
		for (int i = planetCount; i > 0;){
			Random rando = new Random();
			int randoX = rando.nextInt(dimensions);
			int randoY = rando.nextInt(dimensions);
			
			//Check if planet is already present, if not add it and decrement accumulator
			if(getInhabitant(new Point2D(randoX, randoY)).equals(Inhabitant.EMPTY)) {
				grid[randoX][randoY] = Inhabitant.PLANET;
				i--;
			}
		}
	}
	
	//Set Inhabitant at given square
	public void setInhabitant(Inhabitant type, Point2D location) {
		if(isOnMap(location)) {
			int x = (int)location.getX();
			int y = (int)location.getY();
		
			grid[x][y] = type;
		}
	}
	
	//Get Inhabitant at given square
	public Inhabitant getInhabitant(Point2D location) {
		if (location == null)
			return null;
		
		int x = (int)location.getX();
		int y = (int)location.getY();
		
		if (x >= Game.GRID_SIZE || x < 0 || y >= Game.GRID_SIZE || y < 0)
			return null;
		
		return grid[x][y];
	}
	
	public boolean isOnMap(Point2D tester) {
		return !(tester.getX() < 0 || tester.getX() >= dimensions || tester.getY() < 0 || tester.getY() >= dimensions);
	}
	
	public int getDimensions() {
		return dimensions;
	}
}
