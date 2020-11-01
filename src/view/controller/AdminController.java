package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * Controller for the admin subsystem.
 * @author John Hoban
 * @author Kaushal Patel
 *
 */
public class AdminController extends PhotosController {
	
	// FXML entities
	@FXML public ListView<String> lstUsers;
	@FXML public Button cmdCreateUser;
	@FXML public Button cmdDeleteUser;
	@FXML public Button cmdLogout;

}
