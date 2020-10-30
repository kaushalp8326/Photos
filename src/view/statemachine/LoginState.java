package view.statemachine;

public class LoginState extends PhotosState{

	/**
	 * Singleton instance 
	 */
	private static LoginState instance = null;
	
	/**
	 * Prevents instantiation, to implement singleton pattern.
	 */
	private LoginState() { 
		// TODO
	}
	
	public void enter() {
		// TODO
	}
	
	public PhotosState processEvent() {
		return null;
	}
	
	/**
	 * Returns singleton instance.
	 * 
	 * @return Singleton instance
	 */
	public static LoginState getInstance() {
		if(instance == null) {
			instance = new LoginState();
		}
		return instance;
	}
	
}
