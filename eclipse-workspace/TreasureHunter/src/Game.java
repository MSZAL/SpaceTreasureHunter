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
		enemy.setBehavior(new TrackBehavior(enemy,player));
		
		Image playerImage = new Image(player.getImagePath(),50,50,true,true);
		ImageView playerImageView = new ImageView(playerImage);
		
		Image enemyImage = new Image(enemy.getImagePath(),50,50,true,true);
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
			}
			
		};
		
		scene.setOnKeyPressed(keyHandler);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
