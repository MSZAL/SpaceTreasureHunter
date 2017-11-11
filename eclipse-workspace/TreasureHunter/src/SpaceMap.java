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
	
	//Get singleton
	public static SpaceMap getInstance() {
		if(instance == null)
			instance = new SpaceMap();
		
		return instance;
	}
	
	
	//Create Inhabitant array and populate it with planets
	public void buildMap(int dimensions, int planetCount) {
		grid = new Inhabitant[dimensions][dimensions];
		Arrays.fill(grid, Inhabitant.EMPTY); //Initialize to be empty

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
		int x = (int)location.getX();
		int y = (int)location.getY();
		
		grid[x][y] = type;
	}
	
	//Get Inhabitant at given square
	public Inhabitant getInhabitant(Point2D location) {
		int x = (int)location.getX();
		int y = (int)location.getY();
		
		return grid[x][y];
	}
}
