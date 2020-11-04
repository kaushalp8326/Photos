package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
		lblAlbum.setText(a.name);
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
		for(Album a: stateMachine.currentUser.albums) {
			olist.add(a);
		}
		albums = new SortedList<Album>(olist,
				(a1, a2) -> a1.name.toUpperCase().compareTo(a2.name.toUpperCase()));
		
		// Configure the lstAlbums so it shows album names instead of object codes
		lstAlbums.setItems(albums);
		lstAlbums.setCellFactory(param -> new ListCell<Album>() {
            @Override
            public void updateItem(Album a, boolean empty) {
                super.updateItem(a, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(a.name);
                }
            }
        });
		
		lstAlbums
			.getSelectionModel()
			.selectedIndexProperty()
			.addListener((obs, oldVal, newVal) -> showAlbumData());
		lstAlbums.getSelectionModel().select(selectedIndex);
		
	}
	
	@FXML private void processEvent(ActionEvent e) {
		stateMachine.processEvent(e);
	}
	
}