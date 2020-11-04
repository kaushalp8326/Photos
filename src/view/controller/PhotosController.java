package view.controller;

import javafx.stage.Stage;
import view.statemachine.StateMachine;

/**
 * Abstract wrapper class for all controllers. Ensures that all controllers inherit the same state machine instance.
 * @author John Hoban
 * @author Kaushal Patel
 *
 */
public abstract class PhotosController {
	
	protected static StateMachine stateMachine = StateMachine.getInstance();
	
	/**
	 * A particular controller's corresponding stage.
	 * Each controller gets its own stage.
	 */
	public Stage stage;

}
