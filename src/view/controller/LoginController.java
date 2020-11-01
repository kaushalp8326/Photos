package view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import view.statemachine.StateMachine;

/**
 * Controller for the login screen.
 * @author John Hoban
 * @author Kaushal Patel
 *
 */
public class LoginController extends PhotosController {
	
	// FXML entities
	@FXML public TextField txtUsername;
	@FXML public Button cmdLogin;
	@FXML public Button cmdQuit;
	
	@FXML private void cmdLoginPressed(ActionEvent e) {
		stateMachine.processEvent(e);
	}
	
	@FXML private void cmdQuitPressed(ActionEvent e) {
		// TODO
	}
	
}