package view.controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Album;
import model.Picture;
import model.User;

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
			lblEarliestUpload.setText("Earliest Upload: " + a.getEarliestTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE));
			lblLatestUpload.setText("Latest Upload: " + a.getLatestTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE));
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
		
		lstAlbums.setItems(albums);
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
		String name = showInputDialog(stage, "Rename Album", "Enter a name for the new album: ");
		if(name == null) {
			return;
		}
		try{
			Album album = stateMachine.currentUser.createAlbum(name);
			olist.add(album);
		}catch(IllegalArgumentException e) {
			showErrorDialog(stage, "Error", "An album with that name already exists.");
			return;
		}
		
	}
	
	/**
	 * Delete the selected album.
	 */
	public void deleteAlbum() {
		Album album = lstAlbums.getSelectionModel().getSelectedItem();
		ButtonType response = showConfirmationDialog(stage, "Delete Album", "Are you sure you want to delete album \"" + album.getName() + "\"?");
		if(response == null || response == ButtonType.CANCEL) {
			return;
		}
		stateMachine.currentUser.deleteAlbum(album);
		olist.remove(album);
	}
	
	/**
	 * Rename the selected album, as long as the name is not a duplicate.
	 */
	public void renameAlbum() {
		// TODO - some kind of validation that the list isn't empty
		Album album = lstAlbums.getSelectionModel().getSelectedItem();
		String name = showInputDialog(stage, "Rename Album", "Enter a new name for album \"" + album.getName() + "\": ");
		if(name == null) {
			return;
		}
		if(!album.setName(name)) {
			showErrorDialog(stage, "Error", "An album with that name already exists.");
			return;
		}
		
	}
	
	public Album findPhotosByTag(ArrayList<Picture> pictures) {
		ArrayList<Picture> results=new ArrayList<Picture>();
		List<String> choices = new ArrayList<String>();
		for(Picture pic:pictures) {
			choices.addAll(pic.getTags());
		}
		if(choices.size()==0) {
			//none of the photos have tags
			//TODO error message
		}
		String searchBy=showChoiceDialog(stage, "Search By Tag", "Choose a tag to search by:", choices);
		String tag=searchBy.substring(0,searchBy.indexOf(":"));
		String value=searchBy.substring(searchBy.indexOf("\n")+1);
		/*
		String tag=showInputDialog(stage, "Search By Tag", "Enter a tag to search by:");
		String value=showInputDialog(stage, "Search By Tag", "Enter a value for the tag \""+tag+"\":");
		*/
		for(Picture pic:pictures) {
			if(pic.getTags().contains(tag+":\n"+value)) {
				results.add(pic);
			}
		}
				
		Album searchResults=new Album("searchResults", results);
		return searchResults;
	}
	
	@FXML private void processEvent(Event e) {
		stateMachine.processEvent(e);
	}
	
}
