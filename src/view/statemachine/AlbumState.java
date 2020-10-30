package view.statemachine;

public class AlbumState extends PhotosState{

	/**
	 * Singleton instance 
	 */
	private static AlbumState instance = null;
	
	/**
	 * Prevents instantiation, to implement singleton pattern.
	 */
	private AlbumState() { 
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
	public static AlbumState getInstance() {
		if(instance == null) {
			instance = new AlbumState();
		}
		return instance;
	}
	
}
