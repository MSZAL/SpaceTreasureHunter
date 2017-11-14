import javafx.geometry.Point2D;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Game extends Application {
	
	public static final int GRID_SIZE = 100;
	
	public static final int PLANET_COUNT = 20;
	
	private final int DIMENSION = 100;
	private final int SCALE = 10;
	
	private SpaceMap spaceMap;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = new AnchorPane();
		Scene scene = new Scene(root,DIMENSION*SCALE,DIMENSION*SCALE);
		
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
				default:
					rect.setFill(Color.GREEN); //Just denotes if invalid value is popping up
					break;
				}	
				root.getChildren().add(rect);
			}
		}
		
		Player player = new Player(new Point2D (GRID_SIZE / 2, GRID_SIZE / 2), 1);
		
		Enemy enemy = new Enemy(player, new Point2D(30,30));
		Enemy enemy2 = new Enemy(player, new Point2D(70,70));
		enemy.setBehavior(new TrackBehavior(enemy,player));
		enemy2.setBehavior(new PatrolBehavior(enemy2.getPosition()));
		
		Image playerImage = new Image(player.getImagePath(),50,50,true,true);
		ImageView playerImageView = new ImageView(playerImage);
		
		Image enemyImage = new Image(enemy.getImagePath(),50,50,true,true);
		ImageView enemyImageView = new ImageView(enemyImage);
		
		Image enemyImage2 = new Image(enemy2.getImagePath(),50,50,true,true);
		ImageView enemyImageView2 = new ImageView(enemyImage2);
		
		playerImageView.setX(player.getPosition().getX() * SCALE);
		playerImageView.setY(player.getPosition().getY() * SCALE);
		
		enemyImageView.setX(enemy.getPosition().getX() * SCALE);
		enemyImageView.setY(enemy.getPosition().getY() * SCALE);
		
		enemyImageView2.setX(enemy2.getPosition().getX() * SCALE);
		enemyImageView2.setY(enemy2.getPosition().getY() * SCALE);
		
		root.getChildren().add(playerImageView);
		root.getChildren().add(enemyImageView);	
		root.getChildren().add(enemyImageView2);
		
		primaryStage.setTitle("Space Treasure Hunter");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
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
				
				enemyImageView2.setX(enemy2.getPosition().getX() * SCALE);
				enemyImageView2.setY(enemy2.getPosition().getY() * SCALE);
			}
			
		};
		
		scene.setOnKeyPressed(keyHandler);
		
		int starter = (int) Math.floor(Math.random() * spaceMap.getDimensions());
		Point2D asteroidSpot = new Point2D(0, starter);
		spaceMap.setInhabitant(Inhabitant.ASTEROID, asteroidSpot);
		Debris asteroid = new Asteroid(new Point2D(starter, 0), Direction.DOWN);
		
		Image picture = new Image(asteroid.getImagePath(), 1 * SCALE, 1 * SCALE, true, true);
		ImageView asteroidImage = new ImageView(picture);
		asteroidImage.setX(asteroid.getPosition().getX() * SCALE);
		asteroidImage.setY(asteroid.getPosition().getY() * SCALE);
		
		root.getChildren().add(asteroidImage);
		
		//Create thread for asteroids
		Thread thread = new Thread("AsteroidThread") {
			public void run(){
				while(true) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				   
				   spaceMap.setInhabitant(Inhabitant.EMPTY, asteroid.getPosition());
				   asteroid.move();
				   
				   if(spaceMap.isOnMap(asteroid.getPosition())) {
					   spaceMap.setInhabitant(Inhabitant.ASTEROID, asteroid.getPosition());
					   
					   asteroidImage.setX(asteroid.getPosition().getX() * SCALE);
					   asteroidImage.setY(asteroid.getPosition().getY() * SCALE);
				   } else {
					   int randomX = (int) Math.floor(Math.random() * spaceMap.getDimensions());
					   Point2D asteroidSpot = new Point2D(randomX, 0);
					   asteroid.setPosition(asteroidSpot);
					   asteroidImage.setX(asteroidSpot.getX());
					   asteroidImage.setY(asteroidSpot.getY());
				   }
		        }
			}
		};
		thread.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
