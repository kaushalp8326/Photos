package view.statemachine;

public class SlideshowState extends PhotosState{

	/**
	 * Singleton instance 
	 */
	private static SlideshowState instance = null;
	
	/**
	 * Prevents instantiation, to implement singleton pattern.
	 */
	private SlideshowState() { 
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
	public static SlideshowState getInstance() {
		if(instance == null) {
			instance = new SlideshowState();
		}
		return instance;
	}
	
}
