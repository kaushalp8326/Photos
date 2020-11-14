package view.controller;

import java.time.format.DateTimeFormatter;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Picture;

public class SlideshowController extends PhotosController {
	
	// FXML entities
	@FXML public Parent window;
	
	@FXML public Button cmdFirstPhoto;
	@FXML public Button cmdPrevPhoto;
	@FXML public Button cmdNextPhoto;
	@FXML public Button cmdLastPhoto;
	
	@FXML public Label lblPhotoIndex;
	@FXML public Label lblCaption;
	@FXML public Label lblDateCaptured;
	@FXML public Label lblTags;
	
	@FXML public ImageView imgPhoto;
	
	// Current "slide" in the slideshow
	public int slide;

	/**
	 * Initialize the slideshow to show the first photo in the album.
	 */
	public void start() {
		slide = 0;
		showPhoto();
		stage = new Stage();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				processEvent(event);
			}
		});
	}
	
	/**
	 * Update the window given the current slide.
	 */
	public void showPhoto() {
		Picture pic = stateMachine.currentAlbum.getPictures().get(slide);
		imgPhoto.setImage(pic.getImage());
		lblCaption.setText("Caption: " + pic.caption);
		lblDateCaptured.setText("Date Captured: " + pic.getTimestamp()
													.format(DateTimeFormatter.ofPattern("YYYY-MM-dd, h:mm:ss a")));
		lblTags.setText("Tags: "+pic.printTags());
		lblPhotoIndex.setText((slide+1) + " of " + stateMachine.currentAlbum.getSize());
		if(slide == 0) {
			cmdFirstPhoto.setDisable(true);
			cmdPrevPhoto.setDisable(true);
		}else {
			cmdFirstPhoto.setDisable(false);
			cmdPrevPhoto.setDisable(false);
		}
		if(slide == stateMachine.currentAlbum.getSize() - 1) {
			cmdNextPhoto.setDisable(true);
			cmdLastPhoto.setDisable(true);
		}else {
			cmdNextPhoto.setDisable(false);
			cmdLastPhoto.setDisable(false);
		}
	}
	
	@FXML private void processEvent(Event e) {
		stateMachine.processEvent(e);
	}
	
}
