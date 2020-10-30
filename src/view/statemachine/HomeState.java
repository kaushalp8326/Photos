package view.statemachine;

public class HomeState extends PhotosState{

	/**
	 * Singleton instance 
	 */
	private static HomeState instance = null;
	
	/**
	 * Prevents instantiation, to implement singleton pattern.
	 */
	private HomeState() { 
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
	public static HomeState getInstance() {
		if(instance == null) {
			instance = new HomeState();
		}
		return instance;
	}
	
}
