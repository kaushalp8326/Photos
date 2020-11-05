package view.controller;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import model.Album;

/**
 * Controller for the home screen.
 * @author John Hoban
 * @author Kaushal Patel
 *
 */
public class HomeController extends PhotosController {

	// FXML entities
	@FXML public Label lblAlbum;
	@FXML public Label lblSize;
	@FXML public Label lblEarliestUpload;
	@FXML public Label lblLatestUpload;
	
	@FXML public Button cmdCreateAlbum;
	@FXML public Button cmdRenameAlbum;
	@FXML public Button cmdDeleteAlbum;
	@FXML public Button cmdOpenAlbum;
	@FXML public Button cmdFindPhotosByTag;
	@FXML public Button cmdFindPhotosByDate;
	@FXML public Button cmdLogout;
	@FXML public Button cmdQuit;
	
	@FXML public ListView<Album> lstAlbums;
	
	// List of albums
	private ObservableList<Album> olist;
	// Sorted wrapper list
	private ObservableList<Album> albums;
	
	// Selected list index
	private int selectedIndex;
	
	/**
	 * Display attributes of the highlighted album.
	 */
	private void showAlbumData() {
		if(olist.size() == 0) {
			lblAlbum.setText("No Album Data Found");
			lblSize.setText("");
			lblEarliestUpload.setText("");
			lblLatestUpload.setText("");
			return;
		}
		
		Album a = lstAlbums.getSelectionModel().getSelectedItem();
		
		// Display data
		lblAlbum.setText(a.getName());
		lblSize.setText("Size: " + a.getSize());
		if(a.getSize() == 0) {
			lblEarliestUpload.setText("Earliest Upload: None");
			lblLatestUpload.setText("Latest Upload: None");
		}else {
			lblEarliestUpload.setText("Earliest Upload: " + a.getEarliestTimestamp());
			lblLatestUpload.setText("Latest Upload: " + a.getLatestTimestamp());
		}
	}
		
	/**
	 * Imports the list of albums from the current user and displays them.
	 */
	public void start() {
		
		olist = FXCollections.observableArrayList();
		olist.addAll(stateMachine.currentUser.getAlbums());
		albums = new SortedList<Album>(olist,
				(a1, a2) -> a1.getName().toUpperCase().compareTo(a2.getName().toUpperCase()));
		
		// Configure the lstAlbums so it shows album names instead of object codes
		lstAlbums.setItems(albums);
		lstAlbums.setCellFactory(param -> new ListCell<Album>() {
            @Override
            public void updateItem(Album a, boolean empty) {
                super.updateItem(a, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(a.getName());
                }
            }
        });
		
		lstAlbums
			.getSelectionModel()
			.selectedIndexProperty()
			.addListener((obs, oldVal, newVal) -> showAlbumData());
		lstAlbums.getSelectionModel().select(selectedIndex);
		
	}
	
	/**
	 * Create a new album for the current user, as long as the name is not a duplicate.
	 */
	public void createAlbum() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(stage);
		dialog.setTitle("Create Album");
		dialog.setHeaderText("");
		dialog.setContentText("Enter name for new album: ");
		Optional<String> result = dialog.showAndWait();
		
		if(result.isEmpty()) {return;}
		
		String name = result.get();
		try{
			Album album = stateMachine.currentUser.createAlbum(name);
			olist.add(album);
		}catch(IllegalArgumentException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(stage);
			alert.setTitle("Error");
			alert.setHeaderText("");
			alert.setContentText("An album with that name already exists.");
			alert.showAndWait();
			return;
		}
		
	}
	
	/**
	 * Rename the selected album, as long as the name is not a duplicate.
	 */
	public void renameAlbum() {
		// TODO - some kind of validation that the list isn't empty
		Album album = lstAlbums.getSelectionModel().getSelectedItem();
		
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(stage);
		dialog.setTitle("Rename Album");
		dialog.setHeaderText("");
		dialog.setContentText("Enter a new name for album \"" + album.getName() + "\": ");
		Optional<String> result = dialog.showAndWait();
		
		if(result.isEmpty()) {return;}
		
		String name = result.get();
		if(!album.setName(name)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(stage);
			alert.setTitle("Error");
			alert.setHeaderText("");
			alert.setContentText("An album with that name already exists.");
			alert.showAndWait();
			return;
		}
		
	}
	
	@FXML private void processEvent(ActionEvent e) {
		stateMachine.processEvent(e);
	}
	
}
