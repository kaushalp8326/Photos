package view.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
	
	public void start() {}
	
	@FXML private void processEvent(Event e) {
		stateMachine.processEvent(e);
	}
	
}
