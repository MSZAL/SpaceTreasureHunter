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
	private Point2D treasure;
		
	private SpaceMap() {}
	
	private int dimensions = 0;
	
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
		
		Random rando = new Random();
		int randX = rando.nextInt(dimensions);
		int randY = rando.nextInt(dimensions);
		this.treasure = new Point2D(randX,randY);
		grid[randX][randY] = Inhabitant.TREASURE;
		

		//Add planets
		for (int i = planetCount; i > 0;){
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
		if (location == null || type == null)
			return;
		
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
		
		if (!isOnMap(location))
			return null;
		
		return grid[x][y];
	}
	
	public boolean isOnMap(Point2D tester) {
		int x = (int)tester.getX();
		int y = (int)tester.getY();
		
		return !(x >= dimensions || x < 0 || y >= dimensions || y < 0);
	}
	
	public Point2D getTreasure() {
		return treasure;
	}
	
	public int getDimensions() {
		return dimensions;
	}
}
