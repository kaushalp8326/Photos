package view.statemachine;

public class AddPhotoState extends PhotosState{

	/**
	 * Singleton instance 
	 */
	private static AddPhotoState instance = null;
	
	/**
	 * Prevents instantiation, to implement singleton pattern.
	 */
	private AddPhotoState() { 
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
	public static AddPhotoState getInstance() {
		if(instance == null) {
			instance = new AddPhotoState();
		}
		return instance;
	}
	
}
