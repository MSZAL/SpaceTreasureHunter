import javafx.geometry.Point2D;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
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
	
	public static final int ENEMY_COUNT = 3;
	
	private final int START_TRACKING = 8;
	
	private final int DIMENSION = 25;
	private final int SCALE = 25;
	
	private SpaceMap spaceMap;
	
	private Player player;
	private Scene scene;
	Pane root;
	
	AnimationTimer animationTimer;
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
		
		player = generatePlayer();
		ArrayList<Enemy> enemies = generateEnemies(player);
		
		ArrayList<ImageView> imageViews = generateImageViews(enemies);
		updateImageViews(enemies, imageViews);

		for (ImageView iw : imageViews) {
			root.getChildren().add(iw);
		}
		
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
							clearPositions(enemies);
							player.moveRight();
						}
					}
					break;
				case LEFT:
					if (playerX - 1 >= 0) {
						if (spaceMap.getInhabitant(new Point2D(playerX - 1, playerY)) != Inhabitant.PLANET) {
							clearPositions(enemies);
							player.moveLeft();
						}
					}
					break;
				case UP:
					if (playerY - 1 >= 0) {
						if (spaceMap.getInhabitant(new Point2D(playerX, playerY - 1)) != Inhabitant.PLANET) {
							clearPositions(enemies);
							player.moveUp();
						}
					}
					break;
				case DOWN:
					if (playerY + 1 < GRID_SIZE) {
						if (spaceMap.getInhabitant(new Point2D(playerX, playerY + 1)) != Inhabitant.PLANET) {
							clearPositions(enemies);
							player.moveDown();
						}
					}
					break;
				default:
					break;
				}
				updateImageViews(enemies, imageViews);
				setPosition(enemies);
				
				for (Enemy e : enemies) {
					checkBehavior(player, e);
				}
			}
			
		};
		
		scene.setOnKeyPressed(keyHandler);
		
		animationTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				checkPlayer();
			}
			
		};
		
		AsteroidBuilder builder = new AsteroidBuilder();
		
		//Create lone asteroid
		int starter = (int) Math.floor(Math.random() * spaceMap.getDimensions());
		Point2D asteroidSpot = new Point2D(starter, 0);
		spaceMap.setInhabitant(Inhabitant.ASTEROID, asteroidSpot);
		Debris asteroid = new Asteroid(asteroidSpot, Direction.DOWN, builder.buildAsteroid("SpaceRock"));
		
		//Create asteroid field
		starter = (int) Math.floor(Math.random() * spaceMap.getDimensions());
		Point2D fieldSpot = new Point2D(0, starter);
		Point2D asteroid1spot = fieldSpot;
		Point2D asteroid2spot = new Point2D(1, starter + 2);
		spaceMap.setInhabitant(Inhabitant.ASTEROID, asteroid1spot);
		spaceMap.setInhabitant(Inhabitant.ASTEROID, asteroid2spot);
		
		Debris asteroidField = new AsteroidCluster(fieldSpot, Direction.RIGHT);
		Debris asteroid1 = new Asteroid(asteroid1spot, Direction.RIGHT, builder.buildAsteroid("SpaceRock"));
		Debris asteroid2 = new Asteroid(asteroid2spot, Direction.RIGHT, builder.buildAsteroid("SpaceJunk"));
		
		asteroidField.addAsteroid(asteroid1);
		asteroidField.addAsteroid(asteroid2);
		
		root.getChildren().add(asteroid.getSprite().getImage());
		root.getChildren().add(asteroid1.getSprite().getImage());
		root.getChildren().add(asteroid2.getSprite().getImage());
		
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
					
				    //Move lone asteroid
					//Remove reference to asteroid on map
					setAsteroidToMap(asteroid, Inhabitant.EMPTY);
					
					//Move asteroid after reference cleared
					asteroid.move();
				   
					//Set asteroid to map if conditions met, else reset its position
					if(spaceMap.isOnMap(asteroid.getPosition())){
						setAsteroidToMap(asteroid, Inhabitant.ASTEROID);
					} else {
						int random = (int) Math.floor(Math.random() * spaceMap.getDimensions());
						asteroid.reset(random);
					}
					
					//Remove all references to asteroids on map
					setAsteroidListToMap(asteroidField.getAsteroids(), Inhabitant.EMPTY);
					
					//Move asteroid field after references are gone
					asteroidField.move();
				   
					if(spaceMap.isOnMap(asteroidField.getPosition())) {
						
						//Place asteroids on map again.
						setAsteroidListToMap(asteroidField.getAsteroids(), Inhabitant.ASTEROID);
					} else {
						
						//Reset asteroids
					    int random = (int) Math.floor(Math.random() * spaceMap.getDimensions());
						asteroidField.reset(random);
						
						//Place asteroids on map again.
						setAsteroidListToMap(asteroidField.getAsteroids(), Inhabitant.ASTEROID);
					}
		        }
			}
		};
		backgroundThread.start();
		animationTimer.start();
	}
	
	//Set lone asteroid to map
	private void setAsteroidToMap(Debris asteroid, Inhabitant type) {
		Inhabitant inhabitant = spaceMap.getInhabitant(asteroid.getPosition());
		
		if (inhabitant != Inhabitant.PLANET && inhabitant != Inhabitant.TREASURE) {
			spaceMap.setInhabitant(type, asteroid.getPosition());
		}
	}
	
	//Set list of asteroids to map
	private void setAsteroidListToMap(List<Debris> asteroids, Inhabitant type) {
		for(Debris chunk: asteroids) {
			setAsteroidToMap(chunk, type);
		}
	}
	
	private Player generatePlayer() {
		Random r = new Random();
		
		Point2D p = new Point2D(r.nextInt(GRID_SIZE), r.nextInt(GRID_SIZE));
		
		while (spaceMap.getInhabitant(p) != Inhabitant.EMPTY) {
			p = new Point2D(r.nextInt(GRID_SIZE), r.nextInt(GRID_SIZE));
		}
		spaceMap.setInhabitant(Inhabitant.PLAYER, p);
		return new Player(p);
	}
	
	private ArrayList<Enemy> generateEnemies(Player player) {
		ArrayList<Enemy> enemies = new ArrayList<>();
		
		Random r = new Random();
		Point2D p = new Point2D(r.nextInt(GRID_SIZE), r.nextInt(GRID_SIZE));
		
		for (int i = 0; i < ENEMY_COUNT; i++) {
			
			while (spaceMap.getInhabitant(p) != Inhabitant.EMPTY) {
				p = new Point2D(r.nextInt(GRID_SIZE), r.nextInt(GRID_SIZE));
			}
			Enemy e = new Enemy(player, p);
			e.setBehavior(new PatrolBehavior(e.position));
			
			spaceMap.setInhabitant(Inhabitant.ALIEN, p);
			enemies.add(e);
		}
		
		return enemies;
	}
	
	private ArrayList<ImageView> generateImageViews(ArrayList<Enemy> enemies) {
		ArrayList<ImageView> imageViews = new ArrayList<>();
		
		Image playerImage = new Image(player.getImagePath(),SCALE,SCALE,true,true);
		imageViews.add(new ImageView(playerImage));
		
		for (Enemy e : enemies) {
			Image enemyImage = new Image(e.getImagePath(),SCALE,SCALE,true,true);
			imageViews.add(new ImageView(enemyImage));
		}
		
		return imageViews;
	}
	
	private void updateImageViews(ArrayList<Enemy> enemies, ArrayList<ImageView> imageViews) {
		imageViews.get(0).setX(player.getPosition().getX() * SCALE);
		imageViews.get(0).setY(player.getPosition().getY() * SCALE);
		
		for (int i = 0, j = 1; i < enemies.size(); i++,j++) {
			imageViews.get(j).setX(enemies.get(i).getPosition().getX() * SCALE);
			imageViews.get(j).setY(enemies.get(i).getPosition().getY() * SCALE);
		}
	}
	
	// Used in EventHandler for updating positions for the SpaceMap
	private void clearPositions(ArrayList<Enemy> enemies) {
		spaceMap.setInhabitant(Inhabitant.EMPTY, player.getPosition());
		
		for (Enemy e : enemies) {
			spaceMap.setInhabitant(Inhabitant.EMPTY, e.getPosition());
		}
	}
	
	// Used in EventHandler for updating positions for the SpaceMap
	private void setPosition(ArrayList<Enemy> enemies) {
		
		for (Enemy e : enemies) {
			spaceMap.setInhabitant(Inhabitant.ALIEN, e.getPosition());
		}
		checkPlayer();
		
		spaceMap.setInhabitant(Inhabitant.PLAYER, player.getPosition());
	}
	
	//Changes enemy behavior from patrol to tracking if in range
	public void checkBehavior(Player player, Enemy enemy) {
		if (player.getPosition().distance(enemy.getPosition()) < START_TRACKING) {
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
		stop();
		
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
	    animationTimer.stop();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
