package view.statemachine;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.controller.AdminController;

public class AdminState extends PhotosState{

	/**
	 * Singleton instance 
	 */
	private static AdminState instance = null;
	
	/**
	 * Corresponding controller for this state
	 */
	public static AdminController adminController;
	
	/**
	 * Returns singleton instance.
	 * 
	 * @return Singleton instance
	 */
	public static AdminState getInstance() {
		if(instance == null) {
			instance = new AdminState();
		}
		return instance;
	}
	
	/**
	 * Prevents instantiation, to implement singleton pattern.
	 */
	private AdminState() {
		stage = new Stage();
		stage.setTitle("Admin Subsystem");
		stage.setResizable(false);
	}
	
	/**
	 * Activates the controller for this state.
	 */
	public void enter() {
		// get FXML
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/fxml/admin.fxml"));
		Parent root;
		try {
			root = loader.load();
		}catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
		// get controller
		adminController = loader.getController();
		
		// show the window
		Scene mainScene = new Scene(root);
		stage.setScene(mainScene);
		stage.show();
	}
	
	public PhotosState processEvent() {
		return null;
	}
	
}
