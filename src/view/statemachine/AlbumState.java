package view.statemachine;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import view.controller.AlbumController;

public class AlbumState extends PhotosState{

	/**
	 * Singleton instance 
	 */
	private static AlbumState instance = null;
	
	/**
	 * Corresponding controller for this state
	 */
	public static AlbumController albumController;
	
	/**
	 * Prevents instantiation, to implement singleton pattern.
	 */
	private AlbumState() { 
		// TODO
	}
	
	public void enter() {
		// get FXML
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/fxml/album_viewer.fxml"));
		Parent root;
		try {
			root = loader.load();
		}catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
		// get controller
		albumController = loader.getController();
		albumController.start();
		
		// show the window
		Scene mainScene = new Scene(root);
		albumController.stage = stateMachine.homeState.homeController.stage;
		albumController.stage.setTitle("Photo Viewer");
		albumController.stage.setResizable(false);
		albumController.stage.setScene(mainScene);
		albumController.stage.show();
	}
	
	public PhotosState processEvent() {
		
		Button b = (Button)lastEvent.getSource();
		
		if(b == albumController.cmdAddPhoto) {
			albumController.addPhoto();
			
		}else if(b == albumController.cmdRemovePhoto) {
			albumController.removePhoto();
			
		}else if(b == albumController.cmdAddTag) {
			albumController.addTag();
			
		}else if(b == albumController.cmdRemoveTag) {
			albumController.removeTag();
			
		}else if(b == albumController.cmdEditCaption) {
			albumController.editCaption();
			//reload album view
			albumController.stage.close();
			return stateMachine.albumState;
			
		}else if(b == albumController.cmdMovePhoto) {
			albumController.movePhoto();
			
		}else if(b == albumController.cmdCopyPhoto) {
			albumController.copyPhoto();
			
		}else if(b == albumController.cmdViewSlideshow) {
			return stateMachine.slideshowState;
			
		}else if(b == albumController.cmdCreateAlbumFromSearch) {
			albumController.createAlbumFromSearch();
			
		}else if(b == albumController.cmdClose) {
			albumController.stage.close();
			return stateMachine.homeState;
		}
		
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
