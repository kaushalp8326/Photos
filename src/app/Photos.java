package app;

import java.io.Serializable;

import javafx.application.Application;
import javafx.stage.Stage;
import view.statemachine.StateMachine;

/**
 * Main application class.
 * @author John Hoban
 * @author Kaushal Patel
 *
 */
public class Photos extends Application implements Serializable{

	public void start(Stage mainStage) {
		StateMachine sm = new StateMachine();
		sm.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
