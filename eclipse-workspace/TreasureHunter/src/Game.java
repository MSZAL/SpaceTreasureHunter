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
	
	public static final int GRID_SIZE = 50;
	
	public static final int PLANET_COUNT = 10;
	
	
	private final int DIMENSION = 50;
	private final int SCALE = 25;
	
	private SpaceMap spaceMap;
	
	private Player player;
	private Scene scene;
	Pane root;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		root = new AnchorPane();
		scene = new Scene(root,DIMENSION*SCALE,DIMENSION*SCALE);
		
		spaceMap = SpaceMap.getInstance();
		spaceMap.buildMap(GRID_SIZE, PLANET_COUNT);
		
		for (int y = 0; y < DIMENSION; y++) {
			for (int x = 0; x < DIMENSION; x++) {
				Rectangle rect = new Rectangle(x*SCALE,y*SCALE,SCALE,SCALE);
				rect.setStroke(Color.BLACK);
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
		
		player = new Player(new Point2D (0,0), 1);
		
		Enemy enemy = new Enemy(player, new Point2D(0,3));
		enemy.setBehavior(new TrackBehavior(enemy,player));
		
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
				spaceMap.setInhabitant(Inhabitant.EMPTY, enemy.getPosition());
				
				switch(event.getCode()) {
				case RIGHT:
					player.moveRight();
					break;
				case LEFT:
					player.moveLeft();
					break;
				case UP:
					player.moveUp();
					break;
				case DOWN:
					player.moveDown();
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
			}
			
		};
		
		scene.setOnKeyPressed(keyHandler);
		
		//Create lone asteroid
		int starter = (int) Math.floor(Math.random() * spaceMap.getDimensions());
		Point2D asteroidSpot = new Point2D(0, starter);
		spaceMap.setInhabitant(Inhabitant.ASTEROID, asteroidSpot);
		Debris asteroid = new Asteroid(asteroidSpot, Direction.DOWN);
		
		//Create asteroid field
		starter = (int) Math.floor(Math.random() * spaceMap.getDimensions());
		Point2D fieldSpot = new Point2D(0, starter);
		Point2D asteroid1spot = fieldSpot;
		Point2D asteroid2spot = new Point2D(1, starter + 2);
		spaceMap.setInhabitant(Inhabitant.ASTEROID, asteroid1spot);
		spaceMap.setInhabitant(Inhabitant.ASTEROID, asteroid2spot);
		
		Debris asteroidField = new AsteroidCluster(fieldSpot, Direction.RIGHT);
		Debris asteroid1 = new Asteroid(asteroid1spot, Direction.RIGHT);
		Debris asteroid2 = new Asteroid(asteroid2spot, Direction.RIGHT);
		
		asteroidField.addAsteroid(asteroid1);
		asteroidField.addAsteroid(asteroid2);
		
		root.getChildren().add(asteroid.getImage());
		root.getChildren().add(asteroid1.getImage());
		root.getChildren().add(asteroid2.getImage());
		
		//Create thread for asteroids
		Thread thread = new Thread("AsteroidThread") {
			public void run(){
				while(true) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				   //Move asteroid
				   spaceMap.setInhabitant(Inhabitant.EMPTY, asteroid.getPosition());
				   asteroid.move();
				   
				   if(spaceMap.isOnMap(asteroid.getPosition())) {
					   spaceMap.setInhabitant(Inhabitant.ASTEROID, asteroid.getPosition());
				   } else {
					   int randomX = (int) Math.floor(Math.random() * spaceMap.getDimensions());
					   Point2D asteroidSpot = new Point2D(randomX, 0);
					   asteroid.setPosition(asteroidSpot);
				   }
				   
				   //Move asteroid field
				   for(Debris chunk: asteroidField.getAsteroids()) {
					   spaceMap.setInhabitant(Inhabitant.EMPTY, chunk.getPosition());
				   }
				   asteroidField.move();
				   
				   if(spaceMap.isOnMap(asteroidField.getPosition())) {
					   for(Debris chunk: asteroidField.getAsteroids()) {
						   spaceMap.setInhabitant(Inhabitant.ASTEROID, chunk.getPosition());
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
		        }
			}
		};
		thread.start();
	}
	
	private void checkPlayer(){
		Inhabitant inhabitant = spaceMap.getInhabitant(player.getPosition());
		if(inhabitant.equals(Inhabitant.ALIEN) || inhabitant.equals(Inhabitant.ASTEROID)) {
			finishDialogue("You Lose", "Try again some other time...");
		} else if(inhabitant.equals(Inhabitant.TREASURE)) {
			finishDialogue("You Win!", "Congratulations on finding the space treasure!");
		}
	}
	
	private void finishDialogue(String title, String subtitle) {
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
