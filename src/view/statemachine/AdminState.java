package view.statemachine;

public class AdminState extends PhotosState{

	/**
	 * Singleton instance 
	 */
	private static AdminState instance = null;
	
	/**
	 * Prevents instantiation, to implement singleton pattern.
	 */
	private AdminState() { 
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
	public static AdminState getInstance() {
		if(instance == null) {
			instance = new AdminState();
		}
		return instance;
	}
	
}
