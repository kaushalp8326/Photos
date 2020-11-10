package view.controller;

import java.io.FileNotFoundException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import model.Picture;

public class AlbumController extends PhotosController {

	// FXML entities
	@FXML public ListView<Picture> lstPictures;
	@FXML public Label lblAlbum;
	@FXML public Button cmdAddPhoto;
	@FXML public Button cmdRemovePhoto;
	@FXML public Button cmdAddTag;
	@FXML public Button cmdRemoveTag;
	@FXML public Button cmdEditTag;
	@FXML public Button cmdMovePhoto;
	@FXML public Button cmdCopyPhoto;
	@FXML public Button cmdViewSlideshow;
	@FXML public Button cmdCreateAlbumFromSearch;
	
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
		olist.addAll(stateMachine.currentAlbum.pictures);
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
		String path;
		do {
			path = showInputDialog(stage, "Add Photo", "Enter the filepath for this photo (ex. C:\\Users\\Name\\Desktop):");
		}while(path == null);
		
		Picture pic;
		try {
			pic = new Picture(path);
		}catch(FileNotFoundException e) {
			showErrorDialog(stage, "Error", "Could not load the photo at filepath \"" + path + "\".");
			return;
		}
		pic.caption = showInputDialog(stage, "Add Photo", "Enter a caption:");
		// TODO location, tags?
		
		stateMachine.currentAlbum.pictures.add(pic);
		olist.add(pic);
		
	}
	
	@FXML private void processEvent(ActionEvent e) {
		stateMachine.processEvent(e);
	}
	
}
