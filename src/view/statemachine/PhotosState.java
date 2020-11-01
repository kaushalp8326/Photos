package view.statemachine;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public abstract class PhotosState {
	
	/**
	 * The parent state machine
	 */
	static StateMachine stateMachine = StateMachine.getInstance();
	
	/**
	 * The last action event that was fired.
	 */
	static ActionEvent lastEvent;
	
	/**
	 * A particular state's corresponding stage.
	 * Each state gets its own stage.
	 */
	protected Stage stage;
	
	/**
	 * This method is overridden by each subclass with a state-specific implementation.
	 * It is called when a state is entered.
	 */
	abstract void enter();
	
	/**
	 * This method is called when an event is fired, on the current state instance.
	 * Each specific instance will override this method as needed.
	 */
	abstract PhotosState processEvent();
	
}
