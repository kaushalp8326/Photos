package view.controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import model.Album;
import model.User;

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
	
	// Observable list of usernames
	private ObservableList<String> olist;
	// Sorted wrapper list
	private ObservableList<String> usernames;
	
	// Selected list index
	private int selectedIndex;
	
	/**
	 * Imports the list of usernames from the /user/ directory and displays them.
	 */
	public void start() {
		
		olist = FXCollections.observableArrayList();
		usernames = new SortedList<String>(olist, (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase()));
		
		// Walk the user directory and add all .dat files to the list
		// This should never break unless someone mucks about in the user folder
		File dir = new File("user");
		File[] users = dir.listFiles();
		if(users != null) {
			for(File f: users) {
				String fname = f.getName();
				if(fname.endsWith(".dat")) {
					olist.add(fname.substring(0,fname.length()-4));
				}
			}
		}
		
		// Connect lstUsers to a listener
		lstUsers.setItems(usernames);
		lstUsers
			.getSelectionModel()
			.selectedIndexProperty()
			.addListener((obs, oldVal, newVal) -> {});
		lstUsers.getSelectionModel().select(selectedIndex);
		
	}
	
	/**
	 * Create a new user by initializing a new user save file.
	 */
	public void createUser() {
		String name = showInputDialog(stage, "Create User", "Enter a name for the new user: ");
		if(name == null) {
			return;
		}
		User user;
		try{
			user = new User(name);
		}catch(IOException e) {
			showErrorDialog(stage, "Save Data Error", "Unable to create new user.");
			return;
		}
		
		olist.add(user.getName());
		
	}
	
	/**
	 * Delete the selected user by deleting their corresponding save file.
	 */
	public void deleteUser() {
		String target = lstUsers.getSelectionModel().getSelectedItem();
		ButtonType response = showConfirmationDialog(stage, "Delete user", "Are you sure you want to delete user \"" + target + "\"?");
		if(response == null || response == ButtonType.CANCEL) {
			return;
		}
		File f = new File("user/" + target + ".dat");
		f.delete();
		olist.remove(target);
		
	}
	
	@FXML private void processEvent(ActionEvent e) {
		stateMachine.processEvent(e);
	}

}
