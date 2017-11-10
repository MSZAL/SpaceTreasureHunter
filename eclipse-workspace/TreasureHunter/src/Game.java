import javafx.geometry.Point2D;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Game extends Application {
	
	public final int GRID_SIZE		  = 500;
	public final int SCREEN_GRID_SIZE = 10;
	
	private SpaceMap spaceMap;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = new AnchorPane();
		Scene scene = new Scene(root,500,500);
		
		spaceMap = SpaceMap.getInstance();
		
		//spaceMap.setInhabitant(Inhabitant.ALIEN, new Point2D(10,10));
		
		
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
