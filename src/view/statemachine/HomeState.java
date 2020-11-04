package view.statemachine;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
