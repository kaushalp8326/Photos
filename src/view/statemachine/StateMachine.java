package view.statemachine;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import model.User;
import view.controller.LoginController;

/**
 * State machine representation of the Photos application.
 * @author John Hoban
 * @author Kaushal Patel
 *
 */
public class StateMachine {

	/**
	 * Login screen state
	 */
	protected LoginState loginState;
	
	/**
	 * Admin subsystem state
	 */
	protected AdminState adminState;
	
	/**
	 * Home screen state
	 */
	protected HomeState homeState;
	
	/**
	 * Date picker dialog state
	 */
	protected DatePickerState datePickerState;
	
	/**
	 * Album viewer state
	 */
	protected AlbumState albumState;
	
	/**
	 * Add photo dialog state
	 */
	protected AddPhotoState addPhotoState;
	
	/**
	 * Slideshow viewer state
	 */
	protected SlideshowState slideshowState;
	
	/**
	 * Instance of the current state
	 */
	protected PhotosState currentState;
	
	/**
	 * Instance of the current user. When a user logs in, this object is loaded from serialized data.
	 */
	protected User currentUser;
	
	/**
	 * Singleton instance of this state machine.
	 */
	private static StateMachine instance;
	
	/**
	 * Disallow instantiation so there is only one instance of this machine
	 */
	private StateMachine() {}
	
	/**
	 * Get the single state machine instance.
	 */
	public static StateMachine getInstance() {
		if(instance == null) {
			instance = new StateMachine();
		}
		return instance;
	}
	
	/**
	 * Start the state machine by loading each state. Enters at the "login" state.
	 */
	public void start() {
		
		loginState = LoginState.getInstance();
		adminState = AdminState.getInstance();
		homeState = HomeState.getInstance();
		datePickerState = DatePickerState.getInstance();
		albumState = AlbumState.getInstance();
		addPhotoState = AddPhotoState.getInstance();
		slideshowState = SlideshowState.getInstance();
		
		currentState = loginState;
		loginState.enter();
		
	}
	
	/**
     * Process event fired from anywhere in the application
     * 
     * @param e The action event that is to be passed on for handling.
     */
    public void processEvent(ActionEvent e) {
        PhotosState.lastEvent = e; 
        PhotosState next = currentState.processEvent();
        if(next != null) { // null = no state change
	        currentState = next;
	        currentState.enter();
        }
    }
	
}
