package view.statemachine;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.Event;
import model.Album;
import model.Picture;
import model.User;

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
	public User currentUser;
	
	/**
	 * Instance of the current album, used for the Album State.
	 */
	public Album currentAlbum;
	
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
	 * @return An instance of this class.
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
		slideshowState = SlideshowState.getInstance();
		
		/*User u;
		try{
			u = new User("stock");
		}catch(IOException e) {
			e.printStackTrace();
			return;
		}
		u.createAlbum("stock");
		Album a = u.getAlbums().get(0);
		try {
			a.addPicture(new Picture("data/crab0start1.png"));
			a.addPicture(new Picture("data/crab15start1.png"));
			a.addPicture(new Picture("data/crab18start1.png"));
			a.addPicture(new Picture("data/crab39start1.png"));
			a.addPicture(new Picture("data/crab41start1.png"));
			a.addPicture(new Picture("data/gamma_crab.png"));
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			u.save();
		}catch(IOException e) {
			e.printStackTrace();
		}*/
		
		currentState = loginState;
		loginState.enter();
		
	}
	
	/**
	 * If a user is logged in, save their data before terminating the program.
	 */
	public void stop() {
		
		if(currentUser != null) {
			try {
				currentUser.save();
			}catch(IOException e) {
				e.printStackTrace(); // should never occur
			}
		}
		
	}
	
	/**
     * Process event fired from anywhere in the application
     * 
     * @param e The action event that is to be passed on for handling.
     */
    public void processEvent(Event e) {
        PhotosState.lastEvent = e; 
        PhotosState next = currentState.processEvent();
        if(next != null) { // null = no state change
	        currentState = next;
	        currentState.enter();
        }
    }
	
}
