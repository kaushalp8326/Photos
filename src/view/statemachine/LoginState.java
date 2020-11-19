package view.statemachine;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;
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
	protected static LoginState getInstance() {
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
	 * If the user enters a different existing username, go to that user's home screen.
	 * If a nonexistent username is entered, show an error dialog.
	 * If the user quits, terminate the program.
	 */
	public PhotosState processEvent() {
		
		Button b = (Button)lastEvent.getSource();
		
		if(b == loginController.cmdLogin) {
			
			String username = loginController.txtUsername.getText();
			
			// Admin
			if(username.equals("admin")) {
				loginController.stage.close();
				return stateMachine.adminState;
			}
			
			// Non-admin user - attempt to load the user from file.
			// On success, move to the home screen.
			try {
				stateMachine.currentUser = User.load(username);
			}catch(ClassNotFoundException | IOException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(loginController.stage);
				alert.setTitle("Login Error");
				alert.setHeaderText("");
				alert.setContentText("Your username did not match any existing users.");
				alert.showAndWait();
				return null;
			}
			loginController.stage.close();
			return stateMachine.homeState;
			
		}else if(b == loginController.cmdQuit) {
			Platform.exit();
		}
		
		return null;
		
	}
	
}
