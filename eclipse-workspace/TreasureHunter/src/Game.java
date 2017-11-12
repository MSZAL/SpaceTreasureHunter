import javafx.geometry.Point2D;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
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
		
		
		primaryStage.setTitle("Space Treasure Hunter");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		scene.setOnKeyPressed(keyHandler);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
