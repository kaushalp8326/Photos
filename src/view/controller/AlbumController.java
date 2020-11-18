package view.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

/**
 * Controller for the album subsystem.
 * @author John Hoban
 * @author Kaushal Patel
 *
 */
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
	
	/**
	 * List of pictures
	 */
	private ObservableList<Picture> olist;
	/**
	 * Sorted wrapper list
	 */
	private ObservableList<Picture> pictures;
	
	/**
	 * Selected list index
	 */
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
		if(stateMachine.currentAlbum.getOwner()==null) {
			cmdMovePhoto.setDisable(true);
			cmdCopyPhoto.setDisable(true);
			cmdCreateAlbumFromSearch.setDisable(false);
		}else {
			cmdMovePhoto.setDisable(false);
			cmdCopyPhoto.setDisable(false);
			cmdCreateAlbumFromSearch.setDisable(true);
		}
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
		if(target==null) {
			showErrorDialog(stage, "Error", "Please select an image before preceding.");
			return;
		}
		ButtonType response = showConfirmationDialog(stage, "Delete Photo", "Are you sure you want to delete this photo?");
		if(response == null || response == ButtonType.CANCEL) {
			return;
		}
		stateMachine.currentAlbum.removePicture(target);
		olist.remove(target);
	}
	
	/**
	 * Edits the caption of the selected {@code Picture}.
	 * @return Whether the edit was successful.
	 */
	public boolean editCaption() {
		//check if the list is empty
		if(lstPictures.getItems().size()==0) {
			showErrorDialog(stage, "Error", "There are no photos in this album.");
			return false;
		}
		Picture picture = lstPictures.getSelectionModel().getSelectedItem();
		if(picture==null) {
			showErrorDialog(stage, "Error", "Please select an image before preceding.");
			return false;
		}
		String name = showInputDialog(stage, "Rename Photo", "Enter a new caption:");
		if(name == null) {
			return false;
		}
		picture.caption = name;
		return true;
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
		if(picture==null) {
			showErrorDialog(stage, "Error", "Please select an image before preceding.");
			return;
		}
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
		if(picture==null) {
			showErrorDialog(stage, "Error", "Please select an image before preceding.");
			return;
		}
		List<Album> choices = stateMachine.currentUser.getAlbums().stream()
				.filter(a -> a != stateMachine.currentAlbum)
				.collect(Collectors.toList());
		Album album = showChoiceDialog(stage, "Copy Photo", "Choose an album for the copy:", choices);
		if(album == null) {
			return;
		}
		album.addPicture(picture);
	}
	
	/**
	 * Add a tag-value pair to the selected photo.
	 */
	public void addTag() {
		Picture picture = lstPictures.getSelectionModel().getSelectedItem();
		if(picture==null) {
			showErrorDialog(stage, "Error", "Please select an image before preceding.");
			return;
		}
		String tag;
		if(stateMachine.currentUser.uniqueTags==null || stateMachine.currentUser.uniqueTags.size()==0) {
			//there are no existing tags to choose from
			tag=showInputDialog(stage, "Add Tag", "Enter a tag name:");
			if(tag==null) {
				return;
			}
			if(tag.equalsIgnoreCase("")) {
				showErrorDialog(stage, "Error", "Did not add a Tag.");
				return;
			}
			String value=showInputDialog(stage, "Add Tag", "Enter a value for the tag \""+tag+"\":");
			if(value==null) {
				return;
			}
			if(value.equalsIgnoreCase("")) {
				showErrorDialog(stage, "Error", "Did not add a value.");
				return;
			}
			stateMachine.currentUser.uniqueTags=new HashSet<String>();
			stateMachine.currentUser.uniqueTags.add(tag);
			picture.addTag(tag, value);
			return;
		}else {
			//ask whether to get from existing list or make a new tag
			ArrayList<String> existingOrNew=new ArrayList<String>();
			existingOrNew.add("Existing tag");
			existingOrNew.add("New tag");
			String decision=showChoiceDialog(stage, "Add Tag", "Add to an existing Tag, or create a new one?", existingOrNew);
			if(decision==null) {
				return;
			}else if(decision.equalsIgnoreCase("Existing tag")) {
				HashSet<String> choices = stateMachine.currentUser.uniqueTags;
				tag=showChoiceDialog(stage, "Add Tag", "Choose a tag to add to:", choices);
				if(tag==null) {
					return;
				}
				String value=showInputDialog(stage, "Add Tag", "Enter a value for the tag \""+tag+"\":");
				if(value==null) {
					return;
				}
				if(value.equalsIgnoreCase("")) {
					showErrorDialog(stage, "Error", "Did not add a value.");
					return;
				}
				picture.addTag(tag, value);
				return;
			}else {
				//chose to make new tag
				tag=showInputDialog(stage, "Add Tag", "Enter a tag name:");
				if(tag==null) {
					return;
				}
				if(tag.equalsIgnoreCase("")) {
					showErrorDialog(stage, "Error", "Did not add a Tag.");
					return;
				}
				String value=showInputDialog(stage, "Add Tag", "Enter a value for the tag \""+tag+"\":");
				if(value==null) {
					return;
				}
				if(value.equalsIgnoreCase("")) {
					showErrorDialog(stage, "Error", "Did not add a value.");
					return;
				}
				stateMachine.currentUser.uniqueTags.add(tag);
				picture.addTag(tag, value);
				return;
			}
		}
	}
	
	/**
	 * Remove a tag-value pair from the selected photo.
	 */
	public void removeTag() {
		Picture picture = lstPictures.getSelectionModel().getSelectedItem();
		if(picture==null) {
			showErrorDialog(stage, "Error", "Please select an image before preceding.");
			return;
		}
		List<String> choices = picture.getTags();
		if(choices.size()==0) {
			//The photos has no tags to remove
			showErrorDialog(stage, "Error", "There are no tags to remove.");
			return;
		}
		String toRemove=showChoiceDialog(stage, "Remove Tag", "Choose a tag to remove:", choices);
		//check for cancel
		if(toRemove==null) {
			return;
		}
		String tag=toRemove.substring(0,toRemove.indexOf(":"));
		String value=toRemove.substring(toRemove.indexOf("\n")+1);
		picture.removeTag(tag, value);
	}
	
	/**
	 * Create an album from the results of a search.
	 */
	public void createAlbumFromSearch() {
		String name = showInputDialog(stage, "Rename Album", "Enter a name for the new album: ");
		if(name == null) {
			return;
		}
		try{
			Album album = stateMachine.currentUser.createAlbum(name);
			for(Picture pic:lstPictures.getItems()) {
				album.addPicture(pic);
			}
			ObservableList<Album> olist = FXCollections.observableArrayList();
			olist.addAll(stateMachine.currentUser.getAlbums());
			olist.add(album);
		}catch(IllegalArgumentException e) {
			showErrorDialog(stage, "Error", "An album with that name already exists.");
			return;
		}
		
	}
	
	@FXML private void processEvent(Event e) {
		stateMachine.processEvent(e);
	}
	
}
