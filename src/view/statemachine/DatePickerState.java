package view.statemachine;

public class DatePickerState extends PhotosState{

	/**
	 * Singleton instance 
	 */
	private static DatePickerState instance = null;
	
	/**
	 * Prevents instantiation, to implement singleton pattern.
	 */
	private DatePickerState() { 
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
	public static DatePickerState getInstance() {
		if(instance == null) {
			instance = new DatePickerState();
		}
		return instance;
	}
	
}
