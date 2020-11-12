package view.statemachine;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import view.controller.SlideshowController;

public class SlideshowState extends PhotosState{

	/**
	 * Singleton instance 
	 */
	private static SlideshowState instance = null;
	
	/**
	 * Corresponding controller for this state
	 */
	public static SlideshowController slideshowController;
	
	/**
	 * Prevents instantiation, to implement singleton pattern.
	 */
	private SlideshowState() { 
		// TODO
	}
	
	public void enter() {
		// get FXML
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/fxml/slideshow.fxml"));
		Parent root;
		try {
			root = loader.load();
		}catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
		// get controller
		slideshowController = loader.getController();
		slideshowController.start();
		
		// show the window
		Scene mainScene = new Scene(root);
		slideshowController.stage.setTitle("Photo Viewer");
		slideshowController.stage.setResizable(false);
		slideshowController.stage.setScene(mainScene);
		slideshowController.stage.initModality(Modality.WINDOW_MODAL);
		slideshowController.stage.show();
	}
	
	public PhotosState processEvent() {
		
		// Close window event
		if(lastEvent instanceof WindowEvent) {
			slideshowController.stage.close();
			return stateMachine.albumState;
		}
		
		Button b = (Button)lastEvent.getSource();
		
		if(b == slideshowController.cmdFirstPhoto) {
			slideshowController.slide = 0;
			
		}else if(b == slideshowController.cmdPrevPhoto) {
			slideshowController.slide--;
			
		}else if(b == slideshowController.cmdNextPhoto) {
			slideshowController.slide++;
			
		}else if(b == slideshowController.cmdLastPhoto) {
			slideshowController.slide = stateMachine.currentAlbum.getSize() - 1;
			
		}
		
		slideshowController.showPhoto();
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
