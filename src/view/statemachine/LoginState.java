package view.statemachine;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	
	// Instance of the overall state machine
	private StateMachine stateMachine;
	
	/**
	 * Prevents instantiation, to implement singleton pattern.
	 */
	private LoginState() { 
		// TODO
	}
	
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
		
		// show the window
		Scene mainScene = new Scene(root);
		stateMachine.loginStage = new Stage();
		{
			Stage s = stateMachine.loginStage;
			s.setScene(mainScene);
			s.setTitle("Login");
			s.setResizable(false);
			s.show();
		}
	}
	
	public PhotosState processEvent() {
		return null;
	}
	
	/**
	 * Assign this state an instance of the overall state machine.
	 * @param sm State machine
	 */
	public void setStateMachine(StateMachine sm) {
		this.stateMachine = sm;
	}
	
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
	
}
