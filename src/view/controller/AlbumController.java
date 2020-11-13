package view.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import model.Album;
import model.Picture;

public class AlbumController extends PhotosController {

	// FXML entities
	@FXML public ListView<Picture> lstPictures;
	@FXML public Label lblAlbum;
	@FXML public Button cmdAddPhoto;
	@FXML public Button cmdRemovePhoto;
	@FXML public Button cmdAddTag;
	@FXML public Button cmdRemoveTag;
	@FXML public Button cmdEditCaption;
	@FXML public Button cmdMovePhoto;
	@FXML public Button cmdCopyPhoto;
	@FXML public Button cmdViewSlideshow;
	@FXML public Button cmdCreateAlbumFromSearch;
	@FXML public Button cmdClose;
	
	// List of pictures
	private ObservableList<Picture> olist;
	// Sorted wrapper list
	private ObservableList<Picture> pictures;
	
	// Selected list index
	private int selectedIndex;
	
	/**
	 * Imports the list of pictures from the current album and displays each picture's thumbnail and caption.
	 */
	public void start() {
		
		// Pictures are organized from earliest timestamp to latest
		olist = FXCollections.observableArrayList();
		olist.addAll(stateMachine.currentAlbum.getPictures());
		pictures = new SortedList<Picture>(olist,
				(p1, p2) -> p1.getTimestamp().compareTo(p2.getTimestamp()));
		
		// Initialize the listview
		lstPictures.setItems(pictures);
		lstPictures.setCellFactory(param -> new ListCell<Picture>() {
			private ImageView imageView = new ImageView();
            @Override
            public void updateItem(Picture p, boolean empty) {
                super.updateItem(p, empty);
                if(empty) {
                	setText(null);
                	setGraphic(null);
                }else {
                	// TODO - possibly use CSS to make the cells more visually appealing
                	imageView.setImage(p.getImage());
                	imageView.setFitHeight(50);
                	imageView.setPreserveRatio(true);
                	setText(p.caption);
                	setGraphic(imageView);
                }
            }
        });
		
		lblAlbum.setText(stateMachine.currentAlbum.getName());
		
	}
	
	/**
	 * Creates a {@code Picture} object based on a series of user inputs and adds it to the current {@code Album}.
	 */
	public void addPhoto() {
		
		File picFile = showImageDialog(stage, "Add Photo");
		if(picFile == null) {
			return;
		}
		Picture pic;
		try {
			pic = new Picture(picFile.getPath());
		}catch(FileNotFoundException e) {
			e.printStackTrace(); // should not occur
			return;
		}
		pic.caption = showInputDialog(stage, "Add Photo", "Enter a caption:");
		// TODO location, tags?
		
		stateMachine.currentAlbum.addPicture(pic);
		olist.add(pic);
		
	}
	
	/**
	 * Removes the selected {@code Picture} from the current {@code Album}.
	 */
	public void removePhoto() {
		Picture target = lstPictures.getSelectionModel().getSelectedItem();
		ButtonType response = showConfirmationDialog(stage, "Delete Photo", "Are you sure you want to delete this photo?");
		if(response == null || response == ButtonType.CANCEL) {
			return;
		}
		stateMachine.currentAlbum.removePicture(target);
		olist.remove(target);
	}
	
	/**
	 * Edits the caption of the selected {@code Picture}.
	 */
	public void editCaption() {
		// TODO - some kind of validation that the list isn't empty
		Picture picture = lstPictures.getSelectionModel().getSelectedItem();
		String name = showInputDialog(stage, "Rename Photo", "Enter a new caption:");
		if(name == null) {
			return;
		}
		picture.caption = name;
	}
	
	/**
	 * Move the selected photo to another album.
	 */
	public void movePhoto() {
		// There should be at least one other album to choose from
		if(stateMachine.currentUser.getAlbums().size() <= 1) {
			showErrorDialog(stage, "Error", "There are no other albums to which this photo can be moved.");
			return;
		}
		
		// Choose from any album except the current one
		Picture picture = lstPictures.getSelectionModel().getSelectedItem();
		List<Album> choices = stateMachine.currentUser.getAlbums().stream()
				.filter(a -> a != stateMachine.currentAlbum)
				.collect(Collectors.toList());
		Album album = showChoiceDialog(stage, "Move Photo", "Choose a new album for this photo:", choices);
		if(album == null) {
			return;
		}
		album.addPicture(picture);
		stateMachine.currentAlbum.removePicture(picture);
		olist.remove(picture);
	}
	
	/**
	 * Copy the selected photo to another album.
	 */
	public void copyPhoto() {
		// There should be at least one other album to choose from
		if(stateMachine.currentUser.getAlbums().size() <= 1) {
			showErrorDialog(stage, "Error", "There are no other albums to which this photo can be copied.");
			return;
		}
		
		// Choose from any album except the current one
		Picture picture = lstPictures.getSelectionModel().getSelectedItem();
		List<Album> choices = stateMachine.currentUser.getAlbums().stream()
				.filter(a -> a != stateMachine.currentAlbum)
				.collect(Collectors.toList());
		Album album = showChoiceDialog(stage, "Copy Photo", "Choose an album for the copy:", choices);
		if(album == null) {
			return;
		}
		album.addPicture(picture);
	}
	
	public void addTag() {
		Picture picture = lstPictures.getSelectionModel().getSelectedItem();
		//TODO add logic for if they click cancel for either tag or value prompts
		String tag=showInputDialog(stage, "Add Tag", "Enter a tag name:");
		String value=showInputDialog(stage, "Add Tag", "Enter a value for the tag \""+tag+"\":");
		picture.addTag(tag, value);
	}
	
	public void removeTag() {
		Picture picture = lstPictures.getSelectionModel().getSelectedItem();
		List<String> choices = picture.getTags();
		String toRemove=showChoiceDialog(stage, "Remove Tag", "Choose a tag to remove:", choices);
		String tag=toRemove.substring(0,toRemove.indexOf(":"));
		String value=toRemove.substring(toRemove.indexOf("\n")+1);
		/*
		String tag=showInputDialog(stage, "Remove Tag", "Enter a tag to remove from:");
		String value=showInputDialog(stage, "Remove Tag", "Enter the value to remove from tag \""+tag+"\":");
		*/
		picture.removeTag(tag, value);
	}
	
	@FXML private void processEvent(Event e) {
		stateMachine.processEvent(e);
	}
	
}
