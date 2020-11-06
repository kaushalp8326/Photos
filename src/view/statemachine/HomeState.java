package view.statemachine;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
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
		homeController.stage.setTitle("Login");
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
			// TODO
			
		}else if(b == homeController.cmdOpenAlbum) {
			// TODO
			
		}else if(b == homeController.cmdFindPhotosByTag) {
			// TODO	
			
		}else if(b == homeController.cmdFindPhotosByDate) {
			// TODO
			
		}else if(b == homeController.cmdLogout) {
			// TODO
			
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
