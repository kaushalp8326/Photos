package view.statemachine;

import javafx.event.ActionEvent;

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
        currentState = currentState.processEvent();
    }
	
}
