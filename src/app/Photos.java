package app;

import javafx.application.Application;
import javafx.stage.Stage;
import view.statemachine.StateMachine;

/**
 * Main application class.
 * @author John Hoban
 * @author Kaushal Patel
 *
 */
public class Photos extends Application {
	
	public static final StateMachine driver = StateMachine.getInstance();

	public void start(Stage mainStage) {
		driver.start();
	}
	
	public void stop() {
		driver.stop();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
