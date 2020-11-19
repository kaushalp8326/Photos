package view.statemachine;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	protected static AdminState getInstance() {
		if(instance == null) {
			instance = new AdminState();
		}
		return instance;
	}
	
	/**
	 * Prevents instantiation, to implement singleton pattern.
	 */
	private AdminState() {}
	
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
		adminController.start();
		
		// show the window
		Scene mainScene = new Scene(root);
		adminController.stage = new Stage();
		adminController.stage.setTitle("Login");
		adminController.stage.setResizable(false);
		adminController.stage.setScene(mainScene);
		adminController.stage.show();
	}
	
	/**
	 * Process an event from the admin subsystem.
	 * The admin can create and delete users, or log out.
	 * @return The next state.
	 */
	public PhotosState processEvent() {
		
		Button b = (Button)lastEvent.getSource();
		
		if(b == adminController.cmdCreateUser) {
			adminController.createUser();
			
		}else if(b == adminController.cmdDeleteUser) {
			adminController.deleteUser();
			
		}else if(b == adminController.cmdLogout) {
			adminController.stage.close();
			return stateMachine.loginState;
		}
		
		return null;
		
	}
	
}
