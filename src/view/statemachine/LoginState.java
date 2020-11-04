package view.statemachine;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.controller.LoginController;

public class LoginState extends PhotosState{

	/**
	 * Singleton instance 
	 */
	private static LoginState instance = null;
	
	/**
	 * Corresponding controller for this state
	 */
	public static LoginController loginController;
	
	/**
	 * Returns singleton instance.
	 * 
	 * @return Singleton instance
	 */
	public static LoginState getInstance() {
		if(instance == null) {
			instance = new LoginState();
		}
		return instance;
	}
	
	/**
	 * Prevents instantiation, to implement singleton pattern.
	 */
	private LoginState() {}
	
	/**
	 * Activates the controller for this state.
	 */
	public void enter() {
		// get FXML
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/fxml/login.fxml"));
		Parent root;
		try {
			root = loader.load();
		}catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
		// get controller
		loginController = loader.getController();
		loginController.start();
		
		// show the window
		Scene mainScene = new Scene(root);
		loginController.stage = new Stage();
		loginController.stage.setTitle("Login");
		loginController.stage.setResizable(false);
		loginController.stage.setScene(mainScene);
		loginController.stage.show();
	}
	
	/**
	 * Process an event from the login screen.
	 * If the user logs in and the username is "admin", go to the admin subsystem.
	 * TODO If the user enters a different existing username, go to that user's home screen.
	 * TODO If a nonexistent username is entered, show an error dialog.
	 * If the user quits, terminate the program.
	 */
	public PhotosState processEvent() {
		
		Button b = (Button)lastEvent.getSource();
		
		if(b == loginController.cmdLogin) {
			// Admin
			if(loginController.txtUsername.getText().equals("admin")) {
				
				return stateMachine.adminState;
			}
			// TODO - other usernames
		}
		
		// TODO - quit, which probably requires a StateMachine.exit() method
		return null;
		
	}
	
}
