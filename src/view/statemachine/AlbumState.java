package view.statemachine;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
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
		albumController.stage = new Stage();
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
			// TODO
			
		}else if(b == albumController.cmdRemoveTag) {
			// TODO
			
		}else if(b == albumController.cmdEditCaption) {
			albumController.editCaption();
			
		}else if(b == albumController.cmdMovePhoto) {
			albumController.movePhoto();
			
		}else if(b == albumController.cmdCopyPhoto) {
			albumController.copyPhoto();
			
		}else if(b == albumController.cmdViewSlideshow) {
			// TODO
			
		}else if(b == albumController.cmdCreateAlbumFromSearch) {
			// TODO
			
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
