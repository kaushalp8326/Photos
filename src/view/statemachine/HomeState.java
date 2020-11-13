package view.statemachine;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Album;
import model.Picture;
import view.controller.HomeController;

public class HomeState extends PhotosState{

	/**
	 * Singleton instance 
	 */
	private static HomeState instance = null;
	
	/**
	 * Corresponding controller for this state
	 */
	public static HomeController homeController;
	
	/**
	 * Prevents instantiation, to implement singleton pattern.
	 */
	private HomeState() { 
		// TODO
	}
	
	public void enter() {
		// get FXML
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/fxml/home.fxml"));
		Parent root;
		try {
			root = loader.load();
		}catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
		// get controller
		homeController = loader.getController();
		homeController.start();
		
		// show the window
		Scene mainScene = new Scene(root);
		homeController.stage = new Stage();
		homeController.stage.setTitle("Photo Viewer");
		homeController.stage.setResizable(false);
		homeController.stage.setScene(mainScene);
		homeController.stage.show();
	}
	
	/**
	 * Process a wide variety of user commands from the home screen.
	 */
	public PhotosState processEvent() {
		
		Button b = (Button)lastEvent.getSource();
		
		if(b == homeController.cmdCreateAlbum) {
			homeController.createAlbum();
			
		}else if(b == homeController.cmdRenameAlbum) {
			homeController.renameAlbum();
			
		}else if(b == homeController.cmdDeleteAlbum) {
			homeController.deleteAlbum();
			
		}else if(b == homeController.cmdOpenAlbum) {
			stateMachine.currentAlbum = homeController.lstAlbums.getSelectionModel().getSelectedItem();
			return stateMachine.albumState;
			
		}else if(b == homeController.cmdFindPhotosByTag) {
			ArrayList<Picture> pictures=new ArrayList<Picture>();
			for(Album album:homeController.lstAlbums.getSelectionModel().getSelectedItems()) {
				pictures.addAll(album.getPictures());
			}
			homeController.findPhotosByTag(pictures);
			
		}else if(b == homeController.cmdFindPhotosByDate) {
			// TODO
			
		}else if(b == homeController.cmdLogout) {
			try {	
				stateMachine.currentUser.save();
			}catch(IOException e) {
				e.printStackTrace(); // should not occur
			}
			homeController.stage.close();
			stateMachine.currentUser = null;
			return stateMachine.loginState;
			
		}else if(b == homeController.cmdQuit) {
			Platform.exit();
			
		}
		
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
