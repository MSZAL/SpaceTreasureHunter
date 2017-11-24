import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game extends Application {
	
	public static final int GRID_SIZE = 25;
	
	public static final int PLANET_COUNT = 10;
	
	
	private final int DIMENSION = 25;
	private final int SCALE = 25;
	
	private SpaceMap spaceMap;
	
	private Player player;
	private Scene scene;
	Pane root;
	
	Thread backgroundThread;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		root = new AnchorPane();
		scene = new Scene(root,DIMENSION*SCALE,DIMENSION*SCALE);
		
		spaceMap = SpaceMap.getInstance();
		spaceMap.buildMap(GRID_SIZE, PLANET_COUNT);
		
		for (int y = 0; y < DIMENSION; y++) {
			for (int x = 0; x < DIMENSION; x++) {
				Rectangle rect = new Rectangle(x*SCALE,y*SCALE,SCALE,SCALE);
				rect.setStroke(Color.GRAY);
				Inhabitant type = spaceMap.getInhabitant(new Point2D(x,y));
				switch (type) {
				case EMPTY:
					rect.setFill(Color.BLACK);
					break;
				case PLANET:
					rect.setFill(Color.BLUE);
					break;
				case TREASURE:
					rect.setFill(Color.RED);
					break;
				default:
					rect.setFill(Color.GREEN); //Just denotes if invalid value is popping up
					break;
				}	
				root.getChildren().add(rect);
			}
		}
		
		player = new Player(new Point2D (10,0), 1);
		
		Enemy enemy = new Enemy(player, new Point2D(0,3));
		
		enemy.setBehavior(new PatrolBehavior(enemy.position));
		
		
		spaceMap.setInhabitant(Inhabitant.ALIEN, enemy.getPosition());
		
		Image playerImage = new Image(player.getImagePath(),SCALE,SCALE,true,true);
		ImageView playerImageView = new ImageView(playerImage);
		
		Image enemyImage = new Image(enemy.getImagePath(),SCALE,SCALE,true,true);
		ImageView enemyImageView = new ImageView(enemyImage);
		
		playerImageView.setX(player.getPosition().getX() * SCALE);
		playerImageView.setY(player.getPosition().getY() * SCALE);
		
		enemyImageView.setX(enemy.getPosition().getX() * SCALE);
		enemyImageView.setY(enemy.getPosition().getY() * SCALE);
		
		root.getChildren().add(playerImageView);
		root.getChildren().add(enemyImageView);	
		
		primaryStage.setTitle("Space Treasure Hunter");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				double playerX = player.getPosition().getX();
				double playerY = player.getPosition().getY();
				
				switch(event.getCode()) {
				case RIGHT:
					if (playerX + 1 < GRID_SIZE) {
						if (spaceMap.getInhabitant(new Point2D(playerX + 1, playerY)) != Inhabitant.PLANET) {
							spaceMap.setInhabitant(Inhabitant.EMPTY, enemy.getPosition());
							spaceMap.setInhabitant(Inhabitant.EMPTY, player.getPosition());
							player.moveRight();
						}
					}
					break;
				case LEFT:
					if (playerX - 1 >= 0) {
						if (spaceMap.getInhabitant(new Point2D(playerX - 1, playerY)) != Inhabitant.PLANET) {
							spaceMap.setInhabitant(Inhabitant.EMPTY, enemy.getPosition());
							spaceMap.setInhabitant(Inhabitant.EMPTY, player.getPosition());
							player.moveLeft();
						}
					}
					break;
				case UP:
					if (playerY - 1 >= 0) {
						if (spaceMap.getInhabitant(new Point2D(playerX, playerY - 1)) != Inhabitant.PLANET) {
							spaceMap.setInhabitant(Inhabitant.EMPTY, enemy.getPosition());
							spaceMap.setInhabitant(Inhabitant.EMPTY, player.getPosition());
							player.moveUp();
						}
					}
					break;
				case DOWN:
					if (playerY + 1 < GRID_SIZE) {
						if (spaceMap.getInhabitant(new Point2D(playerX, playerY + 1)) != Inhabitant.PLANET) {
							spaceMap.setInhabitant(Inhabitant.EMPTY, enemy.getPosition());
							spaceMap.setInhabitant(Inhabitant.EMPTY, player.getPosition());
							player.moveDown();
						}
					}
					break;
				default:
					break;
				}
				playerImageView.setX(player.getPosition().getX() * SCALE);
				playerImageView.setY(player.getPosition().getY() * SCALE);
				
				enemyImageView.setX(enemy.getPosition().getX() * SCALE);
				enemyImageView.setY(enemy.getPosition().getY() * SCALE);
				
				spaceMap.setInhabitant(Inhabitant.ALIEN, enemy.getPosition());
				
				checkPlayer();
				
				spaceMap.setInhabitant(Inhabitant.PLAYER, player.getPosition());
				
				checkBehavior(player, enemy);
			}
			
		};
		
		scene.setOnKeyPressed(keyHandler);
		
		//Create lone asteroid
		int starter = (int) Math.floor(Math.random() * spaceMap.getDimensions());
		Point2D asteroidSpot = new Point2D(0, starter);
		spaceMap.setInhabitant(Inhabitant.ASTEROID, asteroidSpot);
		Debris asteroid = new Asteroid(asteroidSpot, Direction.DOWN, SCALE);
		
		//Create asteroid field
		starter = (int) Math.floor(Math.random() * spaceMap.getDimensions());
		Point2D fieldSpot = new Point2D(0, starter);
		Point2D asteroid1spot = fieldSpot;
		Point2D asteroid2spot = new Point2D(1, starter + 2);
		spaceMap.setInhabitant(Inhabitant.ASTEROID, asteroid1spot);
		spaceMap.setInhabitant(Inhabitant.ASTEROID, asteroid2spot);
		
		Debris asteroidField = new AsteroidCluster(fieldSpot, Direction.RIGHT);
		Debris asteroid1 = new Asteroid(asteroid1spot, Direction.RIGHT, SCALE);
		Debris asteroid2 = new Asteroid(asteroid2spot, Direction.RIGHT, SCALE);
		
		asteroidField.addAsteroid(asteroid1);
		asteroidField.addAsteroid(asteroid2);
		
		root.getChildren().add(asteroid.getImage());
		root.getChildren().add(asteroid1.getImage());
		root.getChildren().add(asteroid2.getImage());
		
		//Create thread for asteroids
		backgroundThread = new Thread("AsteroidThread") {
			
			@Override
			public void run(){
				while(true) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				   //Move asteroid
					if (spaceMap.getInhabitant(asteroid.getPosition()) == Inhabitant.ASTEROID)
						spaceMap.setInhabitant(Inhabitant.EMPTY, asteroid.getPosition());
					asteroid.move();
				   
					if(spaceMap.isOnMap(asteroid.getPosition()) && spaceMap.getInhabitant(asteroid.getPosition()) != Inhabitant.PLANET
							&& spaceMap.getInhabitant(asteroid.getPosition()) != Inhabitant.TREASURE) {
						spaceMap.setInhabitant(Inhabitant.ASTEROID, asteroid.getPosition());
					} else {
						int randomX = (int) Math.floor(Math.random() * spaceMap.getDimensions());
						Point2D asteroidSpot = new Point2D(randomX, 0);
						asteroid.setPosition(asteroidSpot);
					}
					
					//Move asteroid field
					for(Debris chunk: asteroidField.getAsteroids()) {
						if (spaceMap.getInhabitant(chunk.getPosition()) != Inhabitant.PLANET &&
								spaceMap.getInhabitant(chunk.getPosition()) != Inhabitant.TREASURE) {
							spaceMap.setInhabitant(Inhabitant.EMPTY, chunk.getPosition());
						}
					}
					asteroidField.move();
				   
					if(spaceMap.isOnMap(asteroidField.getPosition())) {
						for(Debris chunk: asteroidField.getAsteroids()) {
							if (spaceMap.getInhabitant(chunk.getPosition()) != Inhabitant.PLANET &&
								spaceMap.getInhabitant(chunk.getPosition()) != Inhabitant.TREASURE) {
								spaceMap.setInhabitant(Inhabitant.ASTEROID, chunk.getPosition());
							}
						}
					} else {
					    int starter = (int) Math.floor(Math.random() * spaceMap.getDimensions());
						Point2D fieldSpot = new Point2D(0, starter);
						Point2D asteroid1spot = fieldSpot;
						Point2D asteroid2spot = new Point2D(1, starter + 2);
						spaceMap.setInhabitant(Inhabitant.ASTEROID, asteroid1spot);
						spaceMap.setInhabitant(Inhabitant.ASTEROID, asteroid2spot);
						
						asteroidField.setPosition(fieldSpot);
						asteroid1.setPosition(asteroid1spot);
						asteroid2.setPosition(asteroid2spot);
					}
					//checkPlayer();
		        }
			}
		};
		backgroundThread.start();
	}
	
	//Changes enemy behavior from patrol to tracking if in range
	public void checkBehavior(Player player, Enemy enemy) {
		if (player.getPosition().distance(enemy.getPosition()) < 8) {
			enemy.setBehavior(new TrackBehavior(enemy, player));
		}
	}
	
	//Check if player has run into obstacle
	private void checkPlayer(){
		Inhabitant inhabitant = spaceMap.getInhabitant(player.getPosition());
		if(inhabitant.equals(Inhabitant.ALIEN)) {
			finishDialogue("You Lose", "Enjoy living in alien jail...");
		} else if(inhabitant.equals(Inhabitant.ASTEROID)) {
			finishDialogue("You Lose", "Maybe watch out for rocks next time...");
		}else if(inhabitant.equals(Inhabitant.TREASURE)) {
			finishDialogue("You Win!", "Congratulations on finding the space treasure!");
		}
	}
	
	//Ending game dialogue
	private void finishDialogue(String title, String subtitle) {
		//Stop thread
		backgroundThread.stop();
		
		//Put black over the screen
		Rectangle rect = new Rectangle(0, 0, scene.getWidth(), scene.getHeight());
		rect.setStroke(Color.BLACK);
		rect.setFill(Color.BLACK);

		root.getChildren().add(rect);// Add to the node tree in the pane

		//Draw You Lose label
		Label loseLabel = new Label(title);
		loseLabel.setFont(new Font("Arial", 30));
		loseLabel.setTextFill(Color.WHITE);
		loseLabel.setMaxWidth(Double.MAX_VALUE);
		AnchorPane.setLeftAnchor(loseLabel, 0.0);
		AnchorPane.setRightAnchor(loseLabel, 0.0);
		AnchorPane.setTopAnchor(loseLabel, scene.getHeight() / 3.0);
		loseLabel.setAlignment(Pos.CENTER);

		//Draw the egg-them-on label
		Label eggLabel = new Label(subtitle);
		eggLabel.setFont(new Font("Arial", 20));
		eggLabel.setTextFill(Color.WHITE);
		eggLabel.setMaxWidth(Double.MAX_VALUE);
		AnchorPane.setLeftAnchor(eggLabel, 0.0);
		AnchorPane.setRightAnchor(eggLabel, 0.0);
		AnchorPane.setTopAnchor(eggLabel, scene.getHeight() / 2.0);
		eggLabel.setAlignment(Pos.CENTER);

		//Add to window
		root.getChildren().add(loseLabel);
		root.getChildren().add(eggLabel);
	}
	
	//Ensure background thread is closed when user clicks X button
	@Override
	public void stop(){
	    backgroundThread.stop();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
